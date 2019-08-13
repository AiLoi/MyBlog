package com.blog.mapper;

import com.blog.entity.pojo.BlogArticle;
import com.blog.entity.pojo.BlogNotice;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface BlogNoticeMapper {
    int deleteByPrimaryKey(Integer noticeId);

    int insert(BlogNotice record);

    int insertSelective(BlogNotice record);

    BlogNotice selectByPrimaryKey(Integer noticeId);

    int updateByPrimaryKeySelective(BlogNotice record);

    int updateByPrimaryKeyWithBLOBs(BlogNotice record);

    int updateByPrimaryKey(BlogNotice record);

    /*
    获取公告
     */
    BlogNotice selectBlogNotice() throws Exception;

    /*
    获取关键字
     */
    List<String> selectHotKetWord() throws Exception;

    /*
    获取最新文章
     */
    List<BlogArticle> selectNewArticle() throws Exception;
}