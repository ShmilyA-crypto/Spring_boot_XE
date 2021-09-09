package com.xe.core.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fivefu.base.HttpUtil;
import com.fivefu.base.common.utils.StrUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @Author admin
 * @Date 2021/5/28 16:03
 */
@Component
public class DataShareUtils {

    private static final Logger logger = LoggerFactory.getLogger(DataShareUtils.class);

    @Autowired
    private HttpUtil httpUtil;

    private static HttpUtil staticHttpUtil;

    private static String dataShareUrl;

    @Value("${datashare.URL}")
    public void setDataShareUrl(String dataShareUrl) {
        DataShareUtils.dataShareUrl = dataShareUrl;
    }

    @PostConstruct
    public void init () { staticHttpUtil = httpUtil; }


    /**
     * 写操作
     * @param value
     * @param params
     * @return
     * @throws Exception
     */
    public static String writeData (String value, Map<String, String> params) throws Exception {
        params.put("method", "write_data");
        return staticHttpUtil.okHttpClientUtil().Post(dataShareUrl + value, params);
    }


    /**
     * 修改、删除操作
     * @param value
     * @param params
     * @return
     * @throws Exception
     */
    public static String modifyData (String value, Map<String, String> params) throws Exception {
        params.put("method", "modify_data");
        return staticHttpUtil.okHttpClientUtil().Post(dataShareUrl + value, params);
    }


    /**
     * 获取数据
     * @param value
     * @param params
     * @return
     * @throws Exception
     */
    public static String obtainData (String value, Map<String, String> params) throws Exception {
        return staticHttpUtil.okHttpClientUtil().Post(dataShareUrl + value, params);
    }


    /**
     * 将拿到的单条数据放在实体类中
     * @param value
     * @param params
     * @param beanCls
     * @param <T>
     * @return
     */
    public static <T> T obtainDataBean (String value, Map<String, String> params,  Class<T> beanCls) {
        T finalResult = null;
        try {
            String initData = obtainData(value, params);
            JSONObject objectResult = JSONObject.parseObject(initData);
            if (objectResult == null) {
                return null;
            }
            String result = objectResult.getString("result");
            if ("success".equals(result)) {
                String data = objectResult.getString("data");
                // 对data解密
                String processedData= AesUtils.aesDecrypt(data);
                JSONObject processJson = JSONObject.parseObject(processedData);
                // 字段名称
                JSONArray columns =  processJson.getJSONArray("columns");
                // 具体数据
                JSONArray datas =  processJson.getJSONArray("datas");
                if(columns.size() > 0 && datas.size() > 0) {
                    //通过反射机制创建实例
                    finalResult = beanCls.newInstance();
                    //获取第一条数据
                    JSONArray dataOne = datas.getJSONArray(0);
                    dataToClass(columns, dataOne, finalResult, beanCls);
                }
            }
        } catch (Exception e) {
            logger.error("获取" + beanCls + "数据失败：" + e.getMessage());
        }
        return finalResult;
    }


    /**
     * 将拿到的多条数据放在实体类中
     * @param value
     * @param params
     * @param beanCls
     * @param <T>
     * @return
     */
    public static <T> List<T> obtainMoreDataBean (String value, Map<String, String> params,  Class<T> beanCls) {
        List<T> list = new ArrayList<T>();
        try {
            String initData = obtainData(value, params);
            JSONObject objectResult = JSONObject.parseObject(initData);
            if (objectResult == null) {
                return null;
            }
            String result = objectResult.getString("result");
            if ("success".equals(result)) {
                String data = objectResult.getString("data");
                // 对data解密
                String processedData= AesUtils.aesDecrypt(data);
                JSONObject processJson = JSONObject.parseObject(processedData);
                // 字段名称
                JSONArray columns =  processJson.getJSONArray("columns");
                // 具体数据
                JSONArray datas =  processJson.getJSONArray("datas");
                if(columns.size() > 0 && datas.size() > 0) {
                    //通过反射机制创建实例
                    for (int j = 0; j < datas.size(); j++) {
                        T finalResult = beanCls.newInstance();
                        JSONArray jsonArray = datas.getJSONArray(j);
                        dataToClass(columns, jsonArray, finalResult, beanCls);
                        list.add(finalResult);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("获取" + beanCls + "数据失败：" + e.getMessage());
        }
        return list;
    }


    /**
     * 将request中的值赋给对应的实体类（要求请求参数和实体类属性一致）
     * @param request
     * @param beanCls
     * @param <T>
     * @return
     */
    public static <T> T parameterToClass (HttpServletRequest request, Class<T> beanCls) {
        T finalResult = null;

        try {
            // 获取类中声明的所有字段
            Field[] fields = beanCls.getDeclaredFields();
            // 创建新的实例对象
            finalResult = beanCls.newInstance();

            for (int i = 0; i < fields.length; i++) {
                // 获取字段名称
                String name = fields[i].getName();
                // 去掉序列化id
                if ("serialVersionUID".equals(name)) {
                    continue;
                }

                // 获取请求中字段的值
                String value = request.getParameter(name);
                // 判断主键类型，不是自增的设置uuid
                TableId tableId = fields[i].getAnnotation(TableId.class);
                if (tableId != null && StrUtils.isNull(value) && !"AUTO".equals(tableId.type().toString())) {
                    fields[i].setAccessible(true);
                    fields[i].set(finalResult, UUID.randomUUID().toString());
                    continue;
                }

                //为空就不设置了，设置了后面日期类型会报错
                if (StrUtils.isNull(value)) {
                    continue;
                }

                // 赋值
                setData(fields[i], name, value, finalResult, beanCls);
            }

        } catch (Exception e) {
            logger.error("实例化" + beanCls + "失败：" + e.getMessage());
        }

        return finalResult;
    }


    public static <T> void dataToClass (JSONArray columns, JSONArray datas, T t, Class beanCls) throws Exception {
        for(int i = 0; i < columns.size(); i++){
            //字符和数据是一一对应的
            String column = columns.getString(i);
            String value = datas.getString(i);
            //为空就不设置了，设置了后面日期类型会报错
            if (StrUtils.isNull(value)) {
                continue;
            }
            //拿到属性之后，先判断是否存在该属性,有的话，再设置值
            if(isExistField(column, t)){
                //获得某个属性对象
                Field field = beanCls.getDeclaredField(column);
                // 赋值
                setData(field, column, value, t, beanCls);
            }
        }
    }


    public static <T> void setData (Field field, String name, String value, T t, Class beanCls) throws InvocationTargetException, IllegalAccessException {
        // 获取set方法
        Method method = ConverterRegistry.obtainSetMethod(name, beanCls);
        // 转换value类型
        Object convert = ConverterRegistry.convert(field.getType(), value);
        method.invoke(t, convert);
    }


    /**
     * 判断你一个类是否存在某个属性（字段）
     *
     * @param field 字段
     * @param obj   类对象
     * @return true:存在，false:不存在或者参数不合法
     */
    public static Boolean isExistField(String field, Object obj) {
        if (obj == null || StrUtils.isEmpty(field)) {
            return false;
        }
        Object o = JSON.toJSON(obj);
        JSONObject jsonObj = new JSONObject();
        if (o instanceof JSONObject) {
            jsonObj = (JSONObject) o;
        }
        return jsonObj.containsKey(field);
    }
}
