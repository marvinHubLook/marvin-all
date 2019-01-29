package cn.edu.system.service;

import cn.edu.config.ErrorInfoApiFallback;
import cn.edu.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author : bingo
 * @category : TODO
 * @Date : 2018/9/11 18:00
 **/
@FeignClient(value = "eureka-client",configuration = FeignConfig.class,fallback = ErrorInfoApiFallback.class)
public interface TestServiceApi {

    @RequestMapping("/test/hello")
    String testHello();
}
