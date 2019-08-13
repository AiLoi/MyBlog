package com.blog.entity.enums;

import java.io.Serializable;

/**
 * @program: MyBlog
 * @description: 性别枚举
 * @author: Ailuoli
 * @create: 2019-03-28 19:57
 **/

public enum  EnumSex implements Serializable
{
    男(1,"man"),
    女(2,"woman");



    private Integer id;
    private String name;

    EnumSex(int id, String name) {
        this.id=id;
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


    public static EnumSex getEnumById(int id)
    {
        for(EnumSex enumSex : EnumSex.values())
        {
            if (enumSex.getId() == id)
                return enumSex;
        }

        return null;
    }
}

