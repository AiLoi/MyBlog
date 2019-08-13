package com.blog.mapper;

import com.blog.entity.pojo.BlogPermission;
import com.blog.security.SecurityRolePermission;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface BlogPermissionMapper {
    int deleteByPrimaryKey(Integer permissionId);

    int insert(BlogPermission record);

    int insertSelective(BlogPermission record);

    BlogPermission selectByPrimaryKey(Integer permissionId);

    int updateByPrimaryKeySelective(BlogPermission record);

    int updateByPrimaryKey(BlogPermission record);

    List<SecurityRolePermission> selectRolePermission();

    List<String> selectAllPage();
}