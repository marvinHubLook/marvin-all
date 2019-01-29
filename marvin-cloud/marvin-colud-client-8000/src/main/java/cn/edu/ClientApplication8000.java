package cn.edu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @Author : bingo
 * @category : TODO
 * @Date : 2018/9/5 15:08
 **/
@SpringBootApplication
@EnableEurekaClient //本服务启动后会自动注册进eureka服务中
@EnableDiscoveryClient //服务发现
public class ClientApplication8000 {
    public static void main(String[] args) {
        SpringApplication.run(ClientApplication8000.class, args);
    }
}
