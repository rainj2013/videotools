package top.rainj2013.service;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.exec.PumpStreamHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import top.rainj2013.bean.DownloadForm;

import java.io.ByteArrayOutputStream;
import java.util.concurrent.*;

/**
 * Created by yangyujian on 2017/7/15 19:42.
 */
@Service
public class DownloadService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DownloadService.class);

    private final static String YOU_GET = "you-get";
    private final static Integer TIMEOUT = 60*30*1000;
    private final static int INIT_CAPACITY = 5;
    @Value("${video.downloadPath}")
    private String downloadPath;
    private static final int corePoolSize = 1;
    private static final int maxPoolSize = 1;
    private static final int keepAliveTime = 3;

    private ThreadPoolExecutor executor = new ThreadPoolExecutor(corePoolSize, maxPoolSize, keepAliveTime, TimeUnit.MILLISECONDS,
            new PriorityBlockingQueue<>(INIT_CAPACITY));

    public void addDownloadTask(DownloadForm form) {
        executor.execute(new DownloadTask(form));
    }

    public void download(String url){
        CommandLine cmdLine = CommandLine.parse(YOU_GET);
        cmdLine.addArgument(url);
        cmdLine.addArgument("-o");
        cmdLine.addArgument(downloadPath);
        DefaultExecutor executor = new DefaultExecutor();
        ExecuteWatchdog watchdog = new ExecuteWatchdog(TIMEOUT);
        executor.setWatchdog(watchdog);
        executor.setExitValue(0);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ByteArrayOutputStream errorStream = new ByteArrayOutputStream();
        try {
            PumpStreamHandler pumpStreamHandler = new PumpStreamHandler(outputStream, errorStream);
            executor.setStreamHandler(pumpStreamHandler);
            executor.execute(cmdLine);
            LOGGER.info("method=download, download video: {} succeed", url, outputStream.toString() + errorStream.toString());
        } catch (Exception e) {
            LOGGER.error("method=download, download video: {} failed", url, outputStream.toString() + errorStream.toString(), e);
        }
    }

    class DownloadTask implements Runnable, Comparable<DownloadTask> {
        private String url;
        private Integer priority;

        public DownloadTask(DownloadForm form) {
            this.url = form.getUrl();
            this.priority = form.getPriority();
        }

        @Override
        public int compareTo(DownloadTask o) {
            return this.priority - o.priority;
        }

        @Override
        public void run() {
            download(url);
        }
    }
}
