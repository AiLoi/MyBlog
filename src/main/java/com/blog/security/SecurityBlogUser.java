package com.blog.security;

import com.blog.entity.pojo.BlogRole;
import com.blog.entity.pojo.BlogUser;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @program: blog
 * @description: jwt用户实体
 * @author: Ailuoli
 * @create: 2019-05-06 14:38
 **/

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class SecurityBlogUser extends BlogUser implements UserDetails, Serializable {


    private List<BlogRole> authorities;


    /*
    为用户赋予权限
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

