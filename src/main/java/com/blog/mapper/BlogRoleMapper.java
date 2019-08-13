package com.blog.mapper;

import com.blog.entity.pojo.BlogRole;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface BlogRoleMapper {
    int deleteByPrimaryKey(Integer roleId) throws Exception;

    int insert(BlogRole record) throws Exception;

    int insertSelective(BlogRole record) throws Exception;

    BlogRole selectByPrimaryKey(Integer roleId) throws Exception;

    int updateByPrimaryKeySelective(BlogRole record) throws Exception;

    int updateByPrimaryKey(BlogRole record) throws Exception;

    /**
     * 根据用户id获取用户权限
     * @param userId 用户id
     * @return list
     */
    List<BlogRole> selectRoleByUserId(Integer userId) throws Exception;

    /**
     * 为用户初始化权限
     * @param userId 用户id
     */
    int insertUserRole(int userId) throws Exception;
}