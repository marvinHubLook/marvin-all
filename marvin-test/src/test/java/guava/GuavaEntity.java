package guava;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * @program: marvin-all
 * @description: TODO
 * @author: Mr.Wang
 * @create: 2018-12-01 09:53
 **/
public class GuavaEntity implements Comparable<GuavaEntity>{
    private String lastName;
    private String firstName;
    private Integer score;

    @Override
    public int compareTo(GuavaEntity o) {
        return ComparisonChain.start()
                .compare(firstName,o.firstName)
                .compare(lastName,o.lastName)
                .compare(score,o.score, Ordering.natural().nullsLast())
                .result();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof GuavaEntity) {
            GuavaEntity that = (GuavaEntity) obj;
            return Objects.equal(firstName, that.firstName)
                    && Objects.equal(lastName, that.lastName)
                    && Objects.equal(score, that.score);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(firstName,lastName,score);
    }

    @Override
    public String toString() {
        return  MoreObjects.toStringHelper(this)
                    .add("firstName",firstName)
                    .add("lastName",lastName)
                    .add("score",score)
                    .toString();
    }


    public GuavaEntity(String lastName, String firstName, Integer score) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.score = score;
    }
    public GuavaEntity() {
        super();
    }

    public static void main(String[] args) {
        GuavaEntity e1=new GuavaEntity("f1","l1",1);
        GuavaEntity e2=new GuavaEntity("f1","l2",3);
        GuavaEntity e3=new GuavaEntity("f2","l1",2);
        GuavaEntity e4=new GuavaEntity("f0","l5",1);

        ArrayList<GuavaEntity> lists = Lists.newArrayList(e1, e2, e3, e4);

        //默认排序
        Collections.sort(lists);
        System.out.println(lists);

        //自定义排序
        Ordering<GuavaEntity> generalOrder = Ordering.from((GuavaEntity o1, GuavaEntity o2) -> {
            return ComparisonChain.start().compare(o1.score, o2.score).compare(o1.firstName, o2.firstName).result();
        }).nullsFirst().reverse();
        Collections.sort(lists,generalOrder);
        System.out.println(lists);

        //前N个元素
        List<GuavaEntity> lists2 = generalOrder.greatestOf(lists, 2);
        System.out.println(lists2);

        System.out.println(generalOrder.max(lists));


    }
}
