package com.blog.entity.enums;

import java.io.Serializable;

/**
 * @program: MyBlog
 * @description: 用户激活枚举
 * @author: Ailuoli
 * @create: 2019-04-28 10:02
 **/
@SuppressWarnings("NonAsciiCharacters")
public enum  EnumEnable implements Serializable
{
    激活(0,"isEnable"),
    未激活(1,"unEnable");

    private Integer id;

    private String name;

    EnumEnable(Integer id, String name) {
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


    public static EnumEnable getEnumById(int id)
    {
        for(EnumEnable enumEnable : EnumEnable.values())
        {
            if (enumEnable.getId()==id)
                return enumEnable;
        }

        return null;
    }
}

