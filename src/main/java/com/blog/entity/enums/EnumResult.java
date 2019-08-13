package com.blog.entity.enums;

/**
 * @program: MyBlog
 * @description: 错误枚举
 * @author: Ailuoli
 * @create: 2019-05-01 18:05
 **/
public enum EnumResult {


    ACCESS_NOT(501,"权限不足"),

    TOKEN_IS_NOT_VALID(502,"token无效,请重新登录");




    private Integer code;

    private String msg;

    EnumResult(Integer code, String msg)
    {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}

