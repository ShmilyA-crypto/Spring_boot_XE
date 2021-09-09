package com.xe.core.util;

/**
 * @Author admin
 * @Date 2021/5/25 11:35
 */
public class LayuiInfo {
    private int code=0;

    private String msg;

    private long count;

    private Object data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }



    public LayuiInfo(int code, String msg, long count, Object data) {
        super();
        this.code = code;
        this.msg = msg;
        this.count = count;
        this.data = data;
    }

    public LayuiInfo() {
        super();
    }
}
