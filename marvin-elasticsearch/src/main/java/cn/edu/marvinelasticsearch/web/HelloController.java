package cn.edu.marvinelasticsearch.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: marvin-all
 * @description: TODO
 * @author: Mr.Wang
 * @create: 2019-02-19 11:00
 **/
@RestController
@RequestMapping("/hello")
@Slf4j
public class HelloController {

    public String index(){
        log.info("hello :{}","info");
        return "hello";
    }

}
