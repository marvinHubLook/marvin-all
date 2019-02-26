package cn.edu.quartz;

import org.quartz.DisallowConcurrentExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

/**
 * @program: marvin-all
 * @description: TODO
 * @author: Mr.Wang
 * @create: 2019-02-12 15:40
 **/
@Component
@EnableScheduling
@DisallowConcurrentExecution // 保证上一次任务执行完毕再执行下一任务
public class MyFirstExerciseJob {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public void myJobBusinessMethod() {
        this.logger.info("哇被触发了哈哈哈哈哈");
    }

}