package cn.ASM;

import jdk.internal.org.objectweb.asm.ClassWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static jdk.internal.org.objectweb.asm.Opcodes.*;

/**
 * @Author : bingo
 * @category : TODO
 * @Date : 2018/10/11 16:20
 **/
public class ExampleWriter {

    public static void main(String[] args) throws IOException {
        ClassWriter cw = new ClassWriter(0);
        /**
         * 参数一: 版本
         * 参数二: 类访问标识
         * 参数三: 类的全限定名
         * 参数四: 和泛型相关的
         * 参数五: 父类的全限定名
         * 参数六: 要生成的类的直接实现的接口
         */
        cw.visit(V1_8, ACC_PUBLIC + ACC_ABSTRACT + ACC_INTERFACE,
                "cn/ExampleWriter", null, "java/lang/Object",
                new String[]{"Comparable"});

        cw.visitField(ACC_PUBLIC + ACC_FINAL + ACC_STATIC, "LESS", "I",
                null, new Integer(-1)).visitEnd();
        cw.visitField(ACC_PUBLIC + ACC_FINAL + ACC_STATIC, "EQUAL", "I",
                null, new Integer(0)).visitEnd();
        cw.visitField(ACC_PUBLIC + ACC_FINAL + ACC_STATIC, "GREATER", "I",
                null, new Integer(1)).visitEnd();
        cw.visitMethod(ACC_PUBLIC + ACC_ABSTRACT, "compareTo",
                "(Ljava/lang/Object;)I", null, null).visitEnd();
        cw.visitEnd();
        byte[] b = cw.toByteArray();
        FileOutputStream fos = new FileOutputStream(getPath()+"/ExampleWriter.class");
        System.out.println();
        fos.write(b);
        fos.close();
    }

    private static String getPath(){
        String path= ExampleWriter.class.getResource("").getPath();
        File file = new File(path+"/"+"out");
        if(!file.exists() && !file.isDirectory()){
            file.mkdirs();
        }
        System.out.println(file.getPath());
        return file.getPath();
    }
}
