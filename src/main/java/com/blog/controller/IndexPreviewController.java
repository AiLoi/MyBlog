package com.blog.controller;

import com.blog.service.IndexPreviewService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @program: blog
 * @description: 主页
 * @author: Ailuoli
 * @create: 2019-05-20 15:15
 **/

@Api(tags = "主页展示")
@RestController
@RequestMapping("/index/preview")
public class IndexPreviewController {


    @Autowired
    private IndexPreviewService indexPreviewService;


    @ApiOperation("获取主页展示数据")
    @GetMapping("/get_right_preview")
    public Map<String, Object> getRightPreview() {
        try {
            return indexPreviewService.getRightPreview();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

