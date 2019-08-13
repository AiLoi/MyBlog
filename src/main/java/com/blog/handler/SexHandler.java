package com.blog.handler;

import com.blog.entity.enums.EnumSex;
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
 * @description:
 * @author: Ailuoli
 * @create: 2019-03-28 20:04
 **/

@MappedJdbcTypes(JdbcType.INTEGER)
@MappedTypes(EnumSex.class)
public class SexHandler implements TypeHandler<EnumSex> {

    @Override
    public void setParameter(PreparedStatement preparedStatement, int i, EnumSex enumSex, JdbcType jdbcType) throws SQLException {
        preparedStatement.setInt(i,enumSex.getId());
    }

    @Override
    public EnumSex getResult(ResultSet resultSet, String s) throws SQLException {
        int code = resultSet.getInt(s);
        return EnumSex.getEnumById(code);
    }

    @Override
    public EnumSex getResult(ResultSet resultSet, int i) throws SQLException {
        int code = resultSet.getInt(i);
        return EnumSex.getEnumById(code);
    }

    @Override
    public EnumSex getResult(CallableStatement callableStatement, int i) throws SQLException {
        int code = callableStatement.getInt(i);
        return EnumSex.getEnumById(code);
    }
}

