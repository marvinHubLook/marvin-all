package cn.instrumentation;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;

/**
 * A trivial example program that basically just says hello!
 */
public class Greeting implements ClassFileTransformer {

    public static void premain(String options, Instrumentation ins) {
        if (options != null) {
            System.out.printf("  I've been called with options: \"%s\"\n",options);
        } else
            System.out.println("  I've been called with no options.");
        ins.addTransformer(new Greeting());
    }

    /**
     * 通过这个方法，代理可以得到虚拟机载入的类的字节码（通过 classfileBuffer 参数）。代理的各种功能一般是通过操作这一串字节码得以实现的
     * @param loader
     * @param className
     * @param cBR
     * @param pD
     * @param classfileBuffer 字节码
     * @return
     * @throws IllegalClassFormatException
     */
    @Override
    public byte[] transform(ClassLoader loader, String className, Class cBR,
                            java.security.ProtectionDomain pD, byte[] classfileBuffer)
            throws IllegalClassFormatException {
        System.out.println("Hello,\t"+className);
        return null;
    }

    private final TrivialShutdownHook _hook = new TrivialShutdownHook();

    class TrivialShutdownHook extends Thread {
        public TrivialShutdownHook() {
            Runtime.getRuntime().addShutdownHook(this);
        }
        public void run() {
            try {
                System.out.println("The JVM is shutting down now!");
            } catch (Throwable e) {
                System.err.println("PrintClassOnShutdown::run " + e);
            }
        }
    }

} // class TrivialInstrumenter

/*
 *
 * Based on the code at:
 * http://www.mackmo.com/nick/blog/java/?permalink=JDK1-5-java-lang-instrument.txt
 *
 * Another example (uses ASM) at:
 * http://www.surguy.net/articles/removing-log-messages.xml
 *
 * A BCEL-using example is at:
 * http://www.onjava.com/pub/a/onjava/2004/06/30/insidebox1.html?page=last
 *
 */
