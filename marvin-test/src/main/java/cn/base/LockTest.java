package cn.base;

import java.util.HashMap;
import java.util.concurrent.locks.ReentrantLock;

/***
 *  简单实现lock
 *      1、可重入
 */
public class LockTest {
    static class MyLock{
        boolean isLocked = false;
        Thread  lockedBy = null;
        int lockedCount = 0;

        public synchronized void lock()
                throws InterruptedException{
            Thread callingThread =
                    Thread.currentThread();
            while(isLocked && lockedBy != callingThread){
                wait();
            }
            isLocked = true;
            lockedCount++;
            lockedBy = callingThread;
        }

        public synchronized void unlock(){
            if(Thread.currentThread() == this.lockedBy){
                lockedCount--;
                if(lockedCount == 0){
                    isLocked = false;
                    notify();
                }
            }
        }
    }
}
