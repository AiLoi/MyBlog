package com.blog.entity.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class ReplyComment {
    private Integer replyId;

    private Integer userId;

    private Integer replyUserId;

    private Integer commentId;

    private Date createTime;

    private Integer isDelete;

    private String replyContent;

}