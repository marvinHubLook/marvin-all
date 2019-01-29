## 一、 参数介绍

* **DEFAULT_INITIAL_CAPACITY** 
> 初始容量大小 . (默认 16)

* **MAXIMUM_CAPACITY**    
> 最大容量 . (默认 1<<30)

* **DEFAULT_LOAD_FACTOR**
> 扩容阀值 .  (默认 0.75f)

* **TREEIFY_THRESHOLD**
> 一个桶的树化阈值 . (默认 8)  
 当桶中元素个数超过这个值时,需要使用红黑树节点替换链表节点

* **UNTREEIFY_THRESHOLD**
> 一个树的链表还原阈值 . （默认 6 )  
当扩容时，桶中元素个数小于这个值,就会把树形的桶元素 还原（切分）为链表结构

* **MIN_TREEIFY_CAPACITY**
> 哈希表的最小树形化容量  
当哈希表中的容量大于这个值时，表中的桶才能进行树形化 否则桶内元素太多时会扩容，而不是树形化 为了避免进行扩容、树形化选择的冲突，这个值不能小于 4 * TREEIFY_THRESHOLD

## 二 、关键方法解读
* put 方法 (默认调用 putVal)
```
    /** java
     * Implements Map.put and related methods
     * @param hash hash for key
     * @param key the key
     * @param value the value to put
     * @param onlyIfAbsent if true, don't change existing value
     * @param evict if false, the table is in creation mode.
     * @return previous value, or null if none
     */
    final V putVal(int hash, K key, V value, boolean onlyIfAbsent,
                   boolean evict) {
        Node<K,V>[] tab; Node<K,V> p; int n, i;
        // 如果table，初始化
        if ((tab = table) == null || (n = tab.length) == 0)  
            n = (tab = resize()).length;
        // 如果当前桶为空
        if ((p = tab[i = (n - 1) & hash]) == null)  
            tab[i] = newNode(hash, key, value, null);
        else { 
            Node<K,V> e; K k;
            // 与当前桶存入key一致，直接覆盖value
            if (p.hash == hash &&
                ((k = p.key) == key || (key != null && key.equals(k))))  
                e = p;
             // 桶内容数据是树状结构
            else if (p instanceof TreeNode)            
                e = ((TreeNode<K,V>)p).putTreeVal(this, tab, hash, key, value);
            else {
                for (int binCount = 0; ; ++binCount) {
                    if ((e = p.next) == null) {
                        p.next = newNode(hash, key, value, null);
                        if (binCount >= TREEIFY_THRESHOLD - 1) // -1 for 1st
                             // 转树状结构
                            treeifyBin(tab, hash);  
                        break;
                    }
                    if (e.hash == hash &&
                        ((k = e.key) == key || (key != null && key.equals(k))))
                        break;
                    p = e;
                }
            }
            if (e != null) { // existing mapping for key
                V oldValue = e.value;
                if (!onlyIfAbsent || oldValue == null)
                    e.value = value;
                afterNodeAccess(e);
                return oldValue;
            }
        }
        ++modCount;
        if (++size > threshold)
            resize();
        afterNodeInsertion(evict);
        return null;
    }
```
![image](http://tech.meituan.com/img/java-hashmap/hashMap%20put%E6%96%B9%E6%B3%95%E6%89%A7%E8%A1%8C%E6%B5%81%E7%A8%8B%E5%9B%BE.png)
