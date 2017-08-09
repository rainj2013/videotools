# videotools
一个用于下载/处理/播放视频的程序  
本程序**除播放页外没有正常交互界面**  
请访问 http://rainj2013.top/swagger-ui.html#/  
查看各HTTP服务API，亦可直接在该页面进行参数提交/获取资源  
如果需要将本程序部署到您的机器，请注意更换配置中的域名  
下载功能依赖以下程序：  
you-get: https://github.com/soimort/you-get (从视频网站下载视频)  
t-get: https://github.com/jeffjose/tget (从磁性链接下载视频) 

nginx/conf.d/videotools.conf
```
server {
    listen       80;
    server_name  rainj2013.top;
    
    location ~ \.(mkv|flv|mp4)$ {
        root  /usr/local/download;
    }

    location / {
        proxy_pass http://127.0.0.1:8888;
    }
}
```
