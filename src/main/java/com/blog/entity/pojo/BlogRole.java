package com.blog.entity.pojo;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BlogRole implements GrantedAuthority {

    private Integer roleId;

    private String roleName;

    private String roleStatus;

    @Override
    public String getAuthority() {
        return roleName;
    }
}