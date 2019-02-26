package cn.proxy.javassist;

import javassist.util.proxy.MethodHandler;

import java.lang.reflect.Method;

/**
 * @program: marvin-all
 * @description: TODO
 * @author: Mr.Wang
 * @create: 2019-02-13 10:59
 **/
public class JavassistProxyHandler implements MethodHandler {


    @Override
    public Object invoke(Object proxy, Method method, Method method1, Object[] args) throws Throwable {
        if (Object.class == method.getDeclaringClass()) {
            String name = method.getName();
            if ("equals".equals(name)) {
                return proxy == args[0];
            } else if ("hashCode".equals(name)) {
                return System.identityHashCode(proxy);
            } else if ("toString".equals(name)) {
                return proxy.getClass().getName() + "@" +
                        Integer.toHexString(System.identityHashCode(proxy)) +
                        ", with InvokeHandler " + this;
            } else {
                throw new IllegalStateException(String.valueOf(method));
            }
        }
        return method.getName();
    }
}
