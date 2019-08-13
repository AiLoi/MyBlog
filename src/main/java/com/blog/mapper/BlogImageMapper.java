package com.blog.mapper;

import com.blog.entity.pojo.BlogImage;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface BlogImageMapper {
    int deleteByPrimaryKey(Integer imageId);

    int insert(BlogImage record);

    int insertSelective(BlogImage record);

    BlogImage selectByPrimaryKey(Integer imageId);

    int updateByPrimaryKeySelective(BlogImage record);

    int updateByPrimaryKey(BlogImage record);

    /**
     * 插入图片
     */
    int insertArticleImage(BlogImage blogImage);

    /*
    根据名字获取图片path
     */
    String selectArticleImagePath(String imageName);

}