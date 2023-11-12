package com.chuhezhe.commonservlet.controller;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * ClassName: PageController
 * Package: com.chuhezhe.commonservlet.controller
 * Description:
 *
 * @Author Chuhezhe
 * @Create 2023/11/12 17:22
 * @Version 1.0
 */
@Controller
public class PageController {
    @RequestMapping("/")
    public String home() {return "index.html";}

    @RequestMapping("/cookie")
    public void testCookie(HttpServletRequest request, HttpServletResponse response) {
        Cookie cookie1 = new Cookie("name1", "admin1");
        cookie1.setMaxAge(-1); // -1 表示只在浏览器内存中存活，关闭浏览器失效。（默认值-1）
        response.addCookie(cookie1);

        Cookie cookie2 = new Cookie("name2", "admin2");
        cookie2.setMaxAge(30); // 正整数 表示存货指定秒数，单位秒
        response.addCookie(cookie2);

        Cookie cookie3 = new Cookie("name3", "admin3");
        cookie3.setMaxAge(0); // 0 表示删除此cookie
        response.addCookie(cookie3);
    }

    @RequestMapping("/cookie01")
    public void cookie01(HttpServletRequest request, HttpServletResponse response) {
        Cookie cookie01 = new Cookie("cookie01", "cookie01");
        // 设置路径为"/"，表示当前服务器下任何项目都可以访问到Cookie对象
        cookie01.setPath("/");
        response.addCookie(cookie01);

        Cookie cookie02 = new Cookie("cookie02", "admin2");
        // 默认不设置Cookie的path 或者 设置为当前站点名
        cookie02.setPath("/cookie01");
        response.addCookie(cookie02);

        Cookie cookie03 = new Cookie("cookie03", "admin3");
        // 设置指定项目的站点名
        cookie03.setPath("/cookie02");
        response.addCookie(cookie03);

        Cookie cookie04 = new Cookie("cookie04", "admin4");
        // 设置指定项目的站点名
        cookie04.setPath("/cookie01/01");
        response.addCookie(cookie04);
    }

    @RequestMapping("/session01")
    public void testSession(HttpServletRequest request, HttpServletResponse response) {
        // 获取session对象
        HttpSession session = request.getSession();

        // 获取Session的会话标识符
        String id = session.getId();
        System.out.println(id);

        // session的创建时间
        System.out.println(session.getCreationTime());

        // session最后一次访问的时间
        System.out.println(session.getLastAccessedTime());

        // 判断是否是新的session对象
        System.out.println(session.isNew());
    }

    /**
     * 1 @RestController = @Controller + @ResponseBody
     * 不使用@ResponseBody或者@RestController 注解时，spring会将返回值作为url，然后跳转到对应url的页面
     * 2 @ResponseBody 意为不需要进行页面跳转直接返回数据
     */
    @ResponseBody
    @RequestMapping("/session02")
    public String testSession02(HttpServletRequest request, HttpServletResponse response) {
        // session域对象
        HttpSession session = request.getSession();

        // 设置session域对象
        session.setAttribute("uname", "xiaozhang");
        session.setAttribute("upwd", "1111");

        // 移除session域对象
        session.removeAttribute("upwd");

        // 设置request域对象
        request.setAttribute("name", "request");

        // 获取session域对象
        String uname = (String) session.getAttribute("uname");
        String upwd = (String) session.getAttribute("upwd");

        // 设置session最大不活动时间 单位秒, 如果15秒内携带session.getId()发送了请求，则重新计时
        // 也可以在application.yml中设置 server.servlet.session.timeout = 3600 单位秒 设置为0则永不失效
        // session.invalidate(); 立即销毁session
        session.setMaxInactiveInterval(15);
        System.out.println(session.getMaxInactiveInterval());
        System.out.println(session.getServletContext());

        // 获取request域对象
        String name = (String) request.getAttribute("name");

        return uname + "<br />"
                + upwd + "<br />"
                + name;
    }

    @ResponseBody
    @RequestMapping("/servletCtx")
    public String testServletContext(HttpServletRequest request, HttpServletResponse response) {
        // 通过request对象获取
        ServletContext servletContext1 = request.getServletContext();

        // 通过session对象获取
        ServletContext servletContext2 = request.getSession().getServletContext();

        // 常用方法
        // 1、获取当前服务器的版本信息
        String serverInfo = servletContext1.getServerInfo();

        // 2、获取项目的真实路径
        String realPath = servletContext1.getRealPath("/");

        return "获取当前服务器的版本信息: " + "<br />" + serverInfo + "<br />"
                + "获取项目的真实路径: " + "<br />" + realPath;
    }
}
