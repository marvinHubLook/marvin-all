package cn.jmx.mxbeans;

import java.util.Date;
import java.util.Queue;

/**
 * @program: marvin-all
 * @description: TODO
 * @author: Mr.Wang
 * @create: 2019-03-08 16:24
 **/
public class QueueSampler implements QueueSamplerMXBean{
    private Queue<String> queue;

    public QueueSampler (Queue<String> queue) {
        this.queue = queue;
    }
    @Override
    public QueueSample getQueueSample() {
        synchronized (queue) {
            return new QueueSample(new Date(),queue.size(), queue.peek());
        }
    }
    @Override
    public void clearQueue() {
        synchronized (queue) {
            queue.clear();
        }
    }
}
