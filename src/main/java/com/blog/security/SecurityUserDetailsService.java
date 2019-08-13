package com.blog.security;

import com.blog.entity.pojo.BlogRole;
import com.blog.mapper.BlogRoleMapper;
import com.blog.mapper.BlogUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @program: blog
 * @description:
 * @author: Ailuoli
 * @create: 2019-05-14 15:39
 **/

@Service
public class SecurityUserDetailsService implements UserDetailsService {

    @Autowired
    private BlogUserMapper blogUserMapper;

    @Autowired
    private BlogRoleMapper blogRoleMapper;

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        try {
            SecurityBlogUser user = blogUserMapper.getUserByName(s);

            if(null != user) {
                List<BlogRole> roles = blogRoleMapper.selectRoleByUserId(user.getUserId());
                user.setAuthorities(roles);
            }
            return user;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}

