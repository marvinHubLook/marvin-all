package cn.proxy;

import cn.proxy.byteBuddy.ByteBuddyProxyHandler;
import cn.proxy.cglib.CglibProxyHandler;
import cn.proxy.jdk.JdkProxyHandler;
import net.sf.cglib.core.DebuggingClassWriter;
import net.sf.cglib.proxy.Enhancer;
import org.assertj.core.internal.bytebuddy.ByteBuddy;
import org.assertj.core.internal.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import org.assertj.core.internal.bytebuddy.implementation.MethodDelegation;
import org.assertj.core.internal.bytebuddy.matcher.ElementMatchers;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

import static cn.util.Preconditions.checkArgument;


/**
 * @program: marvin-all
 * @description: TODO
 * @author: Mr.Wang
 * @create: 2019-02-12 17:02
 **/
public enum Proxies {
    JDK_PROXY(new ProxyDelegate() {

        @Override
        public <T> T newProxy(Class<T> interfaceType, Object handler) {
            checkArgument(handler instanceof InvocationHandler, "handler must be a InvocationHandler");

            Object object = Proxy.newProxyInstance(
                    interfaceType.getClassLoader(), new Class<?>[] { interfaceType }, (InvocationHandler) handler);

            return interfaceType.cast(object);
        }
    }),
    BYTE_BUDDY(new ProxyDelegate() {

        @Override
        public <T> T newProxy(Class<T> interfaceType, Object handler) {
            Class<? extends T> cls = new ByteBuddy()
                    .subclass(interfaceType)
                    .method(ElementMatchers.isDeclaredBy(interfaceType))
                    .intercept(MethodDelegation.to(handler, "handler"))
                    .make()
                    .load(interfaceType.getClassLoader(), ClassLoadingStrategy.Default.INJECTION)
                    .getLoaded();

            try {
                return cls.newInstance();
            } catch (Throwable t) {

            }
            return null; // never get here
        }
    }),
    CGLIB_PROXY(new ProxyDelegate() {
        @Override
        public <T> T newProxy(Class<T> interfaceType, Object handler) {
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(interfaceType);
            enhancer.setCallback((CglibProxyHandler)handler);
            return interfaceType.cast(enhancer.create());
        }
    })/*,
    JAVASSIST_BYTECODE_PROXY(new ProxyDelegate() {
        @Override
        public <T> T newProxy(Class<T> interfaceType, Object handler){
            return Proxy.getProxy(interfaceType).newInstance((InvocationHandler)handler);
        }
    }),
    JAVASSIST_DYNAMIC_PROXY(new ProxyDelegate() {
        @Override
        public <T> T newProxy(Class<T> interfaceType, Object handler) {
            ProxyFactory proxyFactory = new ProxyFactory();
            proxyFactory.setInterfaces(new Class[] { interfaceType });
            Class<?> proxyClass = proxyFactory.createClass();
            T javassistProxy = (T)ReflectUtil.newInstance(proxyClass);
            ((ProxyObject) javassistProxy).setHandler((MethodHandler)handler);
            return javassistProxy;
        }
    })*/;

    private final ProxyDelegate delegate;

    Proxies(ProxyDelegate delegate) {
        this.delegate = delegate;
    }

    public static Proxies getDefault() {
        return BYTE_BUDDY;
    }

    public <T> T newProxy(Class<T> interfaceType, Object handler) {
        return delegate.newProxy(interfaceType, handler);
    }

    interface ProxyDelegate {

        /**
         * Returns a proxy instance that implements {@code interfaceType} by dispatching
         * method invocations to {@code handler}. The class loader of {@code interfaceType}
         * will be used to define the proxy class.
         */
        <T> T newProxy(Class<T> interfaceType, Object handler);
    }





    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");  //该设置用于输出jdk动态代理产生的类  -- 默认会在 当前项目 sun/proxy 项目下
        /**
         *
         *  h 是  class cn.proxy.jdk.JdkProxyHandler
         *
         *  //  D:\project\IdeaProjects\marvin-all\cn\proxy\$Proxy0.class  中的代码
         *  public final String test1(String paramString)
         *   {
         *     try
         *     {
         *       return (String)this.h.invoke(this, m3, new Object[] { paramString });
         *     }
         *     catch (Error|RuntimeException localError)
         *     {
         *       throw localError;
         *     }
         *     catch (Throwable localThrowable)
         *     {
         *       throw new UndeclaredThrowableException(localThrowable);
         *     }
         *   }
         */
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "D:\\class");  //该设置用于输出cglib动态代理产生的类
        /**
         *   CGLIB$CALLBACK_0;//拦截器及集成MethodInterceptor
         *
         *   //  D:\class\cn\proxy\Proxies$TestInterface$$EnhancerByCGLIB$$425eee22.class  中的代码
         *
         *   public final String test1(String paramString)
         *   {
         *     MethodInterceptor tmp4_1 = this.CGLIB$CALLBACK_0;
         *     if (tmp4_1 == null)
         *     {
         *       tmp4_1;
         *       CGLIB$BIND_CALLBACKS(this);
         *     }
         *     MethodInterceptor tmp17_14 = this.CGLIB$CALLBACK_0;
         *     if (tmp17_14 != null) {
         *       return (String)tmp17_14.intercept(this, CGLIB$test1$4$Method, new Object[] { paramString }, CGLIB$test1$4$Proxy);
         *     }
         *     return super.test1(paramString);
         *   }
         */

        TestInterface jdkProxyObj = Proxies.JDK_PROXY.newProxy(TestInterface.class, new JdkProxyHandler());
        TestInterface cglibProxyObj=Proxies.CGLIB_PROXY.newProxy(TestInterface.class,new CglibProxyHandler());
        TestInterface byteBuddy=Proxies.BYTE_BUDDY.newProxy(TestInterface.class,new ByteBuddyProxyHandler());
        System.out.println(jdkProxyObj.getClass().getResource("").getPath());
        jdkProxyObj.test1("hello");

//        Field h = jdkProxyObj.getClass().getSuperclass().getDeclaredField("h");
//        h.setAccessible(true);
//        Object obj = h.get(jdkProxyObj);
//        System.out.println(obj.getClass());

        cglibProxyObj.test1("hello");
        byteBuddy.test1("hello");

    }

    interface TestInterface {
        String test1(String arg);
    }

}
