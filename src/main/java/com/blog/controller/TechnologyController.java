package com.blog.controller;

import com.blog.entity.pojo.BlogCourse;
import com.blog.entity.pojo.TechnologyStack;
import com.blog.service.TechnologyStackService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: MyBlog
 * @description: 查询右侧菜单数据
 * @author: Ailuoli
 * @create: 2019-03-10 23:27
 **/
@SuppressWarnings("ALL")

@Api(tags = "技术栈管理")
@RestController
@RequestMapping("/technology")
public class TechnologyController {


    private static final Logger logger = LoggerFactory.getLogger(TechnologyController.class);

    private final TechnologyStackService technologyService;

    private final RedisTemplate redisTemplate;

    @Autowired
    public TechnologyController(TechnologyStackService technologyService, RedisTemplate redisTemplate) {
        this.technologyService = technologyService;
        this.redisTemplate = redisTemplate;
    }


    /**
     * @param menuId 要显示的模块id
     * @return list
     * @author: Ailuoli
     * @description: 显示左侧菜单
     */
    @ApiOperation("获取技术栈左侧菜单")
    @GetMapping("/get_left_menu")
    public List<BlogCourse> getLeftMenu(int menuId) {
        try {
            return technologyService.findLeftMenu(menuId);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("左侧菜单", e.getMessage());
        }
        return null;
    }

    /**
     * @param courseId 要显示的文章的id
     * @return 文章内容
     * @author: Ailuoli
     * @description: 显示技术栈内容
     */
    @ApiOperation("获取技术栈右侧内容")
    @GetMapping("/get_course_value")
    public BlogCourse getCourseValue(int courseId) {
        BlogCourse resultCourse = new BlogCourse();
        String key = String.valueOf(courseId);
        //说明有数据
        if (redisTemplate.opsForValue().get(key) != null) {
            //通过key取数据
            resultCourse = (BlogCourse) redisTemplate.opsForValue().get(key);
        }
        //如果没有数据
        else {
            try {
                //获取数据
                BlogCourse course = technologyService.findCourseById(courseId);
                redisTemplate.opsForValue().set(course.getCourseId().toString(), course);
                resultCourse = (BlogCourse) redisTemplate.opsForValue().get(key);
            } catch (Exception e) {
                e.printStackTrace();
                logger.info("获取文章信息", e.getMessage());
            }
        }
        return resultCourse;
    }


    /**
     * @param keyWord 关键字
     * @return list
     * @description: 模糊搜索文章名字
     */

    @ApiOperation("获取技术栈技术标题")
    @GetMapping("/get_course_name")
    public List<BlogCourse> getCourseName(String keyWord) {

        if (keyWord == null || "".equals(keyWord))
            keyWord = "";
        BoundListOperations boundListOperations = redisTemplate.boundListOps("courseName");
        //绑定链表courseName;
        List<BlogCourse> tempList = boundListOperations.range(0, boundListOperations.size() - 1);
        List<BlogCourse> resultslist;
        List<BlogCourse> list = null;
        //redis有数据
        if (tempList != null && tempList.size() > 0) {
            //进行关键字匹配
            resultslist = listMatch(keyWord, tempList);
            //如果redis没有匹配关键字
            if (resultslist == null || resultslist.size() == 0) {
                try {
                    List<BlogCourse> addlist = technologyService.likeFindCourseName(keyWord);
                    //将新查到的list添加到redis
                    if (addlist != null && addlist.size() > 0) {
                        boundListOperations.rightPushAll(addlist);
                        //同时将新的文章添加到redis中；
                        addlist.forEach((e) ->
                        {
                            try {
                                BlogCourse course = technologyService.findCourseById(e.getCourseId());
                                if (course.getCourseId() != null) {
                                    redisTemplate.opsForValue().set(e.getCourseId(), course);
                                }
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.info("模糊列表" + e.getMessage());
                }
                tempList = boundListOperations.range(0, boundListOperations.size() - 1);
                resultslist = listMatch(keyWord, tempList);
            }
        } else {
            try {
                //获取所有课程名
                list = technologyService.findAllCourseName();
            } catch (Exception e) {
                e.printStackTrace();
                logger.info("模糊列表" + e.getMessage());
            }
            //进行redis缓存
            if (list != null && list.size() > 0) {
                redisTemplate.opsForList().rightPushAll("courseName", list);
                boundListOperations = redisTemplate.boundListOps("courseName");
                tempList = boundListOperations.range(0, boundListOperations.size() - 1);
                resultslist = listMatch(keyWord, tempList);
            } else
                return null;

        }
        return resultslist;

    }

    /**
     * @param name     文章名字
     * @return 文章
     * @description: 通过文章名字搜索文章内容
     */

    @ApiOperation("通过技术栈标题获取技术内容")
    @GetMapping("/get_course_by_name")
    public BlogCourse getCourseByName(String name) {
        try {
            return getCourseValue(technologyService.findCourseByName(name).getCourseId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * @description: 获取技术栈的主菜单
     * @return list
     */
    @ApiOperation("获取技术栈主菜单")
    @GetMapping("/get_menu_technology")
    public List<TechnologyStack> getMenuTechnology()
    {
        try {
            return technologyService.findAllTechnology();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @ApiOperation("获取技术栈技术栈种类")
    @GetMapping("/get_menu_lan_type")
    public List<TechnologyStack> getMenuLanType()
    {
        try {
            return technologyService.findAllLanType();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



    //list数据模糊匹配
    private static List<BlogCourse> listMatch(String keyWord, List<BlogCourse> tempList) {
        //将list中的数据进行模糊匹配
        if (tempList != null && tempList.size() > 0) {
            List<BlogCourse> results = new ArrayList<>();
            tempList.forEach((e) -> {
                if (e.getCourseName().contains(keyWord))
                    results.add(e);
            });
            return results;
        }
        return null;
    }





//    //使用restTemplate进行http get请求
//
//    @GetMapping("/rest")
//    public String getCourse() {
//        RestTemplate restTemplate = new RestTemplate();
//
//        String s = restTemplate.getForObject("http://192.168.3.80:9527/technology/showCourseValue?courseId=1", String.class);
//
//        System.out.println(s);
//        return s;
//    }

}

