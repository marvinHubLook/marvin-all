package cn.netty.redis;

/**
 * @Author : bingo
 * @category : TODO
 * @Date : 2018/8/1 10:03
 **/

import io.netty.buffer.ByteBuf;

import java.io.IOException;



public class IntegerReply implements RedisReply<Integer> {

    private static final char MARKER = ':';

    private final int data;

    public IntegerReply(int data) {
        this.data = data;
    }

    @Override
    public Integer data() {
        return this.data;
    }

    @Override
    public void write(ByteBuf out) throws IOException {
        out.writeByte(MARKER);
        out.writeBytes(String.valueOf(data).getBytes());
        out.writeBytes(CRLF);
    }

    @Override
    public String toString() {
        return "IntegerReply{" +
                "data=" + data +
                '}';
    }

}