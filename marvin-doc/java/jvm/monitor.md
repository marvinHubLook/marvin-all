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

#### jmap
```
jmap -dump:format=b,file=outfile $pid  
# 进程的内存heap输出出来到outfile文件里,配合MAT（内存分析工具(Memory Analysis Tool）或与jhat (Java Heap Analysis Tool)一起使用，能够以图像的形式直观的展示当前内存是否有问题  

jmap -histo[:live] $pid
打印每个class的实例数目,内存占用,类全名信息. VM的内部类名字开头会加上前缀”*”. 如果live子参数加上后,只统计活的对象数量. 

jmap -clstats $pid
# 打印classload和jvm heap长久层的信息. 包含每个classloader的名字,活泼性,地址,父classloader和加载的class数量. 另外,内部String的数量和占用内存数也会打印出来. 

jmap -heap $pid
# 打印heap的概要信息，GC使用的算法，heap的配置及wise heap的使用情况.
```

```
[root@localhost ~]# jmap -heap 27900
Attaching to process ID 27900, please wait...
Debugger attached successfully.
Client compiler detected.
JVM version is 20.45-b01
using thread-local object allocation.
Mark Sweep Compact GC
Heap Configuration: #堆内存初始化配置
   MinHeapFreeRatio = 40     #-XX:MinHeapFreeRatio设置JVM堆最小空闲比率  
   MaxHeapFreeRatio = 70   #-XX:MaxHeapFreeRatio设置JVM堆最大空闲比率  
   MaxHeapSize = 100663296 (96.0MB)   #-XX:MaxHeapSize=设置JVM堆的最大大小
   NewSize = 1048576 (1.0MB)     #-XX:NewSize=设置JVM堆的‘新生代’的默认大小
   MaxNewSize = 4294901760 (4095.9375MB) #-XX:MaxNewSize=设置JVM堆的‘新生代’的最大大小
   OldSize = 4194304 (4.0MB)  #-XX:OldSize=设置JVM堆的‘老生代’的大小
   NewRatio = 2    #-XX:NewRatio=:‘新生代’和‘老生代’的大小比率
   SurvivorRatio = 8  #-XX:SurvivorRatio=设置年轻代中Eden区与Survivor区的大小比值
   PermSize = 12582912 (12.0MB) #-XX:PermSize=<value>:设置JVM堆的‘持久代’的初始大小  
   MaxPermSize = 67108864 (64.0MB) #-XX:MaxPermSize=<value>:设置JVM堆的‘持久代’的最大大小  
Heap Usage:
New Generation (Eden + 1 Survivor Space): #新生代区内存分布，包含伊甸园区+1个Survivor区
   capacity = 30212096 (28.8125MB)
   used = 27103784 (25.848182678222656MB)
   free = 3108312 (2.9643173217773438MB)
   89.71169693092462% used
Eden Space: #Eden区内存分布
   capacity = 26869760 (25.625MB)
   used = 26869760 (25.625MB)
   free = 0 (0.0MB)
   100.0% used
From Space: #其中一个Survivor区的内存分布
   capacity = 3342336 (3.1875MB)
   used = 234024 (0.22318267822265625MB)
   free = 3108312 (2.9643173217773438MB)
   7.001809512867647% used
To Space: #另一个Survivor区的内存分布
   capacity = 3342336 (3.1875MB)
   used = 0 (0.0MB)
   free = 3342336 (3.1875MB)
   0.0% used
tenured generation:   #当前的Old区内存分布  
   capacity = 67108864 (64.0MB)
   used = 67108816 (63.99995422363281MB)
   free = 48 (4.57763671875E-5MB)
   99.99992847442627% used
Perm Generation:     #当前的 “持久代” 内存分布
   capacity = 14417920 (13.75MB)
   used = 14339216 (13.674942016601562MB)
   free = 78704 (0.0750579833984375MB)
   99.45412375710227% used
```