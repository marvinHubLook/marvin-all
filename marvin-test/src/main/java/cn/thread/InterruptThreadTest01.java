package cn.thread;


/**
 * @Author : bingo
 * @category : 中断非阻塞线程
 * @Date : 2018/5/11 15:11
 **/
public class InterruptThreadTest01 extends Thread{

    @Override
    public void run() {
        // 这里调用的是非清除中断标志位的isInterrupted方法
        while(!Thread.currentThread().isInterrupted()) {
            long beginTime = System.currentTimeMillis();
            System.out.println(Thread.currentThread().getName() + "is running");
            // 当前线程每隔一秒钟检测线程中断标志位是否被置位
            while (System.currentTimeMillis() - beginTime < 1000) {}
        }
        if (Thread.currentThread().isInterrupted()) {
            System.out.println(Thread.currentThread().getName() + "is interrupted");
        }
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        InterruptThreadTest01 itt = new InterruptThreadTest01();
        itt.start();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // 设置线程的中断标志位
        itt.interrupt();
    }
}
