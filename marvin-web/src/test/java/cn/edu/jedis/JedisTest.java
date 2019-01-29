package cn.edu.jedis;

import cn.edu.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @Author : bingo
 * @category : TODO
 * @Date : 2018/10/27 11:02
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class JedisTest {
    @Autowired
    private JedisPool jedisPool;

    @Test
    public void testSet(){
        Jedis jedis = jedisPool.getResource();
        jedis.auth("bingo");
        String str = jedis.set("key", "value");
        System.out.printf("---"+str);
        jedis.close();
    }
}
