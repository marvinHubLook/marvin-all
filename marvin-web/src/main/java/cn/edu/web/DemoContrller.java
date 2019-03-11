package cn.edu.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author : bingo
 * @category : TODO
 * @Date : 2018/7/28 15:38
 **/
@RestController
@RequestMapping("/demo")
public class DemoContrller {

    @GetMapping("/index")
    public String demoIndex(String message) throws InterruptedException {

        Thread.sleep(1000);
        return message;
    }
}
