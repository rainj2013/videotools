git pull
ps -ef|grep videotools|grep -v grep|cut -c 9-15|xargs kill -9
gradle build
java -jar ./build/libs/videotools-1.0.jar