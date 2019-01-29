package cn.collection.concurent;

import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @Author : bingo
 * @category : TODO
 * @Date : 2018/5/28 11:00
 **/
public class Test {
    public static void main(String[] args) {
        new CopyOnWriteArrayList<>();
        new HashMap<>();
    }
}
