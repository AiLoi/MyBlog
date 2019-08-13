package com.blog.entity.pojo;

import com.blog.security.SecurityBlogUser;
import lombok.Data;

import java.io.Serializable;

/**
 * @program: blog
 * @description:
 * @author: Ailuoli
 * @create: 2019-05-09 18:26
 **/

@Data
public class HttpUser implements Serializable {


    private String userIp;

    private SecurityBlogUser securityBlogUser;

    private String userToken;
}

