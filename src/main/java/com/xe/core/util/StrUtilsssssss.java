package com.xe.core.util;

import org.springframework.util.DigestUtils;

import java.util.UUID;


/*可以用hutool封装的方法 很全*/

public class StrUtilsssssss {

    /**
     * 生成UUID
     *
     * @return 生成的字符串
     */
    public static String generateUUID() {
        String uuid = UUID.randomUUID().toString().replace("-", "").substring(0, 32);
        System.out.println(uuid);
        return uuid;
    }

    /*
     * md5常用工具类
     * @param data(String) 传入的数据
     * @return
     * （下面有方法代替）
     */
    /*public static String MD5(String data) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            //获取摘要信息
            byte[] array = md5.digest(data.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (byte item : array) {
                sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
            }
            System.out.println(sb.toString().toUpperCase());
            return sb.toString().toUpperCase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }*/

    /*
     * MD5加密
     * @param str(String) MD5字符串
     * @return 加密后的信息
     */
    public static String getMD5(String str) {
        String md5 = DigestUtils.md5DigestAsHex(str.getBytes());
        System.out.println(md5);
        return md5;
    }

    public static void main(String[] args) {
        StrUtilsssssss.getMD5("sadasdasd");
    }
}
