package netty;

import io.netty.util.HashedWheelTimer;
import io.netty.util.Timeout;
import io.netty.util.TimerTask;
import org.junit.Test;

import java.time.LocalTime;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @Author : bingo
 * @category : TODO
 * @Date : 2018/9/26 17:24
 **/
public class NettyTest {
    /**
     * <pre>tickDuration</pre> : 每 tick 一次的时间间隔, 每 tick 一次就会到达下一个槽位  <br/>
     * <pre>ticksPerWheel</pre>: 轮中的 slot 数，hash算法计算目标槽位
     * @throws InterruptedException
     */
    @Test
    public void testHashedWheelTimer() throws InterruptedException {
        HashedWheelTimer timer=new HashedWheelTimer(100,TimeUnit.MILLISECONDS,16);
        System.out.println(LocalTime.now()+" submitted");
        timer.newTimeout(new TimerTask() {
            @Override
            public void run(Timeout timeout) throws Exception {
                new Thread(){
                    @Override
                    public void run() {
                        System.out.println(new Date() + " executed");
                        try {
                            TimeUnit.MILLISECONDS.sleep(1200);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println(new Date() + " FINISH");
                    }
                }.start();
            }
        }, 1, TimeUnit.SECONDS);

        timer.newTimeout(timeout -> new Thread(()->{
            System.out.println(new Date() + " TASK2 executed");
            try {
                TimeUnit.MILLISECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(new Date() + " TASK2 FINISH");
        }).start(),2,TimeUnit.SECONDS);

        TimeUnit.SECONDS.sleep(10);
    }

    @Test
    public void testHashTime() throws InterruptedException {
        int tickDuration=10,ticksPerWheel=16;
        HashedWheelTimer timer=new HashedWheelTimer(10,TimeUnit.MILLISECONDS,16);
        int taskLen=100;
        Random random = new Random();
        TimerTask[] tasks=new TimerTask[taskLen];
        String[] lists=new String[100];
        long startTime=System.currentTimeMillis();
        for(int i=0;i<taskLen;i++){
            int delay=random.nextInt(10000);
            tasks[i]=new WorkTimer(i,delay,System.currentTimeMillis()-startTime,lists,startTime);
            timer.newTimeout(tasks[i],delay,TimeUnit.MILLISECONDS);
        }
        Thread.sleep(10000);
        System.out.println(String.join("\r\n",lists));
    }

    class WorkTimer implements TimerTask {
        private int mark;
        private int delay;
        private String[] lists;
        private long curTime;
        private long startTime;

        public WorkTimer(int mark,int delay,long curTime,String[] lists,long startTime) {
            this.mark = mark;
            this.delay=delay;
            this.lists=lists;
            this.curTime=curTime;
            this.startTime=startTime;
        }
        @Override
        public void run(Timeout timeout) throws Exception {
            lists[mark]=print();
        }
        private String print(){
            return new StringBuilder()
                    .append("startTime : "+curTime)
                    .append("  delay :"+delay)
                    .append("  curtime : "+ (System.currentTimeMillis()-startTime))
                    .toString();
        }
    }
}
