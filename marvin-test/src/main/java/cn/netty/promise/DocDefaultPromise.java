package cn.netty.promise;

/**
 * @program: marvin-all
 * @description: netty 异步任务采用Promise机制
 *         在Future基础上添加了了:
 *                 1、 调用者提供get()和addListener()用于获取Future任务执行结果和添加监听事件；
 *                 2、 为业务处理任务提供setSuccess()等方法设置任务的成功或失败
 * @author: Mr.Wang
 * @create: 2019-01-11 11:59
 **/
public class DocDefaultPromise {
    /**
     * await(); // 等待Future任务结束
     * await(timeout, unit)  // 等待Future任务结束，超过事件则抛出异常
     * cause();  // 返回Future任务的异常
     * getNow() //  /返回Future任务的执行结果
     * **/



}
