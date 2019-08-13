package com.blog.mapper;

import com.blog.entity.pojo.BlogComment;
import com.blog.entity.vo.BlogCommentVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface BlogCommentMapper {
    int deleteByPrimaryKey(Integer commentId);

    int insert(BlogComment record);

    int insertSelective(BlogComment record);

    BlogComment selectByPrimaryKey(Integer commentId);

    int updateByPrimaryKeySelective(BlogComment record);

    int updateByPrimaryKeyWithBLOBs(BlogComment record);

    int updateByPrimaryKey(BlogComment record);

    List<BlogCommentVo> selectBlogCommentByArticleId(int articleId) throws Exception;

    int insertComment(BlogComment blogComment)throws Exception;
}