package com.blog.controller;

import com.blog.entity.vo.BlogArticleVo;
import com.blog.entity.vo.BlogCommentVo;
import com.blog.entity.vo.ReplyCommentVo;
import com.blog.service.WriteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: blog
 * @description: 写博客接口
 * @author: Ailuoli
 * @create: 2019-05-13 20:43
 **/

@Api(tags = "博客管理")
@RestController
@RequestMapping("/write")
public class WriteController {


    private final WriteService writeService;

    public WriteController(WriteService writeService) {
        this.writeService = writeService;
    }


    /**
     * @param article 博客实体类
     * @return bool
     * @description: 写博客功能实现
     */

    @ApiOperation("写博客")
    @PostMapping("/insert_article")
    public boolean isInsertArticle(@RequestBody BlogArticleVo article) {
        try {
            article.setUserId(article.getBlogUser().getUserId());
            return writeService.addArticle(article);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    @ApiOperation("/首页展示博客")
    @GetMapping("/get_index_article")
    public List<BlogArticleVo> getIndexBlogArticle() {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
        List<BlogArticleVo> resultList = new ArrayList<>();
        try {
            resultList = writeService.getIndexArticle();
            for (BlogArticleVo temp : resultList) {
                temp.setCreateTimeStr(simpleDateFormat.format(temp.getCreateTime()));
                temp.setMarkdownContent(temp.getMarkdownContent().substring(0, 200));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultList;
    }

    @ApiOperation("获取文章具体内容")
    @PostMapping("/get_article_details")
    public BlogArticleVo getArticleDetails(int articleId) {
        try {
            return writeService.getArticleDetails(articleId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @ApiOperation("评论")
    @PostMapping("/add_comment")
    public BlogCommentVo addComment(@RequestBody BlogCommentVo blogComment) {
        try {
            return writeService.addComment(blogComment);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @ApiOperation("回复")
    @PostMapping("/add_reply_comment")
    public ReplyCommentVo addReplyComment(@RequestBody ReplyCommentVo replyComment) {
        try {
            return writeService.addReplyComment(replyComment);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @PostMapping("/get_article_like_keyword")
    public List<BlogArticleVo> getArticleLikeKeyWord(String keyWord,int articleId) {
        try {
            return writeService.getArticleVoLikeKeyWord(keyWord,articleId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

