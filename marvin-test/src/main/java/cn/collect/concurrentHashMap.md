##[ConcurrentHashMap](https://blog.csdn.net/u010723709/article/details/48007881)   

#### 关键点
* sizeCtl
   > **1、** 负数 代表 正在进行初始化或扩容操作
    >> -1 代表正在初始化  
     -N 表示有N-1个线程正在进行扩容操作  
     
   > **2、** 0 代表 hash表还没有被初始化
   
   > **3、** 正数 表示初始化或下一次进行扩容的大小
* Node 节点  
   > Node<K,V> implements Map.Entry<K,V>     
   value和next属性设置了volatile同步锁  
   允许调用setValue方法直接改变Node的value域，它增加了find方法辅助map.get()方法
   ```
       final int hash;  
       final K key;  
       volatile V val;//带有同步锁的value  
       volatile Node<K,V> next;//带有同步锁的next指针
    ```
   
* TreeNode
  > TreeNode<K,V> extends Node<K,V>  
    节点类，另外一个核心的数据结构。当链表长度过长的时候，会转换为TreeNode。  
    但是与HashMap不相同的是，它并不是直接转换为红黑树，而是把这些结点包装成TreeNode放在TreeBin对象中，由TreeBin完成对红黑树的包装。  
    而且TreeNode在ConcurrentHashMap集成自Node类，而并非HashMap中的集成自LinkedHashMap.Entry<K,V>类，也就是说TreeNode带有next指针，这样做的目的是方便基于TreeBin的访问。
    
  ```
    TreeNode<K,V> parent;  // red-black tree links
    TreeNode<K,V> left;
    TreeNode<K,V> right;
    TreeNode<K,V> prev;    // needed to unlink next upon deletion
    boolean red;
  ```        
* TreeBin
   > TreeBin<K,V> extends Node<K,V>   
   它代替了TreeNode的根节点，也就是说在实际的ConcurrentHashMap“数组”中，存放的是TreeBin对象，而不是TreeNode对象，这是与HashMap的区别
  ```
    TreeNode<K,V> root;
    volatile TreeNode<K,V> first;
    volatile Thread waiter;
    volatile int lockState;
    // values for lockState
    static final int WRITER = 1; // set while holding write lock
    static final int WAITER = 2; // set when waiting for write lock
    static final int READER = 4; // increment value for setting read lock

  ```
* ForwardingNode
   > ForwardingNode<K,V> extends Node<K,V>  
   一个用于连接两个table的节点类。它包含一个nextTable指针，用于指向下一张表。而且这个节点的key value next指针全部为null，它的hash值为-1. 这里面定义的find的方法是从nextTable里进行查询节点，而不是以自身为头节点进行查找 
   