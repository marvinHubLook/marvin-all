#### 反向代理
```text
server {
        listen       80;
        server_name  test.nginx.com;
        
        location / {
            proxy_pass   http://webservers;
        }

        location /buy {
            proxy_pass   http://172.18.144.23:5789/;
        }                

        error_page   500 502 503 504  /50x.html;
        location = /50x.html {
            root   html;
        }

    }
```

#### 负载均衡
```text
upstream webservers {
    server 172.18.144.23:4789 weight=10;
    server 172.18.144.23:5789 weight=10;
}

```