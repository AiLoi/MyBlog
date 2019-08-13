package com.blog.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @program: MyBlog
 * @description: 视图映射器
 * @author: Ailuoli
 * @create: 2019-04-22 19:11
 **/
@Controller
public class ViewResetController {

    @RequestMapping("/")
    public ModelAndView def() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/html/index.html");
        return modelAndView;
    }

    @RequestMapping("/index")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/html/index.html");
        return modelAndView;
    }

    @RequestMapping("/user/login")
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/html/login.html");
        return modelAndView;
    }

    @RequestMapping("/403")
    public ModelAndView accessDenied() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/html/403.html");
        return modelAndView;
    }

    @RequestMapping("/technology")
    public ModelAndView technology() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/html/technology.html");
        return modelAndView;
    }

    @RequestMapping("/write")
    public ModelAndView write() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/html/write.html");
        return modelAndView;
    }

    @RequestMapping("/user/registered")
    public ModelAndView register() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/html/register.html");
        return modelAndView;
    }

    @RequestMapping("/article")
    public ModelAndView article() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/html/article.html");
        return modelAndView;
    }


    @RequestMapping("/404")
    public ModelAndView noPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/html/404.html");
        return modelAndView;
    }


    @RequestMapping("/article_details")
    public ModelAndView articleDetails() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/html/article_details.html");
        return modelAndView;
    }


    @RequestMapping(value = "/session/invalid")
    public void sessionTimeout(HttpServletRequest request, HttpServletResponse response) throws IOException {

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

