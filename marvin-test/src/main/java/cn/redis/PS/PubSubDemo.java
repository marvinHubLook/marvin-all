package cn.redis.PS;

import cn.redis.PS.inter.SubAbstractTask;
import cn.redis.PS.task.KuaiDailiTask;

/**
 * @Author : bingo
 * @category : TODO
 * @Date : 2018/9/20 16:24
 **/
public class PubSubDemo {

    public static void main( String[] args )
    {
        KuaiDailiTask.startTask();
//        new Thread(()->{
//            while(true){
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                Publisher.addData(ChannelEnum.DEFAULT,"this is test");
//            }
//        }).start();
//        SubTaskTool.addTask(new MyTask(),ChannelEnum.DEFAULT);
    }

    public static class MyTask extends SubAbstractTask{
        @Override
        public void onPost(String channel, String message) {
            System.out.println("---接受数据---"+message);
        }
    }
}