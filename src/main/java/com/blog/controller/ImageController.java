package com.blog.controller;

import com.alibaba.fastjson.JSONObject;
import com.blog.entity.pojo.BlogImage;
import com.blog.service.WriteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @program: MyBlog
 * @description: 写博客模块
 * @author: Ailuoli
 * @create: 2019-03-28 18:41
 **/

@Api(tags = "图片上传下载")
@RequestMapping("/image")
@Controller
public class ImageController {


    private final WriteService writeService;

    public ImageController(WriteService writeService) {
        this.writeService = writeService;
    }

    @Value("${server.port}")
    private String port;

    @Value("${article.imagePath}")
    private String imagePath;

    /**
     * @param file 获取到的文件对象
     * @return map固定的key ,value
     * @description: 实现写博客时图片的上传功能
     */

    @ApiOperation("图片上传")
    @PostMapping(value = "/insert_article_image", produces = "application/json;charset=UTF-8")
    public void insertArticleImage(@RequestParam(value = "editormd-image-file") MultipartFile file, HttpServletResponse response) throws UnsupportedEncodingException {
        String url;
        JSONObject resultMap = new JSONObject();
        String fileName = file.getOriginalFilename();
        //加一个时间戳 ，比秒文件名重复
        fileName = URLEncoder.encode(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "_" + fileName, "UTF-8");
        //文件要被存储的位置
        String path = imagePath + fileName;
        File dest = new File(path);
        try {
            //保存图片
            file.transferTo(dest);
            //生成访问路径（图片的下载接口
//            url = "https://" + InetAddress.getLocalHost().getHostAddress() + ":" + port + "/image/get_image?fileName=" + fileName;
            url = "https://" +"ailuoli.cn" +"/image/get_image?fileName=" + fileName;
            BlogImage image = new BlogImage();
            image.setImageName(fileName);
            image.setImagePath(path);
            image.setImageUrl(url);
            if (writeService.insertArticleImage(image)) {
                resultMap.put("success", 1);
                resultMap.put("message", "上传成功!");
                resultMap.put("url", url);
            } else {
                resultMap.put("success", 0);
                resultMap.put("message", "上传失败！");
            }

        } catch (IOException e) {
            resultMap.put("success", 0);
            resultMap.put("message", "上传失败！");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        response.setCharacterEncoding("UTF-8");
        try {
            response.getWriter().write(String.valueOf(resultMap));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    /**
     * @param fileName 文件名
     * @description: markdown图片预览功能
     */

    @ApiOperation("图片下载")
    @GetMapping("/get_image")
    public void getImage(String fileName, HttpServletResponse response) throws UnsupportedEncodingException {

        String path;
        byte[] buffer = new byte[1024];
        FileInputStream fileInputStream = null;
        BufferedInputStream bufferedInputStream = null;
        fileName = URLEncoder.encode(fileName, "UTF-8");
        try {
            path = writeService.findImagePath(fileName);
            File file = new File(path);
            if (file.exists()) {
                fileInputStream = new FileInputStream(file);
                bufferedInputStream = new BufferedInputStream(fileInputStream);
                OutputStream outputStream = response.getOutputStream();
                int i = bufferedInputStream.read(buffer);
                while (i != -1) {
                    outputStream.write(buffer, 0, i);
                    i = bufferedInputStream.read(buffer);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {  //关闭输出流
            if (bufferedInputStream != null) {
                try {
                    bufferedInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}

