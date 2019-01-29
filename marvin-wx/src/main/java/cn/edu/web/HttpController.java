package cn.edu.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @program: marvin-all
 * @description: http api 接口测试
 * @author: Mr.Wang
 * @create: 2018-12-11 19:41
 **/
@RequestMapping("/http")
@Controller
public class HttpController {
    String html="<html><head><meta charset=\"UTF-8\"><Meta http-equiv=\"Content-Type\" Content=\"text/html; Charset=utf-8\"></head><body>这是body</body></html>";

    @RequestMapping("/context1")
    public void testContext1(HttpServletResponse response){
        response.addCookie(new Cookie("cookie1","value1"));
        response.setContentType("text/html; Charset=utf-8");
        PrintWriter out=null;
        try {
             out= response.getWriter();
             out.write(html);
             out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            out.close();
        }
    }

    @RequestMapping("/context2")
    @ResponseBody
    public String testContext2(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        for(Cookie cookie:cookies){
            System.out.println(cookie.getName()+"--"+cookie.getValue());
        }
        return "ok";
    }

    @RequestMapping("/interceptor")
    @ResponseBody
    public String tesetInterceptor(HttpServletRequest request){
        String count = request.getHeader("Count");
        System.out.println("interceptor--count:"+count);
        return "ok";
    }

    @RequestMapping("/redirect")
    public void testRedirect(HttpServletResponse response) throws IOException {
        response.sendRedirect("/http/context1");
    }

    @PostMapping("/login")
    public String login(@RequestParam(required = true) String name,@RequestParam(required = true) String password,
            HttpServletRequest request){
        if(name.equals("admin") && password.equals("123")){
            return "ok";
        }
        return "no";
    }



}
