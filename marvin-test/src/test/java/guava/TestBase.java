package guava;

import com.google.common.base.Optional;
import com.google.common.collect.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * @program: marvin-all
 * @description: TODO
 * @author: Mr.Wang
 * @create: 2018-12-01 09:28
 **/
public class TestBase {


    /**
     * Optional.of(T)	创建指定引用的Optional实例，若引用为null则快速失败
     * Optional.absent()	创建引用缺失的Optional实例
     * Optional.fromNullable(T)	创建指定引用的Optional实例，若引用为null则表示缺失
     *
     * boolean isPresent()	如果Optional包含非null的引用（引用存在），返回true
     * T get()	返回Optional所包含的引用，若引用缺失，则抛出java.lang.IllegalStateException
     * T or(T)	返回Optional所包含的引用，若引用缺失，返回指定的值
     * T orNull()	返回Optional所包含的引用，若引用缺失，返回null
     * Set<T> asSet()	返回Optional所包含引用的单例不可变集，如果引用存在，返回一个只有单一元素的集合，如果引用缺失，返回一个空集合。
     */
    @Test
    public void testOptional(){
        Optional<Integer> optional = Optional.of(5);
        Optional.absent();


    }

    /**
     * 前置判断
     */
    @Test
    public void testPreconditions(){
        checkArgument(false,"this is null");
        System.out.println("========");

    }
    /**
     * 不可变集合
     */
    @Test
    public void testImmutable(){
        ImmutableSet.<GuavaEntity>builder()
                .add(new GuavaEntity("f1","l1",2))
                .build();

        ImmutableSortedSet.of("a", "b", "c", "a", "d", "b");

    }

    /**
     * Multiset  与Set 不同的是，可以重复
     */
    @Test
    public void testMultiset(){
        String strWorld="wer|dfd|dd|dfd|dda|de|dr";
        String[] words=strWorld.split("\\|");
        List<String> wordList=new ArrayList<String>();
        for (String word : words) {
            wordList.add(word);
        }
        Multiset<String> wordsMultiset = HashMultiset.create();
        wordsMultiset.addAll(wordList);

        for(String key:wordsMultiset.elementSet()){
            System.out.println(key+" count："+wordsMultiset.count(key));
        }
    }

    /**
     * Multimap
     */
    @Test
    public void teststuMultimap(){
        Multimap<String,GuavaEntity> scoreMultimap = ArrayListMultimap.create();
        for(int i=10;i<20;i++){
            if(new Random().nextInt(10)>=5){
                scoreMultimap.put(">=5",new GuavaEntity("f"+i,"l"+1,i));
            }else{
                scoreMultimap.put("<5",new GuavaEntity("f"+i,"l"+1,i));
            }
        }
        System.out.println("scoreMultimap:"+scoreMultimap.size());
        System.out.println("scoreMultimap:"+scoreMultimap.keys());
    }

    /**
     * BiMap 实现键值对双向映射
     */
    @Test
    public void testBiMap(){
        BiMap<Integer,String> logfileMap = HashBiMap.create();
        logfileMap.put(1,"a.log");
        logfileMap.put(2,"b.log");
        logfileMap.put(3,"c.log");
        logfileMap.forcePut(4,"c.log");  //覆盖
        System.out.println("logfileMap:"+logfileMap);
        BiMap<String,Integer> filelogMap = logfileMap.inverse();
        System.out.println("filelogMap:"+filelogMap);
    }

    /**
     * Table 多个索引的数据结构   类似 Map<FirstName, Map<LastName, Person>>
     */
    @Test
    public void testTable(){
        Table<String, Integer, String> aTable = HashBasedTable.create();
        for (char a = 'A'; a <= 'C'; ++a) {
            for (Integer b = 1; b <= 3; ++b) {
                aTable.put(Character.toString(a), b, String.format("%c%d", a, b));
            }
        }

        System.out.println(aTable.column(2));
        System.out.println(aTable.row("B"));
        System.out.println(aTable.get("B", 2));

        System.out.println(aTable.contains("D", 1));
        System.out.println(aTable.containsColumn(3));
        System.out.println(aTable.containsRow("C"));
        System.out.println(aTable.columnMap());
        System.out.println(aTable.rowMap());
        System.out.println(aTable.remove("B", 3));
        System.out.println(aTable.rowMap());
    }
    /***
     *
     * ClassToInstanceMap    Map<Class<? extends B>, B>
     */
    @Test
    public void testClassToInstanceMap(){
        ClassToInstanceMap<Number> numberDefaults=MutableClassToInstanceMap.create();
        numberDefaults.putInstance(Integer.class, Integer.valueOf(0));
    }

    /**
     * RangeSet描述了一组不相连的、非空的区间。当把一个区间添加到可变的RangeSet时，所有相连的区间会被合并，空区间会被忽略
     */
    @Test
    public void testRangeSet(){
        RangeSet<Integer> rangeSet = TreeRangeSet.create();
        rangeSet.add(Range.closed(1, 10)); // {[1,10]}
        rangeSet.add(Range.closedOpen(11, 15));//不相连区间:{[1,10], [11,15)}
        rangeSet.add(Range.closedOpen(15, 20)); //相连区间; {[1,10], [11,20)}
        rangeSet.add(Range.openClosed(0, 0)); //空区间; {[1,10], [11,20)}
        rangeSet.remove(Range.open(5, 10)); //分割[1, 10]; {[1,5], [10,10], [11,20)}
    }

}
