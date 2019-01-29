#### 线程池状态
* **RUNNING**：
    > 接受新任务并且处理阻塞队列里的任务
* **SHUTDOWN**：
    > 拒绝新任务但是处理阻塞队列里的任务
* **STOP**：
    > 拒绝新任务并且抛弃阻塞队列里的任务同时会中断正在处理的任务
* **TIDYING**：
    > 所有任务都执行完（包含阻塞队列里面任务）当前线程池活动线程为0，将要调用terminated方法
    当线程池变为TIDYING状态时，会执行钩子函数terminated()。terminated()在ThreadPoolExecutor类中是空的，若用户想在线程池变为TIDYING时，进行相应的处理；可以通过重载terminated()函数来实现。 
* **TERMINATED**：
    > 终止状态。terminated方法调用完成以后的状态
    
![线程池状态转换](../../../resources/images/2.jpg)

#### 队列