package cn.jol;

import cn.lang.unsafe.UnsafeUtils;
import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.vm.VM;
import sun.misc.Unsafe;

import java.util.HashMap;

/**
 * @program: marvin-all
 * @description: TODO
 * @author: Mr.Wang
 * @create: 2018-12-29 12:03
 *   http://www.importnew.com/1305.html
 *   https://www.jianshu.com/p/cb5e09facfee
 *   Java对象所占内存的大小 : https://www.jianshu.com/p/9d729c9c94c4
 **/
public class ObjectSize {
    public static void main(String[] args) throws NoSuchFieldException {
        System.out.println(VM.current().details());
        System.out.println(ClassLayout.parseClass(VO.class).toPrintable());
        System.out.println("=================");
        Unsafe unsafe = UnsafeUtils.unsafe;
        VO vo = new VO();
        vo.a=2;
        vo.b=3;
        vo.d=new HashMap<>();
        long aoffset = unsafe.objectFieldOffset(VO.class.getDeclaredField("a"));
        System.out.println("aoffset="+aoffset);
        // 获取a的值
        Object va = unsafe.getObject(vo, aoffset);
        System.out.println("va="+va);


        System.out.println("=======------------==========");
        System.out.println(ClassLayout.parseClass(B.class).toPrintable());
    }

}

/**
 *
 * > * 1: 除了对象整体需要按8字节对齐外，每个成员变量都尽量使本身的大小在内存中尽量对齐。比如 int 按 4 位对齐，long 按 8 位对齐。
 * >
 * > * 2：类属性按照如下优先级进行排列：长整型和双精度类型；整型和浮点型；字符和短整型；字节类型和布尔类型，最后是引用类型。这些属性都按照各自的单位对齐。
 * >
 * > * 3：优先按照规则一和二处理父类中的成员，接着才是子类的成员。
 * >
 * > * 4：当父类中最后一个成员和子类第一个成员的间隔如果不够4个字节的话，就必须扩展到4个字节的基本单位。
 * >
 * > * 5：如果子类第一个成员是一个双精度或者长整型，并且父类并没有用完8个字节，JVM会破坏规则2，按照整形（int），短整型（short），字节型（byte），引用类型（reference）的顺序，向未填满的空间填充。
 */
class VO {
    public Integer a = 0;
    public long b = 0;
    public String c= "123";
    public Object d= null;
    public int e = 100;
    public static int f= 0;
    public static String g= "";
    public Object h= null;
    public boolean i;
    public boolean j;
}

class A {
    byte a;
}

class B extends A{
    long b;
    short c;
    byte d;
}