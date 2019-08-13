package com.blog.controller;

import com.blog.entity.pojo.BlogUser;
import com.blog.service.BlogUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: blog
 * @description: 用户控制器
 * @author: Ailuoli
 * @create: 2019-05-06 15:25
 **/
@Api(tags = "用户模块管理")
@RestController
@RequestMapping("/blog/user")
public class BlogUserController {

    @Autowired
    private BlogUserService blogUserService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @ApiOperation("注册用户，并授权")
    @PostMapping(value = "/registered", produces = "application/json;charset=UTF-8")
    public Map<String, Object> register(@RequestBody BlogUser user) {

        String msg = "success";
        boolean status = false;


        Map<String, Object> resultMap = new HashMap<>();

        try {
            if (blogUserService.checkEmail(user.getUsername()))
                msg = "邮箱已经被注册过";
            else {
                if (blogUserService.checkPhone(user.getPhone()))
                    msg = "手机号已经被注册过";
                else {
                    user.setPassword(passwordEncoder.encode(user.getPassword()));
                    user.setLastPasswordResetDate(new Date());
                    user.setFigureUrl("https://ailuoli.cn/image/get_image?fileName=20190620120632_timg.jpg");
                    status = blogUserService.addUser(user);
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        resultMap.put("msg", msg);

        resultMap.put("status", status);

        return resultMap;
    }


    @ApiOperation("验证用户是否过期，并返回用户信息")
    @GetMapping("/is_expired")
    public Map isExpired(Authentication auth) {
        Map<String, Object> resultMap = new HashMap<>();
        //如果当前请求的凭证不为空说明没有过期
        if (auth != null) {
//            expired = RememberMeAuthenticationToken.class.isAssignableFrom(auth.getClass());
            resultMap.put("userDetails", auth.getPrincipal());
            resultMap.put("expired", true);
        } else {
            resultMap.put("userDetails", null);
            resultMap.put("expired", false);
        }

        return resultMap;
    }

}

