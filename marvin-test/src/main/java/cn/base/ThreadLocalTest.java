package cn.base;

/**
 * java.lang.ThreadLocal 是Java提供用来保存线程变量的机制,是对象为键，任意类型为值的存储结构。
 *
 * ThreadLocal 存在的问题
 * 因为Entry 是继承弱引用类型，而弱引用类型会在GC的时候递归引用进行回收。而如果线程在使用完之后不可达。Entry 的弱引用key将被回收，而value因为是强引用类型所以会导致很多 key为 null的value，没办法访问这些key为null的ThrealLocal。
 * 虽然，ThreadLocal在每次进行get,set,remove方法的时候会进行nullkey的清除，但这还是无法避免内存泄漏的根本问题。所以在此提供良好使用ThreadLocal的两条建议。
 * 1)尽量将ThreadLocal 声明为static.延迟生命周期。
 * 2)每次在线程使用完之后，进行remove操作。
 */
public class ThreadLocalTest {
    private static ThreadLocal<String> threadLocal = new ThreadLocal<String>(){
        @Override
        protected String initialValue() {
            return "init";
        }
    };

    public void testThreadLocal() throws InterruptedException {
        String value = threadLocal.get();
        //第一次获取的是初始值
        System.out.println("value:"+value);// ==>"init"
        threadLocal.set("main value");
        value = threadLocal.get();
        System.out.println("value:"+value);// ==>"main value"
        ValueThread valueThread = new ValueThread("valueThread");
        valueThread.start();
        valueThread.join();
    }
    class ValueThread extends Thread{
        public ValueThread(String name){
            super(name);
        }
        @Override
        public void run() {
            System.out.println("我的线程名称是:"+this.getName());
            System.out.println("我的初始值是:"+threadLocal.get());//==>"init"
            threadLocal.set(this.getName());
            System.out.println("我现在的值是:"+threadLocal.get());//==>valueThread
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new ThreadLocalTest().testThreadLocal();
    }
}
