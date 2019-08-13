package com.blog.mapper;

import com.blog.entity.pojo.BlogCourse;
import com.blog.entity.pojo.TechnologyStack;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface BlogCourseMapper {
    int deleteByPrimaryKey(Integer courseId);

    int insert(BlogCourse record);

    int insertSelective(BlogCourse record);

    BlogCourse selectByPrimaryKey(Integer courseId);

    int updateByPrimaryKeySelective(BlogCourse record);

    int updateByPrimaryKeyWithBLOBs(BlogCourse record);

    int updateByPrimaryKey(BlogCourse record);

    /**
     * 根据 菜单id获取菜单
     * @param menuId id
     */
    List<BlogCourse> selectLeftMenu(int menuId);


    List<BlogCourse> selectAllCourseName();

    BlogCourse selectCourseByName(String name);

    BlogCourse selectCourseById(int id);

    List<BlogCourse> selectCourseLikeName(String keyword);

    List<TechnologyStack> selectAllTechnologyStack();

    List<TechnologyStack> selectAllLanType();
}