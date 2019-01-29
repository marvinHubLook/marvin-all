package cn.base.quene;

import org.assertj.core.util.Lists;

import java.util.ArrayList;
import java.util.PriorityQueue;

/**
 * @program: marvin-all
 * @description: TODO
 * @author: Mr.Wang
 * @create: 2018-12-28 19:07
 **/
public class DemoPriorityQueue {

    public static void main(String[] args) {
        ArrayList<Integer> lists = Lists.newArrayList(5, 2, 3, 4, 1, 6);

        PriorityQueue<Integer> queue = new PriorityQueue<>();

        lists.forEach(item ->{
            queue.add(item);
            queue.forEach(System.out::print);
            System.out.println();
        });
    }

}
