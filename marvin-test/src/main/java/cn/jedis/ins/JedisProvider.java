package cn.jedis.ins;

import java.io.IOException;

/**
 * @program: marvin-all
 * @description: TODO
 * @author: Mr.Wang
 * @create: 2018-11-29 14:49
 **/
public interface JedisProvider<S,B> {
     S get();

     B getBinary();

     void release();

     String mode();

     String groupName();

     void destroy()  throws IOException;
}
