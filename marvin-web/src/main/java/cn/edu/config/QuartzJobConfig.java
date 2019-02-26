package cn.edu.config;

import cn.edu.quartz.MyFirstExerciseJob;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * @program: marvin-all
 * @description: 注册任务和触发器
 * @author: Mr.Wang
 * @create: 2019-02-12 15:38
 *
 * https://www.cnblogs.com/nick-huang/p/8456272.html  定时任务 持久化、集群
 * https://www.cnblogs.com/laoyeye/p/9352002.html  动态定时任务
 **/
@Configuration
public class QuartzJobConfig {

    /**
     * 方法调用任务明细工厂Bean
     */
    @Bean(name = "myFirstExerciseJobBean")
    public MethodInvokingJobDetailFactoryBean myFirstExerciseJobBean(MyFirstExerciseJob myFirstExerciseJob) {
        MethodInvokingJobDetailFactoryBean jobDetail = new MethodInvokingJobDetailFactoryBean();
        jobDetail.setConcurrent(false); // 是否并发
        jobDetail.setName("general-myFirstExerciseJob"); // 任务的名字
        jobDetail.setGroup("general"); // 任务的分组
        jobDetail.setTargetObject(myFirstExerciseJob); // 被执行的对象
        jobDetail.setTargetMethod("myJobBusinessMethod"); // 被执行的方法
        return jobDetail;
    }


    /**
     * 表达式触发器工厂Bean
     */
    @Bean(name = "myFirstExerciseJobTrigger")
    public CronTriggerFactoryBean myFirstExerciseJobTrigger(@Qualifier("myFirstExerciseJobBean") MethodInvokingJobDetailFactoryBean myFirstExerciseJobBean) {
        CronTriggerFactoryBean tigger = new CronTriggerFactoryBean();
        tigger.setJobDetail(myFirstExerciseJobBean.getObject());
        tigger.setCronExpression("*/5 * * * * ?"); // 什么是否触发，Spring Scheduler Cron表达式
        tigger.setName("general-myFirstExerciseJobTrigger");
        return tigger;
    }


    /**
     * 调度器工厂Bean
     */
    @Bean(name = "schedulerFactory")
    public SchedulerFactoryBean schedulerFactory(@Qualifier("myFirstExerciseJobTrigger") Trigger myFirstExerciseJobTrigger) {
        SchedulerFactoryBean bean = new SchedulerFactoryBean();
        // 覆盖已存在的任务
        bean.setOverwriteExistingJobs(true);
        // 延时启动定时任务，避免系统未完全启动却开始执行定时任务的情况
        bean.setStartupDelay(15);
        // 注册触发器
        bean.setTriggers(myFirstExerciseJobTrigger);
        return bean;
    }
}
