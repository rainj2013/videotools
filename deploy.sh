git pull
export SALT=your_salt
export VIDEO_TOOLS_PASSWORD=your_password
ps -ef|grep videotools|grep -v grep|cut -c 9-15|xargs kill -9
gradle build
java -jar ./build/libs/videotools-1.0.jar