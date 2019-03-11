package cn.jmx.standard;

/**
 * @program: marvin-all
 * @description: TODO
 * @author: Mr.Wang
 * @create: 2019-03-08 15:45
 **/
public class ServerMonitor implements ServerMonitorMBean {
    private final ServerImpl target;

    public ServerMonitor(ServerImpl target){
        this.target = target;
    }
    @Override
    public long getUpTime(){
        return System.currentTimeMillis() - target.startTime;
    }
}
