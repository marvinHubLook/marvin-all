package jedis;

import cn.jedis.ins.cluster.JedisClusterProvider;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.io.IOException;

/**
 * @program: marvin-all
 * @description: TODO
 * @author: Mr.Wang
 * @create: 2018-11-29 14:56
 **/
public class TestJedisCluster {
    private JedisClusterProvider jedisProvider;
    @Before
    public void init(){
        String[] ports=new String[]{
            "47.96.107.63:9700",
            "47.96.107.63:9701",
            "47.96.107.63:9702",
            "47.96.107.63:9703",
            "47.96.107.63:9704",
            "47.96.107.63:9705"
        };
        // Jedis连接池配置
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        // 最大空闲连接数, 默认8个
        jedisPoolConfig.setMaxIdle(100);
        // 最大连接数, 默认8个
        jedisPoolConfig.setMaxTotal(500);
        //最小空闲连接数, 默认0
        jedisPoolConfig.setMinIdle(0);
        // 获取连接时的最大等待毫秒数(如果设置为阻塞时BlockWhenExhausted),如果超时就抛异常, 小于零:阻塞不确定的时间,  默认-1
        jedisPoolConfig.setMaxWaitMillis(2000); // 设置2秒
        //对拿到的connection进行validateObject校验
        jedisPoolConfig.setTestOnBorrow(true);

        jedisProvider=new JedisClusterProvider("test",jedisPoolConfig,ports,2000);
    }
    @After
    public void destroy() throws IOException {
        if(null!=jedisProvider) jedisProvider.destroy();
    }

    @Test
    public void testSet() throws IOException {
        JedisCluster jedisCluster = jedisProvider.get();
        jedisCluster.set("test","val");
    }

}
