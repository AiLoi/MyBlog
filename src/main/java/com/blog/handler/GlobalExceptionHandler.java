package com.blog.handler;

import com.alibaba.fastjson.JSONObject;
import com.blog.entity.enums.EnumErrorMessage;
import com.blog.utils.LogicException;
import com.blog.utils.RestResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @program: MyBlog
 * @description: 统一处理异常
 * @author: Ailuoli
 * @create: 2019-05-01 20:16
 **/

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    public Object logicExceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception e) {
        //系统级异常，错误代码固定为1，提示语固定为系统繁忙
        RestResult result = new RestResult(false, EnumErrorMessage.SYSTEM_EXCEPTION.getId().toString(),e.getMessage(), EnumErrorMessage.SYSTEM_EXCEPTION.getName());
        //如果是逻辑异常
        if(e instanceof LogicException) {
            LogicException logicException = (LogicException)e;
            result.setCode(logicException.getCode());
            result.setErrorMessage(logicException.getErrorMsg());
        }else {
            //对系统日志一行进行日志记录
            log.error("系统异常:"+e.getMessage(),e);
        }
        return JSONObject.toJSON(result);
    }
}

