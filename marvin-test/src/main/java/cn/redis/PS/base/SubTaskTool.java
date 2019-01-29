package cn.redis.PS.base;

import cn.redis.PS.inter.SubAbstractTask;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author : bingo
 * @category : TODO
 * @Date : 2018/9/20 17:27
 **/
public class SubTaskTool {
    private static List<String> lists=new ArrayList<>();
    private SubTaskTool(){};
    public static boolean addTask(SubAbstractTask task,ChannelEnum channel){
        String className=task.getClass().getName();
        if(!lists.contains(className)){
            new Thread(new SubThread(task, channel)).start();
            lists.add(className);
            return true;
        }
        return false;
    }
    private static class SubThread implements Runnable{
        private SubAbstractTask task;
        private ChannelEnum channel;
        public SubThread(SubAbstractTask task,ChannelEnum channel){
            this.task=task;
            this.channel=channel;
        }
        Jedis jedis = null;
        @Override
        public void run() {
            try {
                jedis = JredisPoolTool.get();   //取出一个连接
                jedis.subscribe(new Subscriber(task), channel.getChannel());    //通过subscribe 的api去订阅，入参是订阅者和频道名
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (jedis != null) {
                    jedis.close();
                }
            }
        }
    }

}
