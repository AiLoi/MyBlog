package com.blog.entity.vo;

import com.blog.entity.pojo.BlogComment;
import com.blog.security.SecurityBlogUser;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @program: blog
 * @description: 评论扩展类
 * @author: Ailuoli
 * @create: 2019-05-23 15:43
 **/
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class BlogCommentVo extends BlogComment {

    private List<ReplyCommentVo> replyCommentList;

    private SecurityBlogUser blogUser;

    private String createTimeStr;

}

