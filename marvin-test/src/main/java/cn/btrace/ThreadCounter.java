package cn.btrace;

import com.sun.btrace.annotations.*;

import static com.sun.btrace.BTraceUtils.Counters;
import static com.sun.btrace.BTraceUtils.println;

/**
 * @program: marvin-all
 * @description: TODO
 * @author: Mr.Wang
 * @create: 2019-03-07 18:00
 **/
@BTrace
public class ThreadCounter {

    // create a jvmstat counter using @Export
    @Export
    private static long count;

    @OnMethod(
            clazz="java.lang.Thread",
            method="start"
    )
    public static void onnewThread(@Self Thread t) {
        // updating counter is easy. Just assign to
        // the static field!
        count++;
    }

    @OnTimer(2000)
    public static void ontimer() {
        // we can access counter as "count" as well
        // as from jvmstat counter directly.
        println(count);
        // or equivalently ...
        println(Counters.perfLong("btrace.cn.btrace.ThreadCounter.count"));
    }
}