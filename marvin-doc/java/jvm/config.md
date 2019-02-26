常见配置汇总  
1、堆设置  
   * -Xms:初始堆大小
   * -Xmx:最大堆大小
   * -XX:NewSize=n:设置年轻代大小
   * -XX:NewRatio=n:设置年轻代和年老代的比值。如:为3，表示年轻代与年老代比值为1：3，年轻代占整个年轻代年老代和的1/4
   * -XX:SurvivorRatio=n:年轻代中Eden区与两个Survivor区的比值。注意Survivor区有两个。如：3，表示Eden：Survivor=3：2，一个Survivor区占整个年轻代的1/5
   * -XX:MaxPermSize=n:设置持久代大小  #1.8被移除
   * -XX:MetaspaceSize：分配给类元数据空间（以字节计）的初始大小（Oracle逻辑存储上的初始高水位，the initial high-water-mark）。此值为估计值，MetaspaceSize的值设置的过大会延长垃圾回收时间。垃圾回收过后，引起下一次垃圾回收的类元数据空间的大小可能会变大。
   * -XX:MaxMetaspaceSize：分配给类元数据空间的最大值，超过此值就会触发Full GC，此值默认没有限制，但应取决于系统内存的大小。JVM会动态地改变此值。
   * -XX:MinMetaspaceFreeRatio，在GC之后，最小的Metaspace剩余空间容量的百分比，减少为分配空间所导致的垃圾收集
   *　-XX:MaxMetaspaceFreeRatio，在GC之后，最大的Metaspace剩余空间容量的百分比，减少为释放空间所导致的垃圾收集
   >
      java1.8之后移除 PermGen(永久带)，取而代之的是元空间
        JDK1.7中，存储在永久代的部分数据就已经转移到了Java Heap或者是 Native Heap。符号引用(Symbols)转移到了native heap；字面量(interned strings)转移到了java heap；类的静态变量(class statics)转移到了java heap
      元空间与永久代之间最大的区别在于：元空间并不在虚拟机中，而是使用本地内存  


2、收集器设置
   * -XX:+UseSerialGC:设置串行收集器
   * -XX:+UseParallelGC:设置并行收集器
   * -XX:+UseParalledlOldGC:设置并行年老代收集器
   * -XX:+UseConcMarkSweepGC:设置并发收集器  
   
3、垃圾回收统计信息
   * -XX:+PrintGC
   * -XX:+PrintGCDetails
   * -XX:+PrintGCTimeStamps
   * -Xloggc:filename

4、并行收集器设置
   * -XX:ParallelGCThreads=n:设置并行收集器收集时使用的CPU数。并行收集线程数。
   * -XX:MaxGCPauseMillis=n:设置并行收集最大暂停时间
   * -XX:GCTimeRatio=n:设置垃圾回收时间占程序运行时间的百分比。公式为1/(1+n)

5、并发收集器设置
   * -XX:+CMSIncrementalMode:设置为增量模式。适用于单CPU情况。
   * -XX:ParallelGCThreads=n:设置并发收集器年轻代收集方式为并行收集时，使用的CPU数。并行收集线程数。