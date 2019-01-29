package reference;

import org.junit.Test;

import java.lang.ref.*;
import java.util.WeakHashMap;

import static org.junit.Assert.*;

/**
 * @program: marvin-all
 * @description: TODO
 * @author: Mr.Wang
 * @create: 2018-12-03 16:53
 **/
public class TestReference {
    /** 
     * 一旦没有指向 referent 的强引用, weak reference 在 GC 后会被自动回收 
     */
    @Test
    public void testWeakReference() {
        Object referent = new Object();
        WeakReference<Object> weakRerference =new WeakReference<Object>(referent);
        assertSame(referent, weakRerference.get());
        referent=null;
        System.gc();
        assertNull(weakRerference.get());
    }

    @Test
    public void testWeakReference1() throws InterruptedException {
        Object referent = new Object();
        ReferenceQueue<Object> referenceQueue = new ReferenceQueue<Object>();
        WeakReference<Object> weakReference =new WeakReference<Object>(referent,referenceQueue);

        assertFalse(weakReference.isEnqueued());
        Reference<? extends Object> polled = referenceQueue.poll();

        assertNull(polled);
        referent=null;
        System.gc();
        assertTrue(weakReference.isEnqueued());

        Reference<?> removed = referenceQueue.remove();
        assertNotNull(removed);
    }

    @Test
    public void testWeakReference2() throws InterruptedException {
        WeakHashMap<Object, Object> weakMap = new WeakHashMap<Object,Object>();
        Object key=new Object();
        Object value = new Object();

        weakMap.put(key,value);
        assertTrue(weakMap.containsValue(value));

        key=null;
        System.gc();;
        /**
         * 等待无效 entries 进入 ReferenceQueue 以便下一次调用 getTable 时被清理
         */
        Thread.sleep(1000);
        /**
         * 一旦没有指向 key 的强引用, WeakHashMap 在 GC 后将自动删除相关的 entry
         */
        assertFalse(weakMap.containsValue(value));
    }

    /**
     * SoftReference 于 WeakReference 的特性基本一致， 最大的区别在于 SoftReference 会尽可能长的保留引用直到 JVM 内存不足时才会被回收(虚拟机保证),
     *  这一特性使得 SoftReference 非常适合缓存应用
     */
    @Test
    public void testSoftReference(){
        Object obj = new Object();
        SoftReference<Object> softReference = new SoftReference<Object>(obj);
        assertNotNull(softReference.get());
        obj=null;
        System.gc();
        assertNotNull(softReference.get());
    }

    /**
     * Phantom Reference(幽灵引用) 与 WeakReference 和 SoftReference 有很大的不同,  因为它的 get() 方法永远返回 null, 这也正是它名字的由来
     *
     * PhantomReference 唯一的用处就是跟踪 referent 何时被 enqueue 到 ReferenceQueue 中.
     */
    @Test
    public void testPhantomReference(){
        Object referent = new Object();
         PhantomReference<Object> phantomReference = new PhantomReference<Object>(referent, new ReferenceQueue<Object>());
         /**
          * phantom reference 的 get 方法永远返回 null
          */
         assertNull(phantomReference.get());

    }

    @Test
    public void testPhantomReference2() throws InterruptedException {
        Object obj = new Object();
        ReferenceQueue<Object> refQueue =new ReferenceQueue<>();
        PhantomReference<Object> phanRef =new PhantomReference<>(obj, refQueue);
        Object objg = phanRef.get();
        assertNull(objg);  //这里拿到的是null
        obj=null;  //让obj变成垃圾
        System.gc();
        Thread.sleep(3000);

        Reference<? extends Object> phanRefP = refQueue.remove();  //gc后会将phanRef加入到refQueue中
        assertSame(phanRefP,phanRef); //这里输出true
    }
}
