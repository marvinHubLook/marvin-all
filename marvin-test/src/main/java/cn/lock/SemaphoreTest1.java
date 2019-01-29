package cn.lock;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @Author : bingo
 * @category : TODO
 * @Date : 2018/5/23 15:20
 **/
public class SemaphoreTest1 {
    private static final int SEX_MAX=10;

    public static void main(String[] args) {
        Semaphore sem=new Semaphore(SEX_MAX);
        ExecutorService threadPool=Executors.newFixedThreadPool(3);
        threadPool.execute(new MyThread(sem,4));
        threadPool.execute(new MyThread(sem,5));
        threadPool.execute(new MyThread(sem,7));
        threadPool.shutdown();
    }

    static class MyThread extends Thread{
        private volatile Semaphore sem; //信号量
        private int count; //申请信号量大小

        public MyThread(Semaphore sem, int count) {
            this.sem = sem;
            this.count = count;
        }

        @Override
        public void run() {
            try {
                sem.acquire(count);
                Thread.sleep(2000);
                System.out.println(Thread.currentThread().getName()+" acquire count="+count);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                sem.release(count);
                System.out.println(Thread.currentThread().getName()+" release"+count);
            }

        }
    }

}
