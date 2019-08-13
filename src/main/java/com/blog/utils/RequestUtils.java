package com.blog.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * @program: blog
 * @description:
 * @author: Ailuoli
 * @create: 2019-05-24 21:16
 **/
public class RequestUtils {



    public static boolean isAjaxRequest(HttpServletRequest request){
        String header = request.getHeader("X-Requested-With");
        return "XMLHttpRequest".equals(header);
    }


}

