package cn.edu.system.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.ribbon.proxy.annotation.Hystrix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @Author : bingo
 * @category : TODO
 * @Date : 2018/9/10 11:29
 **/
@Service
@Hystrix()
public class TestSerevice {
    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "hiError")
    public String testPort(){
        return restTemplate.getForObject("http://EUREKA-CLIENT/test/hello",String.class);
    }

    public String hiError(){
        return "sorry,error!";
    }
}
