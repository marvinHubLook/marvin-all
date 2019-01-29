package cn.edu.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

/**
 * @Author : bingo
 * @category : TODO
 * @Date : 2018/7/5 18:28
 **/
public class KafkaProductor {
    public static void main(String[] args) throws InterruptedException {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "180.76.249.249:9092");
        properties.put("metadata.broker.list", "180.76.249.249:9092");
        /**
         * "key.serializer" 的类型根据ProducerRecord<Integer,String>中的类型来确定，
         * Integer对应的为IntegerSerializer,String对应的为StringSerializer
         * key.serializer和value.serializer根据定义的ProducerRecord类型来对应
         */
        properties.put("key.serializer", "org.apache.kafka.common.serialization.IntegerSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        KafkaProducer kafkaProducer = new KafkaProducer(properties);
        /**
         * KeyedMessage中"test-topic"为topic的名字，"test-message"为消息内容
         * 6为对应的key值
         * "hello"为对应的value值
         */
        ProducerRecord<Integer,String> producerRecord = new ProducerRecord<>("ftop", 6,"hello");

        kafkaProducer.send(producerRecord);


        Thread.sleep(1000);

        kafkaProducer.close();

        System.out.println("product end");
    }

}
