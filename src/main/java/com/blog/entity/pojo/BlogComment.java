package com.blog.entity.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class BlogComment {
    private Integer commentId;

    private Integer userId;

    private Integer articleId;

    private Date createTime;

    private Integer isDelete;

    private String commentContent;

}