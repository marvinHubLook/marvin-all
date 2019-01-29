package cn.netty.redis;

/**
 * @Author : bingo
 * @category : TODO
 * @Date : 2018/8/1 10:03
 **/

import io.netty.buffer.ByteBuf;

import java.io.IOException;


public interface RedisReply<T> {

    byte[] CRLF = new byte[] { '\r', '\n' };

    T data();

    void write(ByteBuf out) throws IOException;

}
