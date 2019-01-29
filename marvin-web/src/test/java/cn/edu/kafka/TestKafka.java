package cn.edu.kafka;

import cn.edu.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @Author : bingo
 * @category : 测试kafka
 * @Date : 2018/5/17 15:21
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class TestKafka {
    @Autowired
    private KafkaSender sender;


    @Test
    public void testSender(){
        for(int i=0;i<10;i++){
            sender.sendChannelMess("key1","message"+i);
        }
    }
}
