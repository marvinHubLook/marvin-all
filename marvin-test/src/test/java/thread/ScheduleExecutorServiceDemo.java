package thread;/**
 * @Author : bingo
 * @category : TODO
 * @Date : 2018/11/15 18:46
 **/

import java.time.LocalTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * @program: marvin-all
 * @description: TODO
 * @author: Mr.Wang
 * @create: 2018-11-15 18:46
 **/
public class ScheduleExecutorServiceDemo {
    private final static ScheduledExecutorService scheduler =
            Executors.newScheduledThreadPool(5);

    public static void main(String args[]){
        final Runnable beeper = new Runnable() {
            public void run() {
                System.out.println(Thread.currentThread().getName()+" >>> "+LocalTime.now().toString()+" >>> beep");
                //TODO 沉睡吧，少年
                try {
                    TimeUnit.SECONDS.sleep(3L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    scheduler.shutdown();
                }
            }
        };

        //从0s开始输出beep，间隔1s
        //scheduleAtFixedRate    //以固定频率执行
        //scheduleWithFixedDelay //延迟方式执行，间隔时间=间隔时间入参+任务执行时间
        final ScheduledFuture<?> beeperHandle =
                scheduler.scheduleAtFixedRate(beeper, 0, 1, TimeUnit.SECONDS);

        //10s之后停止beeperHandle的疯狂输出行为
        scheduler.schedule(new Runnable() {
            public void run() {
                System.out.println("觉悟吧，beeperHandle！I will kill you!");
                beeperHandle.cancel(true);
            }
        }, 10, TimeUnit.SECONDS);
    }
}
