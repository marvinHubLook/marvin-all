package cn.lock.reentrantLock;

import java.util.Collection;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public interface ReenTrantLockDoc {
    // 创建一个 ReentrantLock ，默认是“非公平锁”。
    ReentrantLock ReentrantLock();
    // 创建策略是fair的 ReentrantLock。fair为true表示是公平锁，fair为false表示是非公平锁。
    ReentrantLock ReentrantLock(boolean fair);

    // 查询当前线程保持此锁的次数。
    int getHoldCount();
    // 返回目前拥有此锁的线程，如果此锁不被任何线程拥有，则返回 null。
     Thread getOwner();
    // 返回一个 collection，它包含可能正等待获取此锁的线程。
     Collection<Thread> getQueuedThreads();
    // 返回正等待获取此锁的线程估计数。
    int getQueueLength();
    // 返回一个 collection，它包含可能正在等待与此锁相关给定条件的那些线程。
     Collection<Thread> getWaitingThreads(Condition condition);
    // 返回等待与此锁相关的给定条件的线程估计数。
    int getWaitQueueLength(Condition condition);
    // 查询给定线程是否正在等待获取此锁。
    boolean hasQueuedThread(Thread thread);
    // 查询是否有些线程正在等待获取此锁。
    boolean hasQueuedThreads();
    // 查询是否有些线程正在等待与此锁有关的给定条件。
    boolean hasWaiters(Condition condition);
    // 如果是“公平锁”返回true，否则返回false。
    boolean isFair();
    // 查询当前线程是否保持此锁。
    boolean isHeldByCurrentThread();
    // 查询此锁是否由任意线程保持。
    boolean isLocked();
    // 获取锁。
    void lock();
    // 如果当前线程未被中断，则获取锁。
    void lockInterruptibly();
    // 返回用来与此 Lock 实例一起使用的 Condition 实例。
    Condition newCondition();
    // 仅在调用时锁未被另一个线程保持的情况下，才获取该锁。
    boolean tryLock();
    // 如果锁在给定等待时间内没有被另一个线程保持，且当前线程未被中断，则获取该锁。
    boolean tryLock(long timeout, TimeUnit unit);
    // 试图释放此锁。
    void unlock();
}
