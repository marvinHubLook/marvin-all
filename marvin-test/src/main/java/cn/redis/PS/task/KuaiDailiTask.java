package cn.redis.PS.task;

import cn.http.HttpUtils;
import cn.redis.PS.tool.HttpInvoker;
import cn.redis.PS.tool.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.TimerTask;

/**
 * @Author : bingo
 * @category : TODO
 * @Date : 2018/9/20 18:05
 **/
public class KuaiDailiTask extends TimerTask {
    private static String startUrl="https://www.kuaidaili.com/proxylist/{}/";
    private KuaiDailiTask(){super();};

    public static void startTask(){
        KuaiDailiTask kuaiDailiTask = new KuaiDailiTask();
        new Thread(kuaiDailiTask).start();
        /*Timer timer = new Timer();
        long delay = 0;
        long period = 1000 * 60 * 6;
        // 每一个小时采集一次ip
        timer.scheduleAtFixedRate(kuaiDailiTask,delay,period);*/
    }
    private void domian(){
        int start = 1;
        while(true){
            start=start%10;
            Document document = HttpInvoker.get(StringUtils.format(startUrl, String.valueOf(start)));
            start++;
            if(null!=document){
                parseDocument(document);
            }
        }
    }

    private void parseDocument(Document document) {
        Elements elements = document.select("#freelist tbody tr");
        if(null!=elements){
            for(Element el:elements){
               String ip= el.child(0).text();
               String port= el.child(1).text();
               putInfo(ip,port);
            }
        }
    }

    private void putInfo(String ip, String port) {
        /*Publisher.addData(ChannelEnum.PROXY,ip+":"+port);*/
        String content=HttpUtils.get("http://cmbchina.jckk.net/index",ip,Integer.valueOf(port));
        System.out.printf("ip:"+ip);
        System.out.printf("port:"+port);
        System.out.printf(content);
        /*System.out.printf(StringUtils.format("ip:{} \t port:{} \t content:{}",ip,port,conetent));*/
    }


    @Override
    public void run() {
        domian();
    }

}
