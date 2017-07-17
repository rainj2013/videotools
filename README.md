# videotools
download and handle video

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
