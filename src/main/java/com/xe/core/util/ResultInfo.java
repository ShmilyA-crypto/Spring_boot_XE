package com.xe.core.util;

import java.io.Serializable;

/**
 * @Author admin
 * @Date 2021/5/25 11:29
 */
public class ResultInfo implements Serializable {

    private static final long serialVersionUID = 3003966470101480905L;

    private Integer code;

    private String result;

    private String message;

    private Object data;


    public ResultInfo(){

    }
    public ResultInfo(int code) {
        super();
        this.code = code;
    }

    public ResultInfo(Integer code, String message){
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
