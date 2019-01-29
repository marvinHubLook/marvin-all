package cn.edu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @Author : bingo
 * @category : TODO
 * @Date : 2018/9/5 14:38
 **/
@SpringBootApplication
@EnableEurekaServer // EurekaServer服务器端启动类,接受其它微服务注册进来
public class ServerApplication7000 {
    public static void main(String[] args) {
        SpringApplication.run(ServerApplication7000.class, args);
    }
}
