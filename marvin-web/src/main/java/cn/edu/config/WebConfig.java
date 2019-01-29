package cn.edu.config;

import cn.edu.utils.InterceptorDemo;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @Author : bingo
 * @category : TODO
 * @Date : 2018/7/28 16:37
 **/
@Configuration
public class WebConfig extends WebMvcConfigurationSupport {
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new InterceptorDemo())
                .addPathPatterns("/demo/**");
        super.addInterceptors(registry);
    }


}
