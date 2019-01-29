package cn.edu;

import cn.edu.factory.ElapsedGatewayFilterFactory;
import cn.edu.filter.ElapsedFilter;
import cn.edu.filter.RateLimitByCpuGatewayFilter;
import cn.edu.filter.RateLimitByIpGatewayFilter;
import cn.edu.filter.TokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.filter.factory.HystrixGatewayFilterFactory;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

import java.time.Duration;

@SpringBootApplication
public class GatewayApplication {
    @Autowired
    private RateLimitByCpuGatewayFilter rateLimitByCpuGatewayFilter;

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    /**
     * @Description : 全局过滤器
     **/
    @Bean
    public TokenFilter tokenFilter(){
        return new TokenFilter();
    }

    @Bean
    public ElapsedGatewayFilterFactory elapsedGatewayFilterFactory() {
        return new ElapsedGatewayFilterFactory();
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder){
        return builder.routes()
                .route(r -> r.path("/feign/**")
                                .filters(f -> f.stripPrefix(1)
                                        .hystrix((HystrixGatewayFilterFactory.Config config) ->{
                                            config.setName("fallbackcmd");
                                            config.setFallbackUri("forward:/fallback");
                                        })
                                        .filter(new ElapsedFilter())  //局部
                                        .filter(new RateLimitByIpGatewayFilter(10,1,Duration.ofSeconds(1)))  //限流
                                        .filter(rateLimitByCpuGatewayFilter)   //CPU 使用率限流,慎用
                                        .addResponseHeader("X-Response-Default-Foo", "Default-Bar"))
                                .uri("http://localhost:9000")
                                .id("fluent_feign_service"))
                .build();
    }


}

