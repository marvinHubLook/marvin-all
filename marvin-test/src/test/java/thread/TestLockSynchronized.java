package thread;

import org.junit.Test;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @program: marvin-all
 * @description: TODO
 * @author: Mr.Wang
 * @create: 2018-11-20 09:05
 **/
public class TestLockSynchronized {
    @Test
    public void testSychronized() throws InterruptedException {
        Object lock = new Object();
        // TODO Auto-generated method stub
        int N = 10;
        Thread[] threads = new Thread[N];
        for(int i = 0; i < N; ++i){
            threads[i] = new Thread(new Runnable(){
                public void run() {
                    synchronized(lock){
                        System.out.println(Thread.currentThread().getName() + " get synch lock!");
                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }

            });
        }
        synchronized(lock){
            for(int i = 0; i < N; ++i){
                threads[i].start();
                Thread.sleep(200);
            }
        }
        for(int i = 0; i < N; ++i) threads[i].join();
    }

    @Test
    public void testLock() throws InterruptedException {
        Lock lock = new ReentrantLock();
        // TODO Auto-generated method stub
        int N = 10;
        Thread[] threads = new Thread[N];
        for(int i = 0; i < N; ++i){
            threads[i] = new Thread(new Runnable(){
                public void run() {
                    lock.lock();
                    System.out.println(Thread.currentThread().getName() + " get JSR166 lock!");
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    lock.unlock();
                }
            });
        }
        lock.lock();
        for(int i = 0; i < N; ++i){
            threads[i].start();
            Thread.sleep(200);
        }
        lock.unlock();

        for(int i = 0; i < N; ++i)
            threads[i].join();
    }

    @Test
    public void testSychronized1() throws InterruptedException {
        Object lock = new Object();
        int N = 10;
        Thread[] threads = new Thread[N];
        Thread[] threadsForWaits = new Thread[N];
        for(int i = 0; i < N; ++i){
            threads[i] = new Thread(new Runnable(){
                @Override
                public void run() {
                    synchronized(lock){
                        System.out.println(Thread.currentThread().getName() + " nowait get lock");
                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }

            });
        }
        for(int i = 0; i < N; ++i){
            threadsForWaits[i] = new Thread(new Runnable(){
                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    synchronized(lock){
                        System.out.println(Thread.currentThread().getName() + " wait first get lock");
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                        System.out.println(Thread.currentThread().getName() + " wait second get lock");
                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }

            });
        }

        for(int i = 0; i < N; ++i){
            threadsForWaits[i].start();
            Thread.sleep(200);
        }
        synchronized(lock){
            for(int i = 0; i < N; ++i){
                threads[i].start();
                Thread.sleep(200);
            }
            for(int i = 0; i < 4 ; ++i){
                lock.notify();
            }
            Thread.sleep(200);
        }


        for(int i = 0; i < N; ++i)
            threads[i].join();

        for(int i = 0; i < N; ++i)
            threadsForWaits[i].join();

    }

    @Test
    public void testLock1() throws InterruptedException {
        Lock lock = new ReentrantLock();
        Condition condition = lock.newCondition();

        int N = 10;
        Thread[] threads = new Thread[N];
        Thread[] threadsForWaits = new Thread[N];
        for(int i = 0; i < N; ++i){
            threads[i] = new Thread(new Runnable(){
                @Override
                public void run() {
                    lock.lock();
                    System.out.println(Thread.currentThread().getName() + " nowait get lock");
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    lock.unlock();
                }

            });
        }
        for(int i = 0; i < N; ++i){
            threadsForWaits[i] = new Thread(new Runnable(){
                @Override
                public void run() {
                    lock.lock();		//synchronized(lock){
                    System.out.println(Thread.currentThread().getName() + " wait first get lock");
                    try {
                        condition.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + " wait second get lock");
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    lock.unlock();
                }
            });
        }

        for(int i = 0; i < N; ++i){
            threadsForWaits[i].start();
            Thread.sleep(200);
        }
        lock.lock();	//synchronized(lock){
        for(int i = 0; i < N; ++i){
            threads[i].start();
            Thread.sleep(200);
        }
        for(int i = 0; i < 4 ; ++i){
            condition.signal();
        }
        Thread.sleep(200);
        lock.unlock();

        for(int i = 0; i < N; ++i)  threads[i].join();

        for(int i = 0; i < N; ++i) threadsForWaits[i].join();
    }





}
