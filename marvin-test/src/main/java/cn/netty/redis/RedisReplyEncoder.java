package cn.netty.redis;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @Author : bingo
 * @category : TODO
 * @Date : 2018/8/1 10:02
 **/
public class RedisReplyEncoder extends MessageToByteEncoder<RedisReply> {

    @Override
    protected void encode(ChannelHandlerContext ctx, RedisReply msg, ByteBuf out) throws Exception {
        System.out.println("RedisReplyEncoder: " + msg);
        msg.write(out);
    }

}