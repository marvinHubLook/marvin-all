package cn.redis.PS.tool;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * @Author : bingo
 * @category : TODO
 * @Date : 2018/9/20 18:55
 **/
public class HttpInvoker {
    private HttpInvoker(){};

    public static Document get(String url){
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return doc;
    }

    public static Elements get(String url,String xpath){
        Document document = get(url);
        Elements elements=null;
        if(null==document) elements=document.select(xpath);
        return elements;
    }

}
