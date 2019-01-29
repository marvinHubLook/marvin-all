package cn.rockmq.simple;

import cn.rockmq.Constant;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * @program: marvin-all
 * @description: 消费者
 * @author: Mr.Wang
 * @create: 2019-01-06 09:49
 **/
public class Consumer {

    public static void main(String[] args) throws MQClientException {
        // Instantiate with specified consumer group name.
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(Constant.GROUP_SIMPLE_NAME);
        consumer.setNamesrvAddr(Constant.CONNECT_URI);
        // Subscribe one more more topics to consume.
        consumer.subscribe(Constant.TOPIC_SIMPLE,"*");
        // Register callback to execute on arrival of messages fetched from brokers.
        consumer.registerMessageListener((List<MessageExt> msgs, ConsumeConcurrentlyContext context)->{
            System.out.printf("%s Receive New Messages: %s %n", Thread.currentThread().getName(), msgs);
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });
        //Launch the consumer instance.
        consumer.start();
        System.out.printf("Consumer Started.%n");

    }
}
