package cn.redis.PS.base;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @Author : bingo
 * @category : TODO
 * @Date : 2018/9/20 17:03
 **/
public class JredisPoolTool {
     private static JredisPoolTool poolTool=new JredisPoolTool();
     private static JedisPool jedisPool;
     private JredisPoolTool(){
         jedisPool=new JedisPool(new JedisPoolConfig(), "180.76.249.249", 6379);
     }
     public static Jedis get(){
         Jedis jedis=jedisPool.getResource();
         jedis.auth("bingo");
         return jedis;
     }
}
