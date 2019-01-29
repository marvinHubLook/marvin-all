package cn.java8;

import com.google.common.collect.Maps;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @program: marvin-all
 * @description: TODO
 * @author: Mr.Wang
 * @create: 2018-12-25 15:43
 **/
public class LambdaTest {
    public static void main(String[] args) {
        test();
    }

    private static void test(){
        List<Map> lists=new ArrayList();
        HashMap<Object, Object> map1 = Maps.newHashMap();
        map1.put("id","1");
        map1.put("count","2");

        HashMap<Object, Object> map2 = Maps.newHashMap();
        map2.put("id","2");
        map2.put("count","3");

        lists.add(map1);
        lists.add(map2);
        Map<Object, Object> collect = lists.stream()
                .collect(Collectors.toMap(item -> item.get(("id")), item -> item.get(("count"))));
        System.out.println(collect);

    }
}
