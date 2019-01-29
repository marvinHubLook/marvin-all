package cn.lock.reentrantLock;

import java.util.Collection;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @Author : bingo
 * @category : TODO
 * @Date : 2018/5/22 17:22
 **/
public interface ReentrantReadWriteLockDoc {
    /**
     * @category :创建一个新的 ReentrantReadWriteLock，默认是采用“非公平策略”。
     */
    ReentrantReadWriteLock ReentrantReadWriteLock();

    /***
     * @category :创建一个新的 ReentrantReadWriteLock，fair是“公平策略”。fair为true，意味着公平策略；否则，意味着非公平策略。
     */
    ReentrantReadWriteLock ReentrantReadWriteLock(boolean fair);

    /**
     * @category : 返回当前拥有写入锁的线程，如果没有这样的线程，则返回 null。
     */
     Thread getOwner();

    /**   
     * @category : 返回一个 collection，它包含可能正在等待获取读取锁的线程。
     */
     Collection<Thread> getQueuedReaderThreads();
    /**
     * @category :返回一个 collection，它包含可能正在等待获取读取或写入锁的线程。
     * **/
     Collection<Thread> getQueuedThreads();
    /**
     * @category :返回一个 collection，它包含可能正在等待获取写入锁的线程。
     * **/
     Collection<Thread> getQueuedWriterThreads();
     /**
      * @category :返回等待获取读取或写入锁的线程估计数目。
      * **/
    int getQueueLength();
     /**
      * @category :查询当前线程在此锁上保持的重入读取锁数量。
      * **/
    int getReadHoldCount();
     /**
      * @category :查询为此锁保持的读取锁数量。
      * **/
    int getReadLockCount();
     /**
      * @category :返回一个 collection，它包含可能正在等待与写入锁相关的给定条件的那些线程。
      * **/
     Collection<Thread> getWaitingThreads(Condition condition);
     /**
      * @category :返回正等待与写入锁相关的给定条件的线程估计数目。
      * **/
    int getWaitQueueLength(Condition condition);
     /**
      * @category :查询当前线程在此锁上保持的重入写入锁数量。
      * **/
    int getWriteHoldCount();
     /**
      * @category :查询是否给定线程正在等待获取读取或写入锁。
      * **/
    boolean hasQueuedThread(Thread thread);
     /**
      * @category :查询是否所有的线程正在等待获取读取或写入锁。
      * **/
    boolean hasQueuedThreads();
     /**
      * @category :查询是否有些线程正在等待与写入锁有关的给定条件。
      * **/
    boolean hasWaiters(Condition condition);
     /**
      * @category :如果此锁将公平性设置为 ture，则返回 true。
      * **/
    boolean isFair();
     /**
      * @category :查询是否某个线程保持了写入锁。
      * **/
    boolean isWriteLocked();
     /**
      * @category :查询当前线程是否保持了写入锁。
      * **/
    boolean isWriteLockedByCurrentThread();
     /**
      * @category :返回用于读取操作的锁。
      * **/
    ReentrantReadWriteLock.ReadLock readLock();
     /**
      * @category :返回用于写入操作的锁。
      * **/
    ReentrantReadWriteLock.WriteLock writeLock();
    
}
