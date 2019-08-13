package com.blog.security;

import com.blog.mapper.BlogPermissionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @program: blog
 * @description:
 * @author: Ailuoli
 * @create: 2019-05-22 01:43
 **/

@Component
public class SecurityAuthorityDecision {

    public static Map<String, Collection<ConfigAttribute>> map ;

    @Autowired
    private BlogPermissionMapper blogPermissionMapper;

    @Autowired
    private RedisTemplate redisTemplate;


    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
        Collection<ConfigAttribute> collection = getAttribute(request);
        if (authentication.getPrincipal().equals("anonymousUser")) {
            return false;
        }

        if (null == collection || collection.size() <= 0) {
            return false;
        }

        ConfigAttribute configAttribute;
        String needRole;
        for (ConfigAttribute attribute : collection) {
            configAttribute = attribute;
            needRole = configAttribute.getAttribute();
            for (GrantedAuthority grantedAuthority : authentication.getAuthorities()) {
                if (needRole.trim().equals(grantedAuthority.getAuthority())) {
                    return true;
                }
            }
        }
        throw new AccessDeniedException("权限不足");

    }


    public Collection<ConfigAttribute> getAttribute(HttpServletRequest request) {


        Collection<ConfigAttribute> roles = new ArrayList<>();
        if (map == null) {
            loadResourceDefine();
        }
        for (Map.Entry<String, Collection<ConfigAttribute>> entry : map.entrySet()) {
            String url = entry.getKey();
            if (new AntPathRequestMatcher(url).matches(request)) {
                roles.addAll(map.get(url));
             //   return map.get(url);
            }
        }

        return roles;
    }

    /**
     * 初始化所有资源对应的角色
     */
    private void loadResourceDefine() {
        map = new HashMap<>();

        List<SecurityRolePermission> rolePermissions = blogPermissionMapper.selectRolePermission();
        rolePermissions.forEach((e) ->
        {
            String url = e.getUrl();
            String roleName = e.getRoleName();
            ConfigAttribute configAttribute = new SecurityConfig(roleName);
            if (map.containsKey(url))
                map.get(url).add(configAttribute);
            else {
                Collection<ConfigAttribute> list = new ArrayList<>();
                list.add(configAttribute);
                map.put(url, list);
            }
        });
    }


}

