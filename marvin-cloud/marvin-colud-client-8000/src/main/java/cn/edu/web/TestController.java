package cn.edu.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author : bingo
 * @category : TODO
 * @Date : 2018/9/5 15:17
 **/
@RestController
@RequestMapping("/test/")
public class TestController {
    @Value("${server.port}")
    private String port;

    @GetMapping("hello")
    public String hello(){
        return "this is port :"+port;
    }
}
