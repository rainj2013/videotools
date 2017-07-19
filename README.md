# videotools
一个用于下载/处理/播放视频的程序
本程序除了播放页，没有交互页面，请自觉使用postman或其他HttpClient工具进行交互（😅其实就是作者懒）

nginx.conf
```
server {
    listen       80;
    server_name  rainj2013.top;

    location ~ \.(css|js)$ {
        proxy_pass http://127.0.0.1:8888;
    }        

    location ^~ /videos/ {
        proxy_pass http://127.0.0.1:8888;
    }
	
    location ~ \.(mkv|flv|mp4)$ {
        root  /usr/local/download;
    }

}

```
