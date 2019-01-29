package cn.redis.PS.inter;

/**
 * @Author : bingo
 * @category : TODO
 * @Date : 2018/9/20 16:39
 **/
public interface TaskInface {

    void onJoin(String channel, int subscribedChannels);

    void onPost(String channel, String message);

    void onLost(String channel,int subscribedChannels);

}
