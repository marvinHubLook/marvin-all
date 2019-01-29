package cn.baidu;

import cn.http.HttpUtils;
import com.google.common.collect.Maps;
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.HashMap;

/**
 * @program: marvin-all
 * @description: TODO
 * @author: Mr.Wang
 * @create: 2019-01-02 10:04
 **/
public class ImageView {

    //设置APPID/AK/SK
    public static final String APP_ID = "15318525";
    public static final String API_KEY = "bp6Qncqc1OBTb0ySmObBbnFt";
    public static final String SECRET_KEY = "grhSUAO5TvDSpLPaoLGVIl3CkGMGdj5Q";

    private static final String TOKEN="24.490685670acd335f1f6989f13694da43.2592000.1548988211.282335-15318525";

    public static final String image_word_url="https://aip.baidubce.com/rest/2.0/ocr/v1/accurate_basic";
    /**图像识别*/
    public static final String image_url="https://aip.baidubce.com/rest/2.0/image-classify/v2/advanced_general";

    public static void main(String[] args) throws IOException {
        /*getToken();*/
        //文字识别API

        String path = ImageView.class.getResource("/images/12306.png").getPath();
        System.out.println(path);
        InputStream in = new FileInputStream(new File(path));
        ByteOutputStream out = new ByteOutputStream();
        byte[] bytes=new byte[1024];
        int len=0;
        while((len=in.read(bytes))!=-1){
            out.write(bytes,0,len);
        }
        out.flush();
        byte[] datas = out.getBytes();
        in.close();
        out.close();
        HashMap<String, String> params = Maps.newHashMap();
        params.put("image", new String(Base64.getEncoder().encode(datas),"UTF-8"));
        params.put("access_token",TOKEN);
        String result = HttpUtils.post(image_url, params);
        System.out.println(result);
    }

    /**
     * 获取token
     * @return
     */
    private static String getToken(){
        String url = String.format("https://aip.baidubce.com/oauth/2.0/token?grant_type=client_credentials&client_id=%s&client_secret=%s&", API_KEY, SECRET_KEY);
        String result = HttpUtils.get(url);
        System.out.println(result);
        return result;
    }
}
