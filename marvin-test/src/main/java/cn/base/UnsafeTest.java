package cn.base;

/**
 * @Author : bingo
 * @category : unsafe 使用
 * @Date : 2018/5/14 14:53
 **/

import cn.lang.unsafe.UnsafeUtils;
import sun.misc.Unsafe;

public class UnsafeTest {
    private static final Unsafe unsafe = UnsafeUtils.unsafe;
    private static final long valueOffset;

    private transient volatile long value=0L;

    public long getValue() {
        return value;
    }
    static {
        try {
            valueOffset = unsafe.objectFieldOffset(UnsafeTest.class.getDeclaredField("value"));
        } catch (Exception ex) {
            throw new Error(ex);
        }
    }

    public final long getAndIncreament(long delta){
        long v;
        do {
            //获取内存中最新值
            v = unsafe.getLongVolatile(this, valueOffset);
            //通过CAS操作
        } while (!unsafe.compareAndSwapLong(this, valueOffset, v, v + 1L));
        return v;
    }

    
    public static void main(String[] args) throws InterruptedException {
        UnsafeTest unsafeTest = new UnsafeTest();
        for (int i=0;i<10;i++){
            Thread thread = new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    unsafeTest.getAndIncreament(1L);
                }
            });
            thread.start();
            thread.join();
        }
        System.out.println(unsafeTest.getValue());
    }

}
