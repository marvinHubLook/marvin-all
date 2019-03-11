package cn.btrace;

/**
 * @program: marvin-all
 * @description: TODO
 * @author: Mr.Wang
 * @create: 2019-03-05 16:31
 **/

import com.sun.btrace.BTraceUtils;
import com.sun.btrace.annotations.*;

import static com.sun.btrace.BTraceUtils.println;

@BTrace
public class ArgArray {
    /*@OnMethod(clazz = "cn.edu.web.DemoContrller", method = "demoIndex", location = @Location(Kind.RETURN))
    public static void getFuncRunTime( @ProbeMethodName String pmn, @Duration long duration) {
        println( "interface " + pmn + strcat("time (ms) is: ", str(duration / 1000000)) );
    }*/

    @OnMethod( clazz = "cn.edu.web.DemoContrller", method = "demoIndex", location = @Location(Kind.ENTRY))
    public static void getFuncEntry(@ProbeClassName String pcn, @ProbeMethodName String pmn, String message ) {

        println("package: " + pcn);
        println("method: " + pmn);

        BTraceUtils.print("params: ");
        BTraceUtils.printFields(message);

        /*Field oneFiled = BTraceUtils.field("cn.edu.web.DemoContrller.User", "userName");
        println("userName: " + BTraceUtils.get(oneFiled, user));

        oneFiled = BTraceUtils.field("cn.codesheep.springbt_brace.entity.User", "userAge");
        println("userAge: " + BTraceUtils.get(oneFiled, user));*/

    }
}
