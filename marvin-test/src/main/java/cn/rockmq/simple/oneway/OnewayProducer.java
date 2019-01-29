package cn.rockmq.simple.oneway;

import cn.rockmq.Constant;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.io.UnsupportedEncodingException;

/**
 * @program: marvin-all
 * @description: 单向模式发送消息   单向传输用于需要中等可靠性的情况，例如日志收集。
 * @author: Mr.Wang
 * @create: 2019-01-06 10:10
 **/
public class OnewayProducer {

    public static void main(String[] args) throws UnsupportedEncodingException, RemotingException, MQClientException, InterruptedException {
        DefaultMQProducer producer = new DefaultMQProducer(Constant.GROUP_SIMPLE_NAME);

        producer.setNamesrvAddr(Constant.CONNECT_URI);
        for( int i=0;i<100;i++){
            Message message = new Message(Constant.TOPIC_SIMPLE, Constant.TAG_SIMPLE_A, ("Hello RockMQ" + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
            producer.sendOneway(message);
        }

        producer.shutdown();
       
    }
}
