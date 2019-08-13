package com.blog.entity.pojo;

import com.blog.entity.enums.EnumEnable;
import com.blog.entity.enums.EnumSex;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;


@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BlogUser {
    private Integer userId;

    private String qqOpenId;

    private String username;

    private String password;

    private String nickname;

    private String qqNickname;

    private String wechatNickename;

    private Date birthDate;

    private EnumSex sex;

    private String figureUrl;

    private Date createDate;

    private String email;

    private String phone;

    private EnumEnable userStatus;

    private Date lastPasswordResetDate;

    private Date lastUserLoginDate;


}