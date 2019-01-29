package cn.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @program: marvin-all
 * @description: TODO
 * @author: Mr.Wang
 * @create: 2019-01-10 10:33
 **/
public class ByteUtils {
    private static final String CURRENT_PATH=ByteUtils.class.getResource("").getPath();

    public static void outBytes(byte[] bytes,String fileName){
        FileOutputStream fos=null;
        try {
            fos= new FileOutputStream(mkdirs()+"/"+fileName);
            fos.write(bytes);
            fos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(null!=fos) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private static String mkdirs(){
        File file = new File(CURRENT_PATH+"/"+"out");
        if(!file.exists() && !file.isDirectory()){
            file.mkdirs();
        }
        System.out.println(file.getPath());
        return file.getParent();
    }
}
