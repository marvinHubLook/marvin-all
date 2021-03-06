package bytebuddy;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;
import org.junit.Assert;
import org.junit.Test;

/**
 * @program: marvin-all
 * @description: TODO
 * @author: Mr.Wang
 * @create: 2019-02-13 11:47
 **/
public class TestByteBuddy {

    @Test
    public void test01() throws IllegalAccessException, InstantiationException {
        Class<?> dynamicType = new ByteBuddy()
                .subclass(Object.class)
                .method(ElementMatchers.named("toString"))
                .intercept(FixedValue.value("Hello World!"))
                .make()
                .load(getClass().getClassLoader())
                .getLoaded();
        Assert.assertEquals(dynamicType.newInstance().toString(),"Hello World!");
    }

    @Test
    public void test02() throws IllegalAccessException, InstantiationException {
        Class<? extends java.util.function.Function> dynamicType = new ByteBuddy()
                .subclass(java.util.function.Function.class)
                .method(ElementMatchers.named("apply"))
                .intercept(MethodDelegation.to(new GreetingInterceptor()))
                .make()
                .load(getClass().getClassLoader())
                .getLoaded();
        Assert.assertEquals((String) dynamicType.newInstance().apply("Byte Buddy"),"Hello from Byte Buddy");
    }



    public static class GreetingInterceptor{
        public Object greet(Object argument) {
            return "Hello from " + argument;
        }
    }



}
