
* show processlist
> 查看用户连接状态   
   > 客户端如果太长时间没动静，连接器就会自动将它断开。这个时间是由参数 wait_timeout 控制的，默认值是 8 小时

* show variables like 'transaction_isolation'  查看事务隔离级别
   * 读未提交（read uncommitted）、读提交（read committed）、可重复读（repeatable read）和串行化（serializable ）      
        * **读未提交** : 一个事务还没提交时，它做的变更就能被别的事务看到。  
        * **读提交**:一个事务提交之后，它做的变更才会被其他事务看到。  
        * **可重复读**:一个事务执行过程中看到的数据，总是跟这个事务在启动时看到的数据是一致的。当然在可重复读隔离级别下，未提交变更对其他事务也是不可见的。  
        * **串行化**: 顾名思义是对于同一行记录，“写”会加“写锁”，“读”会加“读锁”。当出现读写锁冲突的时候，后访问的事务必须等前一个事务执行完成，才能继续执行。
   * 读提交 与 可重复读  区别
      * 在可重复读隔离级别下，只需要在事务开始的时候创建一致性视图，之后事务里的其他查询都共用这个一致性视图；
      * 在读提交隔离级别下，每一个语句执行前都会重新算出一个新的视图。
   
   *  概念
      1. 一致性识图，保证了当前事务从启动到提交期间，读取到的数据是一致的（包括当前事务的修改）。
      2. 当前读，保证了当前事务修改数据时，不会丢失其他事务已经提交的修改。
      3. 两阶段锁协议，保证了当前事务修改数据时，不会丢失其他事务未提交的修改。
      4. RR是通过事务启动时创建一致性识图来实现，RC是语句执行时创建一致性识图来实现。
        
* explain 

    * id: SELECT 查询的标识符. 每个 SELECT 都会自动分配一个唯一的标识符.
    * select_type: SELECT 查询的类型.
        * SIMPLE, 表示此查询不包含 UNION 查询或子查询
        * PRIMARY, 表示此查询是最外层的查询
        * UNION, 表示此查询是 UNION 的第二或随后的查询
        * DEPENDENT UNION, UNION 中的第二个或后面的查询语句, 取决于外面的查询
        * UNION RESULT, UNION 的结果
        * SUBQUERY, 子查询中的第一个 SELECT
        * DEPENDENT SUBQUERY: 子查询中的第一个 SELECT, 取决于外面的查询. 即子查询依赖于外层查询的结果.
        * DERIVED：被驱动的select子查询（子查询位于FROM子句）
        * MATERIALIZED：被物化的子查询
    * table: 查询的是哪个表
    * partitions: 匹配的分区
    * type: join 类型
        * system: 表中只有一条数据. 这个类型是特殊的 const 类型.
        * const: 针对主键或唯一索引的等值查询扫描, 最多只返回一行数据. const 查询速度非常快, 因为它仅仅读取一次即可.
        * eq_ref: 此类型通常出现在多表的 join 查询, 表示对于前表的每一个结果, 都只能匹配到后表的一行结果. 并且查询的比较操作通常是 =, 查询效率较高
        * ref: 此类型通常出现在多表的 join 查询, 针对于非唯一或非主键索引, 或者是使用了 最左前缀 规则索引的查询. 
        * range: 表示使用索引范围查询, 通过索引字段范围获取表中部分数据记录. 这个类型通常出现在 =, <>, >, >=, <, <=, IS NULL, <=>, BETWEEN, IN() 操作中.
        * index: 表示全索引扫描(full index scan), 和 ALL 类型类似, 只不过 ALL 类型是全表扫描, 而 index 类型则仅仅扫描所有的索引, 而不扫描数据.  
        不同的 type 类型的性能关系如下:  
        ALL < index < range ~ index_merge < ref < eq_ref < const < system
    例如下面的这个查询, 它使用了主键索引, 因此 type 就是 const 类型的.
    * possible_keys: 此次查询中可能选用的索引
    * key: 此次查询中确切使用到的索引.
    * key_len: 查询优化器使用了索引的字节数.这个字段可以评估组合索引是否完全被使用, 或只有最左部分字段被使用到.
        * 字符串
            * char(n): n 字节长度
            * varchar(n): 如果是 utf8 编码, 则是 3 n + 2字节; 如果是 utf8mb4 编码, 则是 4 n + 2 字节.
        * 数值类型:
            * TINYINT: 1字节
            * SMALLINT: 2字节
            * MEDIUMINT: 3字节
            * INT: 4字节
            * BIGINT: 8字节
        * 时间类型
            * DATE: 3字节
            * TIMESTAMP: 4字节
            * DATETIME: 8字节  
        字段属性: NULL 属性 占用一个字节. 如果一个字段是 NOT NULL 的, 则没有此属性.
    * ref: 哪个字段或常数与 key 一起被使用
    * rows: 显示此查询一共扫描了多少行. 这个是一个估计值.
    * filtered: 表示此查询条件所过滤的数据的百分比
    * extra: 额外的信息
        * Using filesort
          当 Extra 中有 Using filesort 时, 表示 MySQL 需额外的排序操作, 不能通过索引顺序达到排序效果. 一般有 Using filesort, 都建议优化去掉, 因为这样的查询 CPU 资源消耗大.
        * Using index
          "覆盖索引扫描", 表示查询在索引树中就可查找所需数据, 不用扫描表数据文件, 往往说明性能不错
        * Using temporary
          查询有使用临时表, 一般出现于排序, 分组和多表 join 的情况, 查询效率不高, 建议优化.

[explain字段解释](https://segmentfault.com/a/1190000008131735)   

* mysqldump -uroot -proot --all-databases >/tmp/all.sql  --该命令会导出包括系统数据库在内的所有数据库   
* mysqldump -uroot -p --databases db1 db2 > D:/tmp/db.sql    --导出db1、db2两个数据库的所有数据
* mysqldump -uroot -proot --databases db1 --tables a1 a2  >/tmp/db1.sql  -- 导a1,a2表
* mysqldump -uroot -proot --databases db1 --tables a1 --where='id=1'  >/tmp/a1.sql --条件导出
    > 备份的时候,如果是innodb引擎,则最好加上参数 --single-transaction。 下面两个参数选择  
    --master-data=2表示在dump过程中记录主库的binlog和pos点，并在dump文件中注释掉这一行；  
    --master-data=1表示在dump过程中记录主库的binlog和pos点，并在dump文件中不注释掉这一行，即恢复时会执行；  
    --dump-slave=2表示在dump过程中，在从库dump，mysqldump进程也要在从库执行，记录当时主库的binlog和pos点，并在dump文件中注释掉这一行；  
    --dump-slave=1表示在dump过程中，在从库dump，mysqldump进程也要在从库执行，记录当时主库的binlog和pos点，并在dump文件中不注释掉这一行；  

[mysqldump数据库导出](https://www.cnblogs.com/chenmh/p/5300370.html)


* 查询扫描基数
> show index from $table   
> analyze table $table  # 重新统计索引信息
