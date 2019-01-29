package cn.jmx;

/**
 * @program: marvin-all
 * @description: TODO
 * @author: Mr.Wang
 * @create: 2018-12-07 14:22
 **/
public class HelloService implements HelloServiceMBean{
    private String echo;

    @Override
    public void print(String echo) {
        System.out.println("echo 的值是:"+echo);
    }

    @Override
    public String getEcho() {
        return echo;
    }
    @Override
    public void setEcho(String echo) {
        this.echo = echo;
    }
}
