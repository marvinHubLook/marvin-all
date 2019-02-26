package cn.instrumentation;

/**
 * @program: marvin-all
 * @description: TODO
 * @author: Mr.Wang
 * @create: 2019-02-13 15:57
 **/
public class Sample {
    public static native long getCurrentThreadCpuTime();

    static {
        System.loadLibrary("threadCPUTime"); //$NON-NLS-1$
    }

    public static void main(String args[]) {
        System.loadLibrary("threadCPUTime"); //$NON-NLS-1$
        helloWorld();
    }

    private static void helloWorld() {
        System.out.println("Hello world"); //$NON-NLS-1$
        String result = "";
        for (int i = 0; i < 10000; i++) {
            result += (char)(i%26 + 'a');
        }
    }
}
