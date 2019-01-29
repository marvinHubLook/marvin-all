#### jar包运行
>nohup java -Xloggc:${logging_file_location}gc.log -XX:+PrintGCDetails -jar app.jar --spring.profiles.active=${environment} --logging.file.location=${logging_file_location} --domain=com.xx.xxx.xxxx > /dev/null 2>&1 &
* -Xloggc: 指定程序运行过程中产生的 GC 日志输出到 gc.log 文件中。
* -XX:+PrintGCDetails 指定 输出详细的GC日志。
* spring.profiles.active=${environment} 可根据 environment变量来选择是生产环境还是测试环境。有时生产环境中使用的数据源（比如 Mysql）与测试环境不一样，这样就很方便。
* --logging.file.location指定程序输出的日志
* --domain 这个参数主要用来对程序进行标识。比如，使用 ps aux | grep com.xx.xxx.xxxx 就能方便地找到程序的进程号了。


#### 查看线程数
> cat /proc/{pid}/status | grep Threads



