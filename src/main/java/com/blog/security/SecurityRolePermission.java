package com.blog.security;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @program: blog
 * @description: 角色资源对应实体
 * @author: Ailuoli
 * @create: 2019-05-14 15:51
 **/

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SecurityRolePermission {


    private String url;

    private String roleName;

}

