package cn.refrence;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;

/**
 * @program: marvin-all
 * @description: TODO
 * @author: Mr.Wang
 * @create: 2018-12-03 11:06
 **/
public class ReferenceBase {




    public static void main(String[] args) throws InterruptedException {
        Object obj = new Object();
        ReferenceQueue<Object> refQueue =new ReferenceQueue<>();
        PhantomReference<Object> phanRef =new PhantomReference<>(obj, refQueue);

        Object objg = phanRef.get();
        //这里拿到的是null
        System.out.println(objg);
        //让obj变成垃圾
        obj=null;
        System.gc();
        Thread.sleep(3000);
        //gc后会将phanRef加入到refQueue中
        Reference<? extends Object> phanRefP = refQueue.remove();
        //这里输出true
        System.out.println(phanRefP==phanRef);
    }
}
