package com.xe.core.util;


import com.fivefu.base.common.utils.DateUtils;
import com.fivefu.base.common.utils.StrUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * 反射的类型转换
 *
 * @Author admin
 * @Date 2021/6/29 9:48
 */
public class ConverterRegistry {

    private static final Logger logger = LoggerFactory.getLogger(ConverterRegistry.class);

    private static final String PATTERN_DEFAULT = "yyyy-MM-dd HH:mm:ss";

    public static Method obtainSetMethod (String name, Class beanCls) {
        try {
            PropertyDescriptor descriptor = new PropertyDescriptor(name, beanCls);
            return descriptor.getWriteMethod();
        } catch (Exception e) {
            logger.error("获取" + beanCls + "中的" + name + "属性的set方法失败：" + e.getMessage());
            return null;
        }
    }

    public static Object convert (Class type, String value) {

        if (StrUtils.isNull(value)) {
            return value;
        } else if (type.isAssignableFrom(String.class)) {
            return value;
        } else if (type.isAssignableFrom(int.class) || type.isAssignableFrom(Integer.class)) {
            return Integer.parseInt(value);
        } else if (type.isAssignableFrom(Double.class) || type.isAssignableFrom(double.class)) {
            return Double.parseDouble(value);
        } else if (type.isAssignableFrom(Boolean.class) || type.isAssignableFrom(boolean.class)) {
            return Boolean.parseBoolean(value);
        } else if (type.isAssignableFrom(Long.class) || type.isAssignableFrom(long.class)) {
            return Long.valueOf(value);
        } else if (type.isAssignableFrom(Date.class)) {
            return DateUtils.format(disDateVail(value));
        } else if (type.isAssignableFrom(LocalDateTime.class)) {
            return DateUtils.parse(disDateVail(value));
        } else if (type.isAssignableFrom(Timestamp.class)) {
            return DateUtils.parse(disDateVail(value)).toInstant(ZoneOffset.ofHours(8)).toEpochMilli();
        } else {
            return "";
        }

    }

    public static String disDateVail (String date) {
        if (isDateVail(date)) {
            return date;
        } else if (StrUtils.isNotNull(date) && date.length() == 10) {
            return date + " 00:00:00";
        } else if (StrUtils.isNotNull(date) && date.length() == 13) {
            return date + ":00:00";
        } else if (StrUtils.isNotNull(date) && date.length() == 16) {
            return date + ":00";
        } else {
            return date;
        }
    }

    /**
     * 判断时间字符串是否是yyyy-MM-dd HH:mm:ss格式
     * @param date
     * @return
     */
    public static Boolean isDateVail (String date) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(PATTERN_DEFAULT);

        boolean flag = true;
        try {
            LocalDateTime.parse(date, dtf);
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }
}
