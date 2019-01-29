package cn.edu.web;

import cn.edu.system.service.TestSerevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author : bingo
 * @category : TODO
 * @Date : 2018/9/10 11:36
 **/
@RestController
@RequestMapping(value = "/test/")
public class TestController {
    @Autowired
    private TestSerevice testSerevice;
    @RequestMapping("hello")
    public String testHello(){
        return testSerevice.testPort();
    }

}
