package com.blog.entity.pojo;


import lombok.Data;

import java.io.Serializable;

@Data
public class BlogCourse implements Serializable {
    private Integer courseId;

    private String courseName;

    private Integer lanId;

    private Integer isDelete;

    private String courseValue;

}