package cn.jmx.modelBean;

/**
 * @program: marvin-all
 * @description: TODO
 * @author: Mr.Wang
 * @create: 2019-03-08 15:59
 **/
public class Server {
    private long startTime;

    public Server() {   }

    public int start(){
        startTime = System.currentTimeMillis();
        return 0;
    }

    public long getUpTime(){
        return System.currentTimeMillis() - startTime;
    }
}
