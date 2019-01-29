#### 机器的物理处理器核数
> cat /proc/cpuinfo | grep "physical id" | sort | uniq | wc -l

#### 机器的总内存大小
> cat /proc/meminfo | grep MemTotal
