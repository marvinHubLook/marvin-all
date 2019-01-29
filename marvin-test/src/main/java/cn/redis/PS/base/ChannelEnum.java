package cn.redis.PS.base;

/**
 * @Author : bingo
 * @category : TODO
 * @Date : 2018/9/20 17:41
 **/
public enum ChannelEnum {
    DEFAULT("default"),PROXY("proxy");
    String channel;
    ChannelEnum(String channel){
        this.channel=channel;
    }
    public String getChannel() {
        return channel;
    }
    public void setChannel(String channel) {
        this.channel = channel;
    }
}
