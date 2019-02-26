package cn.edu.config;

/**
 * @Author : bingo
 * @category : TODO
 * @Date : 2018/10/27 11:23
 **/

import org.springframework.context.annotation.Configuration;

/**
 * Redis配置
 */
@Configuration
/*@EnableCaching*/
public class RedisConfig {
    /*@Bean
    @ConfigurationProperties(prefix = "spring.redis.pool")
    public JedisPoolConfig getRedisConfig(){
        JedisPoolConfig config = new JedisPoolConfig();
        return config;
    }*/


    /**
     * 指明序列化方式
     * @param factory 工厂
     * @return
     */
    /*@Bean
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<String, String>();
        redisTemplate.setConnectionFactory(factory);
        RedisSerializer<String> redisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(redisSerializer);
        redisTemplate.setHashKeySerializer(redisSerializer);
        redisTemplate.setValueSerializer(redisSerializer);
        redisTemplate.setHashValueSerializer(redisSerializer);
        return redisTemplate;
    }*/

}
