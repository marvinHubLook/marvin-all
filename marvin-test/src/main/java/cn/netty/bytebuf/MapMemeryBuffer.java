package cn.netty.bytebuf;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @program: marvin-all
 * @description: MappedByteBuffer 超大文件读写
 * @author: Mr.Wang
 * @create: 2019-01-10 14:57
 **/
public class MapMemeryBuffer {

    public static void main(String[] args) throws IOException {
        ByteBuffer byteBufer = ByteBuffer.allocate(1024 * 10 * 1024);
        byte[] bbb = new byte[14 * 1024 * 1024];
        FileInputStream fis = new FileInputStream("D:\\迅雷下载\\ubuntu-16.04.5-desktop-amd64.iso");
        FileOutputStream fos = new FileOutputStream("D:\\迅雷下载\\copy.iso");
        FileChannel fc = fis.getChannel();
        long startTime = System.currentTimeMillis();

//        fc.read(byteBufer);
        MappedByteBuffer mbb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());

        System.out.println(fc.size());
        long endTime = System.currentTimeMillis();// 得到当前的时间
        System.out.println("Read time :" + (endTime - startTime) + "ms");
        startTime= System.currentTimeMillis();

//        fos.write(bbb);
        mbb.flip();
        fos.write(bbb);

        endTime = System.currentTimeMillis();
        System.out.println("Write time :" + (endTime - startTime) + "ms");
        fos.flush();
        fc.close();
        fis.close();
        fos.close();

    }
}
