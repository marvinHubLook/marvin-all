package cn.jmx;

import javax.management.*;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.io.IOException;

/**
 * @program: marvin-all
 * @description: TODO
 * @author: Mr.Wang
 * @create: 2018-12-07 14:45
 **/
public class JmxClient {
    public static void main(String[] args) throws IOException, MalformedObjectNameException, AttributeNotFoundException, MBeanException, ReflectionException, InstanceNotFoundException, InvalidAttributeValueException {
        JMXServiceURL url = new JMXServiceURL
                ("service:jmx:rmi:///jndi/rmi://localhost:9999/jmxrmi");

        JMXConnector jmxc = JMXConnectorFactory.connect(url,null);
        MBeanServerConnection mbsc = jmxc.getMBeanServerConnection();

        //ObjectName的名称与前面注册时候的保持一致
        ObjectName mbeanName = new ObjectName("jmxBean:name=helloService");

        System.out.println("Domains ......");
        String[] domains = mbsc.getDomains();

        for(int i=0;i<domains.length;i++)
        {
            System.out.println("doumain[" + i + "]=" + domains[i] );
        }

        System.out.println("MBean count = " + mbsc.getMBeanCount());

        //设置指定Mbean的特定属性值
        //这里的setAttribute、getAttribute操作只能针对bean的属性
        //例如对getName或者setName进行操作，只能使用Name，需要去除方法的前缀
        mbsc.setAttribute(mbeanName, new Attribute("Echo","杭州"));
        String echo = (String)mbsc.getAttribute(mbeanName, "Echo");
        System.out.println("echo=" + echo);

        HelloServiceMBean proxy = MBeanServerInvocationHandler.
                newProxyInstance(mbsc, mbeanName, HelloServiceMBean.class, false);

        proxy.print(echo);

        //invoke调用bean的方法，只针对非设置属性的方法
        System.out.println(mbsc.invoke(mbeanName, "print", new Object[]{"test"}, new String[]{String.class.getName()}));
    }

}
