package cn.jmx.mxbeans;

/**
 * @program: marvin-all
 * @description: TODO
 * @author: Mr.Wang
 * @create: 2019-03-08 16:23
 **/
public interface  QueueSamplerMXBean {
    public QueueSample getQueueSample();
    public void clearQueue();
}
