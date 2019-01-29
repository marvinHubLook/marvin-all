package netty;

import io.netty.util.Recycler;
import io.netty.util.concurrent.FastThreadLocal;
import lombok.Data;
import org.junit.Assert;
import org.junit.Test;

/**
 * @program: marvin-all
 * @description: TODO
 * @author: Mr.Wang
 * @create: 2019-01-20 09:48
 **/
public class TestRecycler {
    private static final Recycler<User> userRecycler = new Recycler<User>() {
        @Override
        protected User newObject(Handle<User> handle) {
            return new User(handle);
        }
    };

    @Data
    static final class User {
        private String name;
        private Recycler.Handle<User> handle;

        public User(Recycler.Handle<User> handle) {
            this.handle = handle;
        }

        public void recycle() {
            handle.recycle(this);
        }

    }

    @Test
    public void test01(){
        // 1、从回收池获取对象
        User user1 = userRecycler.get();
        // 2、设置对象并使用
        user1.setName("hello,java");
        System.out.println(user1);
        // 3、对象恢复出厂设置
        user1.setName(null);
        // 4、回收对象到对象池
        user1.recycle();
        // 5、从回收池获取对象
        User user2 = userRecycler.get();
        Assert.assertEquals(user1.getName(),user2.getName());   //在Handler里面重写recycle,进行恢复出厂设置
        Assert.assertSame(user1,user2);
    }

    /**
     * 异线程创建回收
     * @throws InterruptedException
     */
    @Test
    public void testGetAndRecycleAtDifferentThread() throws InterruptedException {
        // 1、从回收池获取对象
        User user1 = userRecycler.get();
        // 2、设置对象并使用
        user1.setName("hello,java");

        Thread thread = new Thread(()->{
            System.out.println(user1);
            // 3、对象恢复出厂设置
            user1.setName(null);
            // 4、回收对象到对象池
            user1.recycle();
        });

        thread.start();
        thread.join();

        // 5、从回收池获取对象
        User user2 = userRecycler.get();
        Assert.assertSame(user1, user2);
    }

}
