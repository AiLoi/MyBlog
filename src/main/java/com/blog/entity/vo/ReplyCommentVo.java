package com.blog.entity.vo;

import com.blog.entity.pojo.ReplyComment;
import com.blog.security.SecurityBlogUser;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @program: blog
 * @description:
 * @author: Ailuoli
 * @create: 2019-05-23 15:46
 **/
@Accessors(chain = true)
@Data
public class ReplyCommentVo extends ReplyComment {

    private SecurityBlogUser blogUser;

    private SecurityBlogUser replyBlogUser;

    private String createTimeStr;
}

