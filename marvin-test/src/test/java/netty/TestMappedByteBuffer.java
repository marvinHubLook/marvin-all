package netty;

import org.junit.Test;

import java.io.*;
import java.lang.reflect.Method;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.AccessController;
import java.security.PrivilegedAction;

/**
 * @program: marvin-all
 * @description: TODO
 * @author: Mr.Wang
 * @create: 2019-01-10 15:22
 **/
public class TestMappedByteBuffer {
    private static final String PATH="D://temp//";

    private static int length = 0x2FFFFFFF;//1G
    private abstract static class Tester {
        private String name;
        public Tester(String name) {
            this.name = name;
        }
        public void runTest() {
            System.out.print(name + ": ");
            long start = System.currentTimeMillis();
            test();
            System.out.println(System.currentTimeMillis()-start+" ms");
        }
        public abstract void test();
    }

    private static Tester[] testers = {
            new Tester("Stream RW") {
                public void test() {
                    try (FileInputStream fis = new FileInputStream(
                            PATH+"a.txt");
                         DataInputStream dis = new DataInputStream(fis);
                         FileOutputStream fos = new FileOutputStream(
                                 PATH+"a.txt");
                         DataOutputStream dos = new DataOutputStream(fos);) {

                        byte b = (byte)0;
                        for(int i=0;i<length;i++) {
                            dos.writeByte(b);
                            dos.flush();
                        }
                        while (dis.read()!= -1) {
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            },
            new Tester("Mapped RW") {
                public void test() {
                    try (FileChannel channel = FileChannel.open(Paths.get(PATH+"b.txt"),
                            StandardOpenOption.READ, StandardOpenOption.WRITE);) {
                        MappedByteBuffer mapBuffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, length);
                        for(int i=0;i<length;i++) {
                            mapBuffer.put((byte)0);
                        }
                        mapBuffer.flip();
                        while(mapBuffer.hasRemaining()) {
                            mapBuffer.get();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            },
            new Tester("Mapped PRIVATE") {
                public void test() {
                    try (FileChannel channel = FileChannel.open(Paths.get(PATH+"c.txt"),
                            StandardOpenOption.READ, StandardOpenOption.WRITE);) {
                        MappedByteBuffer mapBuffer = channel.map(FileChannel.MapMode.PRIVATE, 0, length);
                        for(int i=0;i<length;i++) {
                            mapBuffer.put((byte)0);
                        }
                        mapBuffer.flip();
                        while(mapBuffer.hasRemaining()) {
                            mapBuffer.get();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
    };

    public static void clean(final Object buffer) throws Exception {
        AccessController.doPrivileged(new PrivilegedAction() {
            public Object run() {
                try {
                    Method getCleanerMethod = buffer.getClass().getMethod("cleaner",new Class[0]);
                    getCleanerMethod.setAccessible(true);
                    sun.misc.Cleaner cleaner =(sun.misc.Cleaner)getCleanerMethod.invoke(buffer,new Object[0]);
                    cleaner.clean();
                } catch(Exception e) {
                    e.printStackTrace();
                }
                return null;}});

    }

    @Test
    public void testStream(){
        Tester tester = testers[0];
        tester.runTest();
    }
    @Test
    public void testMappedRW(){
        Tester tester = testers[1];
        tester.runTest();
    }
    @Test
    public void testMappedPRIVATE(){
        Tester tester = testers[2];
        tester.runTest();
    }

}
