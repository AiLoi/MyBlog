package com.blog.service;

import com.blog.entity.pojo.BlogCourse;
import com.blog.entity.pojo.TechnologyStack;
import com.blog.mapper.BlogCourseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: MyBlog
 * @description: 查询右侧菜单数据
 * @author: Ailuoli
 * @create: 2019-03-10 23:25
 **/
@Service
public class TechnologyStackService {
    private final BlogCourseMapper blogCourseMapper;


    @Autowired
    public TechnologyStackService(BlogCourseMapper blogCourseMapper) throws Exception{
        this.blogCourseMapper = blogCourseMapper;
    }


    public List<BlogCourse> findLeftMenu(int menuId) throws Exception {
        return blogCourseMapper.selectLeftMenu(menuId);
    }


    public List<BlogCourse> findAllCourseName()throws Exception
    {
        return blogCourseMapper.selectAllCourseName();
    }


    public BlogCourse findCourseByName(String name)throws Exception
    {
        return blogCourseMapper.selectCourseByName(name);
    }

    public BlogCourse findCourseById(int id)throws Exception
    {
        return blogCourseMapper.selectCourseById(id);
    }

    public List<BlogCourse> likeFindCourseName(String keyword) throws  Exception
    {
        return blogCourseMapper.selectCourseLikeName(keyword);
    }

    public List<TechnologyStack> findAllTechnology()throws  Exception
    {
        return blogCourseMapper.selectAllTechnologyStack();
    }

    public List<TechnologyStack> findAllLanType() throws Exception
    {
        return blogCourseMapper.selectAllLanType();
    }
}

