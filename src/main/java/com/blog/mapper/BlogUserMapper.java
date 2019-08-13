package com.blog.mapper;

import com.blog.security.SecurityBlogUser;
import com.blog.entity.pojo.BlogUser;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Mapper
public interface BlogUserMapper {
    int deleteByPrimaryKey(Integer userId) throws Exception;

    int insert(BlogUser record) throws Exception;

    int insertSelective(BlogUser record) throws Exception;

    BlogUser selectByPrimaryKey(Integer userId) throws Exception;

    int updateByPrimaryKeySelective(BlogUser record) throws Exception;

    int updateByPrimaryKey(BlogUser record) throws Exception;

    /**
     * 根据用户名获取用户信息
     * @param username 用户名
     * @return jwtUser
     */
    SecurityBlogUser getUserByName(String username) throws Exception;

    /**
     * 更新用户的最后登录时间
     * @param blogUser 用户信息
     */
    int updateUserLoginTime(BlogUser blogUser) throws Exception;

    /**
     * 校验邮箱
     * @param email 邮箱
     */
    int checkEmail(String email) throws Exception;

    /**
     * 校验手机号
     * @param phone 手机号
     */
    int checkPhone(String phone) throws Exception;

    @Transactional
    int insertUser(BlogUser blogUser) throws Exception;

    SecurityBlogUser selectBlogUserById(int userId)throws Exception;

    int selectOpenIdForQQLogin(String openId) throws Exception;

    int insertBlogUserForQQLogin(SecurityBlogUser blogUser) throws Exception;

    int updateBlogUserForQQLogin(SecurityBlogUser blogUser) throws Exception;

    SecurityBlogUser selectBlogUserByQQOpenId(String openId) throws Exception;
}