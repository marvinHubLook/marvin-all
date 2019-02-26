package cn.proxy.byteBuddy;

import org.assertj.core.internal.bytebuddy.implementation.bind.annotation.AllArguments;
import org.assertj.core.internal.bytebuddy.implementation.bind.annotation.Origin;
import org.assertj.core.internal.bytebuddy.implementation.bind.annotation.RuntimeType;

import java.lang.reflect.Method;

/**
 * @program: marvin-all
 * @description: TODO
 * @author: Mr.Wang
 * @create: 2019-02-12 17:06
 **/
public class ByteBuddyProxyHandler {

    @SuppressWarnings("UnusedParameters")
    @RuntimeType
    public Object invoke(@Origin Method method, @AllArguments @RuntimeType Object[] args) throws Throwable {
        return method.getName();
    }
}
