package com.ike.sq.alliance.bean;

import java.io.Serializable;

/**
 * Created by T-BayMax on 2017/8/28.
 */

public class JsonResult<T> implements Serializable {
    private int code;
    private T data;
    private String msg;

    public JsonResult() {
    }

    public JsonResult(int code, T data) {
        this.code = code;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
