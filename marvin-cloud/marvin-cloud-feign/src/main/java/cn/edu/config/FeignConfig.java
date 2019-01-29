package cn.edu.config;

import feign.Request;
import feign.Retryer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * @program: marvin-all
 * @description: TODO
 * @author: Mr.Wang
 * @create: 2019-01-03 17:16
 **/
@Configuration
public class FeignConfig {
    public static int connectTimeOutMillis = 5000;// 连接超时时长
    public static int readTimeOutMillis = 2000;  // 响应超时时长


    @Bean
    public Request.Options options() {
        return new Request.Options(connectTimeOutMillis, readTimeOutMillis);
    }

    @Bean
    public Retryer feignRetryer(){
        /**默认 :  自定义重试次数 */
        return new Retryer.Default(100L, TimeUnit.SECONDS.toMillis(1L), 5);
    }
}
