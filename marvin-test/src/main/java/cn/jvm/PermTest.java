package cn.jvm;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: marvin-all
 * @description: TODO
 * @author: Mr.Wang
 * @create: 2018-12-12 09:15
 **/
public class PermTest {

    static  String base = "StringPool";

    // java7 -XX:PermSize=4m -XX:MaxPermSize=4m -Xmx=8m
    // java8 -XX:MetaspaceSize=4m -XX:MaxMetaspaceSize=4m -Xmx=8m
    public static void main(String[] args) {
        test1();
    }

    private static void test1(){
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            String str = base + base;
            base = str;
            list.add(str.intern());
        }
    }

    private static void test2(){
        URL url = null;
        List<ClassLoader> classLoaderList = new ArrayList<ClassLoader>();
        try {
            url = new File("D:\\project\\IdeaProjects\\marvin-all\\marvin-test").toURI().toURL();
            URL[] urls = { url };
            while (true) {
                ClassLoader loader = new URLClassLoader(urls);
                classLoaderList.add(loader);
                loader.loadClass("cn.jvm.PermTest");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
