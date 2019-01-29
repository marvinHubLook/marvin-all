package netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import org.junit.Test;

/**
 * @program: marvin-all
 * @description: TODO
 * @author: Mr.Wang
 * @create: 2018-12-03 17:34
 **/
public class TestByteBuf {
    /**
     * netty4.x 之后 , 对象的生命周期由它们的引用计数（reference counts）管理，而不是由垃圾收集器
     *
     * 当你释放（release）引用计数对象时，它的引用计数减1.
     *  如果引用计数为0，这个引用计数对象会被释放（deallocate），并返回对象池。
     */
    @Test
    public void testReference(){
        PooledByteBufAllocator allocator = PooledByteBufAllocator.DEFAULT;
        ByteBuf byteBuf = allocator.directBuffer(8);
        assert byteBuf.refCnt()==1;

        boolean destroyed = byteBuf.release();
        assert destroyed;
        assert byteBuf.refCnt() == 0;
        try {
            byteBuf.writeLong(0xdeadbeef);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 可通过retain()操作来增加引用计数，前提是此引用计数对象未被销毁
     */
    @Test
    public void testRemain(){
        PooledByteBufAllocator allocator = PooledByteBufAllocator.DEFAULT;
        ByteBuf byteBuf = allocator.directBuffer(8);
        assert byteBuf.refCnt() == 1;
        byteBuf.retain();
        assert byteBuf.refCnt() == 2;
        boolean destroyed = byteBuf.release();
        assert !destroyed;
        assert byteBuf.refCnt() == 1;
        assert byteBuf.release();
    }

    /**
     * 谁最后访问（access）了引用计数对象，谁就负责销毁（destruction）它
     *
     * 如果组件（component）A把一个引用计数对象传给另一个组件B，那么组件A通常不需要销毁对象，而是把决定权交给组件B。
     * 如果一个组件不再访问一个引用计数对象了，那么这个组件负责销毁它。
     */
    @Test
    public void testDestroy(){
        PooledByteBufAllocator allocator = PooledByteBufAllocator.DEFAULT;
        ByteBuf byteBuf = allocator.directBuffer(4);
        c(b(a(byteBuf)));
        assert byteBuf.refCnt() == 0;
    }


    public ByteBuf a(ByteBuf input) {
        input.writeByte(42);
        return input;
    }

    public ByteBuf b(ByteBuf input) {
        try {
            ByteBuf output = input.alloc().directBuffer(input.readableBytes() + 1);
            output.writeBytes(input);
            output.writeByte(42);
            return output;
        } finally {
            input.release();
        }
    }

    public void c(ByteBuf input) {
        System.out.println(input);
        input.release();
    }



    @Test
    public void testPooledAllocate(){
        int size=1024*8;
        PooledByteBufAllocator allocator = PooledByteBufAllocator.DEFAULT;
        allocator.directBuffer(size);
    }

}
