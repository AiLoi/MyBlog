package com.blog.entity.vo;

import com.blog.entity.pojo.BlogArticle;
import com.blog.security.SecurityBlogUser;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @program: blog
 * @description: 文章扩展类
 * @author: Ailuoli
 * @create: 2019-05-15 19:26
 **/
@EqualsAndHashCode(callSuper = false)
@Data
@Accessors(chain = true)
public class BlogArticleVo extends BlogArticle {

    private SecurityBlogUser blogUser;

    private String createTimeStr;

    private List<BlogCommentVo> blogCommentVoList;

}

