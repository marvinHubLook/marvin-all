package cn.redis.PS.tool;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author : bingo
 * @category : TODO
 * @Date : 2018/9/20 19:49
 **/
public class StringUtils extends org.apache.commons.lang3.StringUtils {

    public static String format(String txt,String ... strs){
        String result="";
        if(StringUtils.isBlank(txt)) result="";
        else if(null==strs || strs.length<1){
            result=txt;
        }else{
            String strRegex = "\\{\\}";
            Pattern p = Pattern.compile(strRegex);
            Matcher m = p.matcher(txt);
            StringBuffer buff = new StringBuffer();
            int i=0;
            while (m.find()) {
                m.appendReplacement(buff, strs[i]);
                i++;
            }
            m.appendTail(buff);
            result=buff.toString();
        }
        return result;
    }
}
