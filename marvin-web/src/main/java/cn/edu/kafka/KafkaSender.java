package cn.edu.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;

/**
 * @Author : bingo
 * @category : 生产者
 * @Date : 2018/5/17 15:07
 **/
/*@Component*/
public class KafkaSender {

    @Autowired
    private KafkaTemplate kafkaTemplate;

    /**
     * 发送消息到kafka
     */
    public void sendChannelMess(String key, String message){
        kafkaTemplate.send("ftop",key,message);
    }
}
