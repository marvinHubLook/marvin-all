package cn.redis.PS.tool;

import cn.redis.PS.model.IpInfo;
import com.google.common.collect.Maps;

import java.util.Map;

/**
 * @Author : bingo
 * @category : TODO
 * @Date : 2018/9/20 18:31
 **/
public class IpPostFilter {
   private static Map<String,BloomFilter> map=Maps.newHashMap();

   private static  boolean exist(String key, IpInfo info){
       BloomFilter bloomFilter = map.get(key);
       if(null==bloomFilter){
           bloomFilter= new BloomFilter64bit(10000000, 22);
           bloomFilter.add(info.getIp()+":"+info.getPort());
           map.put(key,bloomFilter);
           return true;
       }
       return bloomFilter.contains(info.getIp()+":"+info.getPort());
   }
}
