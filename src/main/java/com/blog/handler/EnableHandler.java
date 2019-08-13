package com.blog.handler;

import com.blog.entity.enums.EnumEnable;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @program: MyBlog
 * @description: 激活转换类
 * @author: Ailuoli
 * @create: 2019-04-28 10:06
 **/
@MappedJdbcTypes(JdbcType.INTEGER)
@MappedTypes(EnumEnable.class)
public class EnableHandler implements TypeHandler<EnumEnable> {

    @Override
    public void setParameter(PreparedStatement preparedStatement, int i, EnumEnable enumEnable, JdbcType jdbcType) throws SQLException {
        preparedStatement.setInt(i,enumEnable.getId());
    }

    @Override
    public EnumEnable getResult(ResultSet resultSet, String s) throws SQLException {
        int code = resultSet.getInt(s);
        return EnumEnable.getEnumById(code);
    }

    @Override
    public EnumEnable getResult(ResultSet resultSet, int i) throws SQLException {

        int code = resultSet.getInt(i);
        return EnumEnable.getEnumById(code);
    }

    @Override
    public EnumEnable getResult(CallableStatement callableStatement, int i) throws SQLException {
        int code = callableStatement.getInt(i);
        return EnumEnable.getEnumById(code);
    }
}

