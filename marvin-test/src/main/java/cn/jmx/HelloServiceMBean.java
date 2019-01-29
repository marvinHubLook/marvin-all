package cn.jmx;

/**
 * @Author : bingo
 * @category : TODO
 * @Date : 2018/12/7 14:21
 **/
public interface HelloServiceMBean {
    void print(String echo);

    String getEcho();

    void setEcho(String echo);
}
