package com.blog.utils;

/**
 * @program: MyBlog
 * @description: 定义逻辑异常类
 * @author: Ailuoli
 * @create: 2019-05-01 20:09
 **/
public class LogicException extends RuntimeException {



    private String errorMsg;

    private String code;


    public LogicException(String errorMsg) {
        super(errorMsg);
        this.code = errorMsg.substring(0, 5);
        this.errorMsg = errorMsg.substring(6);
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


    public static LogicException le(String errorMsg)
    {
        return new LogicException(errorMsg);
    }

}

