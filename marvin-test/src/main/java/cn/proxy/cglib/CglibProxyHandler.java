package cn.proxy.cglib;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @program: marvin-all
 * @description: Cglib拦截
 * @author: Mr.Wang
 * @create: 2019-02-12 17:12
 *
 * https://www.cnblogs.com/xrq730/p/6661692.html  拦截策略
 **/
public class CglibProxyHandler implements MethodInterceptor {

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        return method.getName();
    }
}
