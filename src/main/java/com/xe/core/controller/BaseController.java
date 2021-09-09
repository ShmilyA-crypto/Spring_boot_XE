package com.xe.core.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.fivefu.base.common.utils.StrUtils;
import com.xe.core.util.AesUtils;
import com.xe.core.util.LayuiInfo;
import com.xe.core.util.ResultInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @Author admin
 * @Date 2021/5/24 16:39
 */
public class BaseController {
    private static final Logger log = LoggerFactory.getLogger(com.xe.core.controller.BaseController.class);
    @Autowired
    protected HttpServletRequest request;
    @Autowired
    protected HttpServletResponse response;

    @ApiIgnore
    @RequestMapping({ "/" })
    public ResultInfo index() {
        return renderSuccess("success");
    }

    /**
     * 渲染失败数据
     *
     * @return result
     */
    protected ResultInfo renderError() {
        ResultInfo result = new ResultInfo();
        result.setResult("failed");
        result.setCode(500);
        return result;
    }

    /**
     * 渲染失败数据（带消息）
     *
     * @param msg 需要返回的消息
     * @return result
     */
    protected ResultInfo renderError(String msg) {
        ResultInfo result = renderError();
        result.setMessage(msg);
        return result;
    }

    /**
     * 渲染成功数据
     *
     * @return result
     */
    protected ResultInfo renderSuccess() {
        ResultInfo result = new ResultInfo();
        result.setResult("success");
        result.setCode(200);
        return result;
    }

    /**
     * 渲染成功数据（带信息）
     *
     * @param msg 需要返回的信息
     * @return result
     */
    protected ResultInfo renderSuccess(String msg) {
        ResultInfo result = renderSuccess();
        result.setMessage(msg);
        return result;
    }

    /**
     * 渲染成功数据（带数据）
     *
     * @param data 需要返回的对象
     * @return result
     */
    protected ResultInfo renderSuccess(Object data) {
        ResultInfo result = renderSuccess();
        result.setData(data);
        return result;
    }

    // =============================================================================数据加密====================================================================

    /**
     * 渲染成功数据 (带加密数据，使用JSONArray)
     * @param data
     * @return
     */
    protected ResultInfo renderSuccessEncryption (Object data) {
        ResultInfo result = renderSuccess();
        result.setData(AesUtils.aesEncrypt(JSONArray.toJSONString(data)));
        return result;
    }

    // =============================================================================END====================================================================


    /**
     * layui成功数据（带数据）
     *
     * @param obj 需要返回的对象
     * @return result
     */
    protected LayuiInfo returnSuccess(Object obj) {
        LayuiInfo layuiInfo = new LayuiInfo();
        layuiInfo.setData(obj);
        return layuiInfo;
    }

    /**
     * layui成功数据（带数据）
     *
     * @param info
     * @return result
     */
    protected LayuiInfo returnSuccess(LayuiInfo info) {
        return info;
    }

    /**
     * layui失败数据（带数据）
     *
     * @param msg
     * @return result
     */
    protected LayuiInfo returnError(String msg) {
        LayuiInfo layuiInfo = new LayuiInfo();
        layuiInfo.setCode(500);
        layuiInfo.setMsg(msg);
        return layuiInfo;
    }

    /**
     * 获取表单数据
     * @return
     */
    public Map<String, Object> formatDataToMap() {
        Map<String, Object> info = new HashMap<>();
        Map<String, String[]> map = request.getParameterMap();
        map.forEach((key, value) -> {
            info.put(key, StrUtils.isCheckNull(value[0]));
        });
        return info;
    }

    public Map<String, String> formatDataToStrMap() {
        Map<String, String> info = new HashMap<>();
        Map<String, String[]> map = request.getParameterMap();
        map.forEach((key, value) -> {
            info.put(key, StrUtils.isCheckNull(value[0]));
        });
        return info;
    }

    //分页参数
    protected void setDefaultPageLimit(Map<String, Object> paramMap) {
        int page = StrUtils.isNull(String.valueOf(paramMap.get("page"))) ? 1 : Integer.parseInt(String.valueOf(paramMap.get("page")));
        int limit = StrUtils.isNull(String.valueOf(paramMap.get("limit"))) ? 10 : Integer.parseInt(String.valueOf(paramMap.get("limit")));

        page = (page - 1) * limit;
        paramMap.put("page", page);
        paramMap.put("limit", limit);
    }

    protected void setDefaultStrPageLimit(Map<String, String> paramMap) {
        int page = StrUtils.isNull(paramMap.get("page")) ? 1 : Integer.parseInt(paramMap.get("page"));
        int limit = StrUtils.isNull(paramMap.get("limit")) ? 10 : Integer.parseInt(paramMap.get("limit"));

        paramMap.put("page", String.valueOf(page));
        paramMap.put("limit", String.valueOf(limit));
    }

    /**
     *
     * @param flag              成功失败的标识 成功的就给true   失败的就给false
     * @param request           请求的request
     * @param serverName        服务的名称描述
     * @param operatorName      操作人名称
     * @param object            结果数据
     */
    protected void addLog (boolean flag, HttpServletRequest request, String serverName, String operatorName, Object object) {
        if(flag){
            log.info(UUID.randomUUID().toString().replaceAll("-","")+"访问记录：IP地址为["+request.getRemoteHost()+"]，端口为["+request.getRemotePort()+"]，服务地址为["+request.getRequestURI()+"]，操作人["+operatorName+"]，执行["+serverName+"] 操作，传参为["+ JSON.toJSONString(request.getParameterMap())+"]，返回结果为["+ JSON.toJSONString(object)+"]");
        }else{
            log.error(UUID.randomUUID().toString().replaceAll("-","")+"访问记录失败：IP地址为["+request.getRemoteHost()+"]，端口为["+request.getRemotePort()+"]，服务地址为["+request.getRequestURI()+"]，操作人["+operatorName+"]，执行["+serverName+"] 操作，传参为["+JSON.toJSONString(request.getParameterMap())+"]，返回结果为["+ JSON.toJSONString(object)+"]");
        }
    }
}
