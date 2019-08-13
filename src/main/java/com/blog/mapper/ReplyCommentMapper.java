package com.blog.mapper;

import com.blog.entity.pojo.ReplyComment;
import com.blog.entity.vo.ReplyCommentVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ReplyCommentMapper {
    int deleteByPrimaryKey(Integer replyId);

    int insert(ReplyComment record);

    int insertSelective(ReplyComment record);

    ReplyComment selectByPrimaryKey(Integer replyId);

    int updateByPrimaryKeySelective(ReplyComment record);

    int updateByPrimaryKeyWithBLOBs(ReplyComment record);

    int updateByPrimaryKey(ReplyComment record);

    List<ReplyCommentVo> selectReplyCommentByCommentId(int commentId);

    int insertReplyComment(ReplyComment replyComment);
}