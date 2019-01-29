package cn.redis.PS.base;

import cn.redis.PS.inter.TaskInface;
import redis.clients.jedis.JedisPubSub;

/**
 * @Author : bingo
 * @category : TODO
 * @Date : 2018/9/20 16:42
 **/
public class Subscriber extends JedisPubSub {
    private TaskInface taskInface;

    public Subscriber(TaskInface taskInface){
        this.taskInface=taskInface;
    }
    @Override
    public void onMessage(String channel, String message) {       //收到消息会调用
        taskInface.onPost(channel,message);
    }
    @Override
    public void onSubscribe(String channel, int subscribedChannels) {    //订阅了频道会调用
        taskInface.onJoin(channel,subscribedChannels);
    }
    @Override
    public void onUnsubscribe(String channel, int subscribedChannels) {   //取消订阅 会调用
        taskInface.onLost(channel,subscribedChannels);
    }
}