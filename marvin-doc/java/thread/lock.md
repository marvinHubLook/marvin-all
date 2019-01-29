### 1、锁分类
 * **独占锁** 锁在一个时间点只能被一个线程锁占有。 例如: ReentrantLock 、 ReentrantReadWriteLock.WriteLock
    > * **公平锁** ：是按照通过CLH等待线程按照先来先得的规则，公平的获取锁  ReentrantLock、ReentrantReadWriteLock.WriteLock  
    > * **非公平锁** : 则当线程要获取锁时，它会无视CLH等待队列而直接获取锁
 
 * **共享锁** 能被多个线程同时拥有，能被共享的锁。 例如: ReentrantReadWriteLock.ReadLock，CyclicBarrier， CountDownLatch和Semaphore都是共享锁
 
 
  

 
##### AbstractQueuedSynchronizer > Node
   * **SHARED** : 表示Node处于共享模式
   * **EXCLUSIVE** : 表示Node处于独占模式  
######   状态( waitStatus )
   * **CANCELLED =  1** :
   > 因为超时或者中断，Node被设置为取消状态，被取消的Node不应该去竞争锁，只能保持取消状态不变，不能转换为其他状态，处于这种状态的Node会被踢出队列，被GC回收
   * **SIGNAL    = -1** :
   > 当前线程的后继线程处于阻塞状态，而当前线程被release或cancel掉，因此需要唤醒当前线程的后继线程。
   * **CONDITION = -2** :
   > 线程(处在Condition休眠状态)在等待Condition唤醒，对应的waitStatus的值
   * **PROPAGATE = -3** :
   > (共享锁)其它线程获取到“共享锁”，表示当前场景下后续的acquireShared能够得以执行；
   * **0**
   > 示当前节点在sync队列中，等待着获取锁。
   
   
   
 
 
 
 
 
 
 
 
 
 
 
 
 [Java多线程系列目录(共43篇)](http://www.cnblogs.com/skywang12345/p/java_threads_category.html)