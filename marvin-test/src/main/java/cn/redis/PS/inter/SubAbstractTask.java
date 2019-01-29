package cn.redis.PS.inter;

/**
 * @Author : bingo
 * @category : TODO
 * @Date : 2018/9/20 17:00
 **/
public abstract class SubAbstractTask implements TaskInface{
    @Override
    public void onJoin(String channel, int subscribedChannels) {}

    @Override
    public void onLost(String channel, int subscribedChannels) {}
}
