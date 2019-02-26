学习历程
Day Day Up


####docker 开放远程Docker远程访问端口
```
# vim /lib/systemd/system/docker.service
ExecStart=/usr/bin/dockerd -H tcp://0.0.0.0:2377 -H unix:///var/run/docker.sock
#sudo service docker restart
```

