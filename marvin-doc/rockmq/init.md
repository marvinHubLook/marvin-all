#### nameServer 启动
```sh
#!/bin/bash
 
start() {
      echo -n $"Starting mqNameServer : "
      nohup sh bin/mqnamesrv 2>&1 >> logs/nameserver.log &
  }
stop() {
     echo -n $"Stoping mqNameServer :"
     sh bin/mqshutdown namesrv
}
 
case "$1" in  
    start)
        $1  
        ;;  
    stop)
        $1  
        ;;  
    *)  
        echo $"Usage: $0 {start|stop}"
        exit 2
esac;  
```

#### broker 启动/停止
```sh
#!/bin/bash
          
start() {
      echo -n $"Starting Broker : "
      nohup sh bin/mqbroker -n localhost:9876  autoCreateTopicEnable=true  2>&1 >> logs/broker.log &                                                                                                                            
  }       
stop() {
     echo -n $"Stoping Broker :"
     sh bin/mqshutdown broker    
}         
          
case "$1" in  
    start)
        $1
        ;;
    stop)
        $1
        ;;
    *)  
        echo $"Usage: $0 {start|stop}"
        exit 2
esac;

```

#### 查看集群状态
>  bin/mqadmin clusterList -n localhost:9876

#### 新建topic
> bin/mqadmin updateTopic -b localhost:10911 -n localhost:9876 -t TopicTest

#### 查看topic
> bin/mqadmin topicList -n localhost:9876