#### 打印GC日志 
> -XX:+printGC 简要信息
> -XX:+PrintGCDetails 详细信息
> -XX:+PrintHeapAtGC 每一次GC前和GC后，都打印堆信息
```
-verbose:gc -Xloggc:logs/gc.log XX:+PrintGCTimeStamps -XX:+PrintGCDetails
```

* -XX:+TraceClassLoading 监控类的加载

* -Xmx –Xms：指定最大堆和最小堆
* -Xmn、-XX:NewRatio、-XX:SurvivorRatio:
    > -Xmn : 设置新生代大小  
     -XX:NewRatio : 　新生代（eden+2*s）和老年代（不包含永久区）的比值  
          >> 例如: 4，表示新生代:老年代=1:4，即新生代占整个堆的1/5  
     -XX:SurvivorRatio（幸存代） : 设置两个Survivor区和eden的比值  
          >> 例如:8，表示两个Survivor:eden=2:8，即一个Survivor占年轻代的1/10
          
          