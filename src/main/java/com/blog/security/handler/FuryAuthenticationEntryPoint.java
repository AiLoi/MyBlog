package com.blog.security.handler;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @program: blog
 * @description:
 * @author: Ailuoli
 * @create: 2019-05-16 12:19
 **/

@Component
public class FuryAuthenticationEntryPoint implements AuthenticationEntryPoint {



    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        String type = request.getHeader("X-Requested-With")==null?"":request.getHeader("X-Requested-With");

        if("XMLHttpRequest".equals(type)) {
            //设置响应头为重定向
            response.setHeader("REDIRECT", "REDIRECT");
            response.setHeader("CONTEXTPATH",request.getContextPath()+"/user/login");
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
        else {
            response.sendRedirect(request.getContextPath()+"/user/login");
        }

    }
}

