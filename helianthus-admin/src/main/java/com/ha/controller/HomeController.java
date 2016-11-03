package com.ha.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 首页controller
 * User: shuiqing
 * DateTime: 16/8/18 下午1:55
 * Email: helianthus301@163.com
 * GitHub: https://github.com/shuiqing301
 * Blog: http://shuiqing301.github.io/
 * _
 * |_)._ _
 * | o| (_
 */
@Controller
public class HomeController {

    @RequestMapping("/index")
    public ModelAndView index() {
        return new ModelAndView("index");
    }

    /*@RequestMapping("/testCookie")
    @ResponseBody
    public ModelAndView testCookie(HttpServletRequest request,HttpServletResponse response) {
        request.getSession().setAttribute("openid","11111111");
        Cookie cookie = new Cookie("openid", (String)request.getSession().getAttribute("openid"));
        cookie.setMaxAge(3600*24*30);
        response.addCookie(cookie);
        return new ModelAndView("test");
    }*/

}