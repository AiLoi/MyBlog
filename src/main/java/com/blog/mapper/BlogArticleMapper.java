package com.blog.mapper;

import com.blog.entity.pojo.BlogArticle;
import com.blog.entity.vo.BlogArticleVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface BlogArticleMapper {
    int deleteByPrimaryKey(Integer articleId);

    int insert(BlogArticle record);

    int insertSelective(BlogArticleVo record) throws Exception;

    BlogArticle selectByPrimaryKey(Integer articleId);

    int updateByPrimaryKeySelective(BlogArticle record);

    int updateByPrimaryKeyWithBLOBs(BlogArticle record);

    int updateByPrimaryKey(BlogArticle record);




    /**
     * @description: 查询主页推荐文章
     */
    @Transactional
    List<BlogArticleVo> selectIndexBlogArticle() throws Exception;

    /**
     * @description: 根据文章id查询文章信息及相关信息
     */
    BlogArticleVo selectBLogArticleById(int articleId) throws Exception;

    /**
     * @description: 更新文章浏览数量
     */
    int updateArticleVisitCount(int articleId) throws Exception;

    /**
     * @description: 更新文章评论数量
     */
    int updateArticleCommentCount(int articleId) throws Exception;

    /**
     * @description: 更新文章评论数量（回复）
     */
    int updateArticleCommentCountForReply(int replyId) throws Exception;

    /**
     * @description: 获取推荐文章，根据关键字
     * @param keyWords 关键字
     */
    List<BlogArticleVo> selectBlogArticleLikeKeyWord(Map<String,Object> keyWords) throws Exception;
}