package com.blog.entity.pojo;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BlogPermission {


    private Integer permissionId;

    private String url;

    private String name;

    private Integer permissionStatus;

}