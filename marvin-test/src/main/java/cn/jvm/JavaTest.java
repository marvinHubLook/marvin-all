package cn.jvm;

/**
 * @program: marvin-all
 * @description: TODO
 * @author: Mr.Wang
 * @create: 2018-12-12 09:58
 **/
public class JavaTest {
    public static void main(String[] args) {
        byte[] b = null;
        for (int i = 0; i < 10; i++)
            b = new byte[1 * 1024 * 1024];
    }
}
