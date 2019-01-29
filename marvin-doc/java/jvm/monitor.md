#### jstat -gcutil $pid   
* **S0**: Survivor 0区的空间使用率 Survivor space 0 utilization as a percentage of the space's current capacity.
* **S1**: Survivor 1区的空间使用率 Survivor space 1 utilization as a percentage of the space's current capacity.
* **E**: Eden区的空间使用率 Eden space utilization as a percentage of the space's current capacity.
* **O**: 老年代的空间使用率 Old space utilization as a percentage of the space's current capacity.
* **M**: 元数据的空间使用率 Metaspace utilization as a percentage of the space's current capacity.
* **CCS**: 类指针压缩空间使用率 Compressed class space utilization as a percentage.
* **YGC**: 新生代GC次数 Number of young generation GC events.
* **YGCT**: 新生代GC总时长 Young generation garbage collection time.
* **FGC**: Full GC次数 Number of full GC events.
* **FGCT**: Full GC总时长 Full garbage collection time.
* **GCT**: 总共的GC时长 Total garbage collection time.
```
[tomcat@n01 ~]$ /opt/java/jdk1.8.0_101/bin/jstat -gcutil 11368   
  S0     S1     E      O      M     CCS       YGC   YGCT     FGC    FGCT     GCT   
  2.02   0.00  49.32   3.16  92.13  88.17     24    1.220     3    0.637    1.857

```


#### top -p $pid -H  查看当前进程下线程占用情况
    PID 系统线程id (对应jstack 中 nid )
    
### jstack $pid (进程id)    查看当前进程里面的线程情况
* [线程分类](https://www.javatang.com/archives/2017/10/19/51301886.html)
* [线程详细信息分析](https://www.javatang.com/archives/2017/10/25/36441958.html)  
例如 : 
```log
"resin-22129" daemon prio=10 tid=0x00007fbe5c34e000 nid=0x4cb1 waiting on condition [0x00007fbe4ff7c000]
   java.lang.Thread.State: WAITING (parking)
    at sun.misc.Unsafe.park(Native Method)
    at java.util.concurrent.locks.LockSupport.park(LockSupport.java:315)
    at com.caucho.env.thread2.ResinThread2.park(ResinThread2.java:196)
    at com.caucho.env.thread2.ResinThread2.runTasks(ResinThread2.java:147)
    at com.caucho.env.thread2.ResinThread2.run(ResinThread2.java:118)
```
* "resin-22129" 线程名称：如果使用 java.lang.Thread 类生成一个线程的时候，线程名称为 Thread-(数字) 的形式，这里是resin生成的线程；
* daemon 线程类型：线程分为守护线程 (daemon) 和非守护线程 (non-daemon) 两种，通常都是守护线程；
* prio=10 线程优先级：默认为5，数字越大优先级越高；
* tid=0x00007fbe5c34e000 JVM线程的id：JVM内部线程的唯一标识，通过 java.lang.Thread.getId()获取，通常用自增的方式实现；
* nid=0x4cb1 系统线程id：对应的系统线程id（Native Thread ID)，可以通过 top 命令进行查看，现场id是十六进制的形式；
* waiting on condition 系统线程状态：这里是系统的线程状态，具体的含义见下面 系统线程状态 部分；
* [0x00007fbe4ff7c000] 起始栈地址：线程堆栈调用的其实内存地址；
* java.lang.Thread.State: WAITING (parking) JVM线程状态：这里标明了线程在代码级别的状态，详细的内容见下面的 JVM线程运行状态 部分。
线程调用栈信息：下面就是当前线程调用的详细栈信息，用于代码的分析。堆栈信息应该从下向上解读，因为程序调用的顺序是从下向上的。