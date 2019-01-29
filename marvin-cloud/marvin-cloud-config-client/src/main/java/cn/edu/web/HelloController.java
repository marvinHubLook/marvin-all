package cn.edu.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: marvin-all
 * @description: TODO
 * @author: Mr.Wang
 * @create: 2019-01-03 16:26
 **/
@RestController
@RequestMapping("/config")
public class HelloController {
    @Value("${democonfigclient.message}")
    String message;

    @RequestMapping(value = "/message")
    public String hi(){
        return message;
    }
}
