package cn.redis.PS.base;

import redis.clients.jedis.Jedis;

/**
 * @Author : bingo
 * @category : TODO
 * @Date : 2018/9/20 16:52
 **/
public class Publisher {
    private Publisher(){};
    public static boolean addData(ChannelEnum channel, String data){
        Jedis jedis =null;
        try{
            jedis = JredisPoolTool.get();   //连接池中取出一个连接
            jedis.publish(channel.channel, data);   //从 mychannel 的频道上推送消息
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }
}
