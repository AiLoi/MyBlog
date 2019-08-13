package com.blog.entity.enums;

/**
 * @program: MyBlog
 * @description: 异常枚举
 * @author: Ailuoli
 * @create: 2019-05-01 20:01
 **/
public enum EnumErrorMessage {
    SYSTEM_EXCEPTION(-1, "系统繁忙,请稍后再试"),
    NOT_LOGIN(408, "您还未登陆或者登陆已超时，请重新登陆"),
    EMAIL_ALREADY_REGISTER(407, "该邮箱已备注册过"),
    GRANT_EXCEPTION(403, "权限不足，请登录管理员账号");


    private Integer id;

    private String name;


    EnumErrorMessage(Integer id, String name) {
        this.id = id;
        this.name = name;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static EnumErrorMessage getEnumById(int id) {
        for (EnumErrorMessage enumErrorMessage : EnumErrorMessage.values()) {
            if (enumErrorMessage.getId() == id)
                return enumErrorMessage;
        }

        return null;
    }
}
