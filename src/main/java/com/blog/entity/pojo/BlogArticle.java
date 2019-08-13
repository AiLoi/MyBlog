package com.blog.entity.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BlogArticle {
    private Integer articleId;

    private String title;

    private Integer userId;

    private Date createTime;

    private Integer visitCount;

    private Integer commentCount;

    private Integer isTop;

    private String keyWord;

    private Integer isDelete;

    private String articleContent;

    private String markdownContent;

}