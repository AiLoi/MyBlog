package com.blog.utils;

import java.sql.Timestamp;
import java.util.Calendar;

/**
 * @program: MyBlog
 * @description: 统一rest风格的数据结构
 * @author: Ailuoli
 * @create: 2019-05-01 19:46
 **/
public class RestResult {

    private boolean success;

    private String code;

    private Object data;

    private Object errorMessage;

    private Timestamp currentTime;

    public RestResult() {

    }

    public RestResult(boolean success, String code, Object data, Object errorMessage) {
        this.success = success;
        this.code = code;
        this.data = data;
        this.errorMessage = errorMessage;
        this.currentTime = new Timestamp(Calendar.getInstance().getTimeInMillis());
    }

    @Override
    public String toString() {
        return "RestResult{" +
                "success=" + success +
                ", code='" + code + '\'' +
                ", data=" + data +
                ", errorMessage=" + errorMessage +
                ", currentTime=" + currentTime +
                '}';
    }


    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Object getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(Object errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Timestamp getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(Timestamp currentTime) {
        this.currentTime = currentTime;
    }
}

