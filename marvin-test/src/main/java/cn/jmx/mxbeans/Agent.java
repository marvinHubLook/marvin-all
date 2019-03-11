package cn.jmx.mxbeans;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * @program: marvin-all
 * @description: TODO
 * @author: Mr.Wang
 * @create: 2019-03-08 16:25
 *
 * http://www.tianshouzhi.com/api/tutorials/jmx/35
 **/
public class Agent {

    public static void main(String[] args) throws Exception {
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();

        ObjectName mxbeanName = new ObjectName("cn.mxbeans:type=QueueSampler");

        Queue<String> queue = new ArrayBlockingQueue<String>(10);
        queue.add("Request-1");
        queue.add("Request-2");
        queue.add("Request-3");
        QueueSampler mxbean = new QueueSampler(queue);
        mbs.registerMBean(mxbean, mxbeanName);

        System.out.println("Waiting...");
        Thread.sleep(Long.MAX_VALUE);
    }
}
