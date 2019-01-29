package cn.ASM;

import org.junit.Test;

import java.io.*;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @program: marvin-all
 * @description: TODO
 * @author: Mr.Wang
 * @create: 2019-01-10 10:22
 **/
public class TestFastMethodAccessor {

    @Test
    public void  testOutClass() throws IOException {
        FastMethodAccessor accessor = FastMethodAccessor.get(Service.class);
        Method[] methods = accessor.getClass().getDeclaredMethods();
        Arrays.stream(methods).forEach(item -> System.out.println(item.toGenericString()));

        int index = accessor.getIndex("method", new Class[]{Integer.class, String.class});
        System.out.println(index);
        accessor.invoke(new ServiceImpl(),index,new Object[]{1,"2"});



        /*accessor.invoke(new ServiceImpl(),1,);*/
    }

    public interface Service {
        void method(Integer i, String s);
        void method(Integer i, CharSequence s);
        void method(int i, String s);
        void method(int i, CharSequence s);
        void method(long i, CharSequence s);
    }

    static class ServiceImpl implements  Service{

        @Override
        public void method(Integer i, String s) {
            System.out.println("------1----");
        }

        @Override
        public void method(Integer i, CharSequence s) {
            System.out.println("------2----");
        }

        @Override
        public void method(int i, String s) {
            System.out.println("------3----");
        }

        @Override
        public void method(int i, CharSequence s) {
            System.out.println("------4----");
        }

        @Override
        public void method(long i, CharSequence s) {
            System.out.println("------5----");
        }
    }
}
