package cn.base;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/***
 * “快速失败”也就是fail-fast，它是Java集合的一种错误检测机制。当多个线程对集合进行结构上的改变的操作时，有可能会产生fail-fast机制。
 *  记住是有可能，而不是一定。
 *      例如：假设存在两个线程（线程1、线程2），线程1通过Iterator在遍历集合A中的元素，在某个时候线程2修改了集合A的结构（是结构上面的修改，而不是简单的修改集合元素的内容），
 *       那么这个时候程序就会抛出 ConcurrentModificationException 异常，从而产生fail-fast机制。
 */
public class FailFastTest {
    private static List<Integer> list = new ArrayList<>();
    /**
     * @desc:线程one迭代list
     * @Project:test
     * @file:FailFastTest.java
     * @Authro:chenssy
     * @data:2014年7月26日
     */
    private static class threadOne extends Thread{
        public void run() {
            Iterator<Integer> iterator = list.iterator();
            while(iterator.hasNext()){
                int i = iterator.next();
                System.out.println("ThreadOne 遍历:" + i);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * @desc:当i == 3时，修改list
     * @Project:test
     * @file:FailFastTest.java
     * @Authro:chenssy
     * @data:2014年7月26日
     */
    private static class threadTwo extends Thread{
        public void run(){
            int i = 0 ;
            while(i < 6){
                System.out.println("ThreadTwo run：" + i);
                if(i == 3){
                    list.remove(i);
                }
                i++;
            }
        }
    }

    public static void main(String[] args) {
        for(int i = 0 ; i < 10;i++){
            list.add(i);
        }
        new threadOne().start();
        new threadTwo().start();
    }
}
