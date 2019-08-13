package com.blog.service;

import com.blog.mapper.BlogNoticeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: blog
 * @description:
 * @author: Ailuoli
 * @create: 2019-05-20 15:17
 **/

@Service
public class IndexPreviewService {

    @Autowired
    private BlogNoticeMapper blogNoticeMapper;



    public Map<String,Object> getRightPreview() throws Exception {

        Map<String ,Object> map = new HashMap<>();
        map.put("notice", blogNoticeMapper.selectBlogNotice());
        map.put("key_word",blogNoticeMapper.selectHotKetWord());
        map.put("article",blogNoticeMapper.selectNewArticle());
        return map;
    }
}

