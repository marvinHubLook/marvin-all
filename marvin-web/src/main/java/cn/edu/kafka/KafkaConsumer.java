package cn.edu.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * @Author : bingo
 * @category : 消费者
 * @Date : 2018/5/17 15:19
 **/
/*@Component*/
public class KafkaConsumer {
    /**
     * 监听seckill主题,有消息就读取
     * @param message
     */
    @KafkaListener(topics = {"ftop"})
    public void receiveMessage(String message){
        //收到通道的消息之后执行秒杀操作
        System.out.printf(message);
    }
}
