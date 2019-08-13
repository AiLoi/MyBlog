package com.blog.service;

import com.blog.entity.pojo.BlogImage;
import com.blog.entity.vo.BlogArticleVo;
import com.blog.entity.vo.BlogCommentVo;
import com.blog.entity.vo.ReplyCommentVo;
import com.blog.mapper.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: MyBlog
 * @description:
 * @author: Ailuoli
 * @create: 2019-03-28 20:12
 **/

@Service
@Slf4j
public class WriteService {


    @Autowired
    private BlogImageMapper blogImageMapper;

    @Autowired
    private BlogArticleMapper blogArticleMapper;

    @Autowired
    private BlogUserMapper blogUserMapper;

    @Autowired
    private BlogCommentMapper blogCommentMapper;

    @Autowired
    private ReplyCommentMapper replyCommentMapper;


    public boolean insertArticleImage(BlogImage image) throws Exception {
        return blogImageMapper.insertArticleImage(image) > 0;
    }


    public String findImagePath(String imageName) throws Exception {
        return blogImageMapper.selectArticleImagePath(imageName);
    }

    public boolean addArticle(BlogArticleVo article) throws Exception {
        return blogArticleMapper.insertSelective(article) > 0;
    }

    public List<BlogArticleVo> getIndexArticle() throws Exception {
        List<BlogArticleVo> blogArticleVo = blogArticleMapper.selectIndexBlogArticle();
        for (BlogArticleVo temp : blogArticleVo) {
            temp.setBlogUser(blogUserMapper.selectBlogUserById(temp.getUserId()));
        }

        return blogArticleVo;
    }

    /**
     * @param articleId 文章id
     * @return 详情
     * @throws Exception 抛出异常
     * @description: 获取文章的详情
     */
    public BlogArticleVo getArticleDetails(int articleId) throws Exception {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
        //获取文章基本信息
        BlogArticleVo blogArticleVo = blogArticleMapper.selectBLogArticleById(articleId);
        //获取作者信息
        blogArticleVo.setBlogUser(blogUserMapper.selectBlogUserById(blogArticleVo.getUserId()));
        //转时间为字符串
        blogArticleVo.setCreateTimeStr(simpleDateFormat.format(blogArticleVo.getCreateTime()));

        //获取一级评论
        List<BlogCommentVo> list = blogCommentMapper.selectBlogCommentByArticleId(blogArticleVo.getArticleId());

        if (list.size() > 0) {
            for (BlogCommentVo blogCommentVo : list) {
                //对一级评论时间进行处理
                blogCommentVo.setCreateTimeStr(simpleDateFormat.format(blogCommentVo.getCreateTime()));

                //获取一级评论用户信息
                blogCommentVo.setBlogUser(blogUserMapper.selectBlogUserById(blogCommentVo.getUserId()));

                //获取二级评论
                List<ReplyCommentVo> replyCommentVoList = replyCommentMapper.selectReplyCommentByCommentId(blogCommentVo.getCommentId());
                if (replyCommentVoList.size() > 0) {
                    //对二级评论时间进行转换
                    for (ReplyCommentVo replyCommentVo : replyCommentVoList) {
                        replyCommentVo.setCreateTimeStr(simpleDateFormat.format(replyCommentVo.getCreateTime()));
                        replyCommentVo.setBlogUser(blogUserMapper.selectBlogUserById(replyCommentVo.getUserId()));
                        replyCommentVo.setReplyBlogUser(blogUserMapper.selectBlogUserById(replyCommentVo.getReplyUserId()));
                    }
                    blogCommentVo.setReplyCommentList(replyCommentVoList);
                }

            }
        }

        blogArticleVo.setBlogCommentVoList(list);

        //给文章浏览数量增加
        blogArticleMapper.updateArticleVisitCount(articleId);

        return blogArticleVo;
    }


    /**
     * @param blogComment 评论实体类
     * @return bool
     * @description: 添加评论
     */
    public BlogCommentVo addComment(BlogCommentVo blogComment) throws Exception {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd:HH:mm:ss");
        if (blogCommentMapper.insertComment(blogComment) == 1) {
            blogComment.setCreateTimeStr(simpleDateFormat.format(new Date()));
            blogComment.setBlogUser(blogUserMapper.selectBlogUserById(blogComment.getUserId()));
        }

        //给文章评论数+1
        blogArticleMapper.updateArticleCommentCount(blogComment.getArticleId());
        return blogComment;
    }


    public ReplyCommentVo addReplyComment(ReplyCommentVo replyComment) throws Exception {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd:HH:mm:ss");
        if (replyCommentMapper.insertReplyComment(replyComment) == 1) {
            replyComment.setCreateTimeStr(simpleDateFormat.format(new Date()));
            replyComment.setBlogUser(blogUserMapper.selectBlogUserById(replyComment.getUserId()));
            replyComment.setReplyBlogUser(blogUserMapper.selectBlogUserById(replyComment.getReplyUserId()));
        }

        //给文章评论数+1
        blogArticleMapper.updateArticleCommentCountForReply(replyComment.getReplyId());
        return replyComment;
    }

    public List<BlogArticleVo> getArticleVoLikeKeyWord(String keyWord,int articleId) throws Exception {

        String[] listStr = keyWord.split(",");
        StringBuilder keyWords = new StringBuilder();
        for (String temp : listStr) {
            keyWords.append(temp);
            keyWords.append("|");
        }

        String para = keyWords.substring(0, keyWords.toString().length() - 1);

        Map<String,Object> map = new HashMap<>();
        map.put("keyWords",para);
        map.put("articleId",articleId);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd:HH:mm:ss");
        List<BlogArticleVo> list = blogArticleMapper.selectBlogArticleLikeKeyWord(map);
        if(list.size()>0) {
            list.forEach(e -> {
                e.setCreateTimeStr(simpleDateFormat.format(e.getCreateTime()));
                e.setMarkdownContent(e.getMarkdownContent().substring(0, 200));
            });
        }

        return list;
    }

}

