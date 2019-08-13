package com.blog.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: blog
 * @description: qq登录处理器
 * @author: Ailuoli
 * @create: 2019-05-27 20:00
 **/
@RestController
@RequestMapping("/qqlogin")
public class QQLoginController {




    @GetMapping("/success")
    public String QQLoginSuccess()
    {
        System.out.println(1);
        return "1";
    }

}

