
##### 1、List的实现类主要有: LinkedList, ArrayList, Vector, Stack。
>   (1) **LinkedList** 是双向链表实现的双端队列；它不是线程安全的，只适用于单线程。  
    (2) **ArrayList** 是数组实现的队列，它是一个动态数组；它也不是线程安全的，只适用于单线程。  
    (3) **Vector** 是数组实现的矢量队列，它也一个动态数组；不过和ArrayList不同的是，Vector是线程安全的，它支持并发。  
    (4) **Stack** 是Vector实现的栈；和Vector一样，它也是线程安全的。

##### 2、 Set的实现类主要有: HastSet和TreeSet。
>(1) **HashSet** 是一个没有重复元素的集合，它通过HashMap实现的；HashSet不是线程安全的，只适用于单线程。  
(2) **TreeSet** 也是一个没有重复元素的集合，不过和HashSet不同的是，TreeSet中的元素是有序的；它是通过TreeMap实现的；TreeSet也不是线程安全的，只适用于单线程。  
 

##### 3、 Map的实现类主要有: HashMap，WeakHashMap, Hashtable和TreeMap。
>(1) **HashMap** 是存储“键-值对”的哈希表；它不是线程安全的，只适用于单线程。  
(2) **WeakHashMap** 是也是哈希表；和HashMap不同的是，HashMap的“键”是强引用类型，而WeakHashMap的“键”是弱引用类型，也就是说当WeakHashMap 中的某个键不再正常使用时，会被从WeakHashMap中被自动移除。WeakHashMap也不是线程安全的，只适用于单线程。  
(3) **Hashtable** 也是哈希表；和HashMap不同的是，Hashtable是线程安全的，支持并发。  
(4) **TreeMap** 也是哈希表，不过TreeMap中的“键-值对”是有序的，它是通过R-B Tree(红黑树)实现的；TreeMap不是线程安全的，只适用于单线程。  


### JUC中的集合类

##### 1、List Set : CopyOnWriteArrayList, CopyOnWriteArraySet和ConcurrentSkipListSet  
>(1) **CopyOnWriteArrayList** 相当于线程安全的ArrayList，它实现了List接口。CopyOnWriteArrayList是支持高并发的。  
 (2) **CopyOnWriteArraySet** 相当于线程安全的HashSet，它继承于AbstractSet类。CopyOnWriteArraySet内部包含一个CopyOnWriteArrayList对象，它是通过CopyOnWriteArrayList实现的。
 
##### 2、 Map : ConcurrentHashMap和ConcurrentSkipListMap 
> (1) **ConcurrentHashMap** 是线程安全的哈希表(相当于线程安全的HashMap)；它继承于AbstractMap类，并且实现ConcurrentMap接口。ConcurrentHashMap是通过“锁分段”来实现的，它支持并发。  
(2) **ConcurrentSkipListMap** 是线程安全的有序的哈希表(相当于线程安全的TreeMap); 它继承于AbstractMap类，并且实现ConcurrentNavigableMap接口。ConcurrentSkipListMap是通过“跳表”来实现的，它支持并发。  
(3) **ConcurrentSkipListSet** 是线程安全的有序的集合(相当于线程安全的TreeSet)；它继承于AbstractSet，并实现了NavigableSet接口。ConcurrentSkipListSet是通过ConcurrentSkipListMap实现的，它也支持并发。

##### 3、 Queue ： ArrayBlockingQueue, LinkedBlockingQueue, LinkedBlockingDeque, ConcurrentLinkedQueue和ConcurrentLinkedDeque
>(1) **ArrayBlockingQueue** 是数组实现的线程安全的有界的阻塞队列。  
(2) **LinkedBlockingQueue** 是单向链表实现的(指定大小)阻塞队列，该队列按 FIFO（先进先出）排序元素。  
(3) **LinkedBlockingDeque** 是双向链表实现的(指定大小)双向并发阻塞队列，该阻塞队列同时支持FIFO和FILO两种操作方式。  
(4) **ConcurrentLinkedQueue** 是单向链表实现的无界队列，该队列按 FIFO（先进先出）排序元素。  
(5) **ConcurrentLinkedDeque** 是双向链表实现的无界队列，该队列同时支持FIFO和FILO两种操作方式。

  