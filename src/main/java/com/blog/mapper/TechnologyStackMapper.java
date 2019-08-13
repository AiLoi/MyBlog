package com.blog.mapper;

import com.blog.entity.pojo.TechnologyStack;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface TechnologyStackMapper {
    int deleteByPrimaryKey(Integer lanId);

    int insert(TechnologyStack record);

    int insertSelective(TechnologyStack record);

    TechnologyStack selectByPrimaryKey(Integer lanId);

    int updateByPrimaryKeySelective(TechnologyStack record);

    int updateByPrimaryKey(TechnologyStack record);
}