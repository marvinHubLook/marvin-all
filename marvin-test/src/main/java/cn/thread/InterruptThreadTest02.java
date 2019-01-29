package cn.thread;

/**
 * @Author : bingo
 * @category : 中断阻塞线程
 * @Date : 2018/5/11 15:16
 **/
public class InterruptThreadTest02 extends Thread {


    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            System.out.println(Thread.currentThread().getName() + "is running");
            try {
                System.out.println(Thread.currentThread().getName() + " Thread.sleep begin");
                Thread.sleep(1000);
                System.out.println(Thread.currentThread().getName() + " Thread.sleep end");
            } catch (InterruptedException e) {
                //由于调用sleep()方法清除状态标志位 所以这里需要再次重置中断标志位 否则线程会继续运行下去
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
        }
        if (Thread.currentThread().isInterrupted()) {
            System.out.printf(Thread.currentThread().getName() + "is interrupted");
        }
    }

    public static void main(String[] args) {
        InterruptThreadTest02 thread2=new InterruptThreadTest02();
        thread2.start();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread2.interrupt();
    }
}
