package cn.edu.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: marvin-all
 * @description: 服务降级
 * @author: Mr.Wang
 * @create: 2019-01-07 22:10
 **/
@RestController
public class FallbackController {

    @GetMapping("/fallback")
    public String fallback() {
        return "Hello World!\nfrom gateway limit";
    }
}
