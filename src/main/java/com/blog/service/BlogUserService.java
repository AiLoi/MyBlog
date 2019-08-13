package com.blog.service;

import com.blog.entity.pojo.BlogUser;
import com.blog.mapper.BlogRoleMapper;
import com.blog.mapper.BlogUserMapper;
import com.blog.security.SecurityBlogUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @program: blog
 * @description: 用户service
 * @author: Ailuoli
 * @create: 2019-05-06 15:14
 **/
@Service
public class BlogUserService {

    private final BlogUserMapper userMapper;

    private final BlogRoleMapper roleMapper;


    public BlogUserService(BlogUserMapper userMapper, BlogRoleMapper roleMapper) {
        this.userMapper = userMapper;
        this.roleMapper = roleMapper;
    }


    @Transactional
    public boolean addUser(BlogUser blogUser) throws Exception {
        //返回主键
        if(userMapper.insertUser(blogUser)==1) {
            return roleMapper.insertUserRole(blogUser.getUserId())==1;
        }
        return false;
    }

    public boolean checkEmail(String email) throws Exception {
        return userMapper.checkEmail(email)>0;
    }


    public boolean checkPhone(String phone) throws Exception {
        return userMapper.checkEmail(phone)>0;
    }

    public boolean checkHavingQOpenId(String openId) throws Exception
    {
        return userMapper.selectOpenIdForQQLogin(openId)==0;
    }

    public boolean addUserForQQLogin(SecurityBlogUser securityBlogUser) throws Exception
    {
        return userMapper.insertBlogUserForQQLogin(securityBlogUser)>0;
    }

    public boolean updateUserForQQLogin(SecurityBlogUser securityBlogUser) throws Exception
    {
        return userMapper.updateBlogUserForQQLogin(securityBlogUser)>0;
    }

    public SecurityBlogUser getBlogUserForQQOpenId(String openId) throws Exception
    {
        return userMapper.selectBlogUserByQQOpenId(openId);
    }

}

