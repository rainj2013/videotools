package top.rainj2013.service;

import com.google.common.collect.Sets;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.exec.PumpStreamHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import top.rainj2013.bean.form.DownloadForm;

import javax.validation.constraints.NotNull;
import java.io.ByteArrayOutputStream;
import java.util.Set;
import java.util.concurrent.*;

/**
 * Author:  rainj2013
 * Email:  yangyujian25@gmail.com
 * Date:  17-07-16
 */
@Service
public class DownloadService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DownloadService.class);

    @Value("${downloadPath}")
    private String downloadPath;
    private final static String MAGNET_LINK_PREFIX = "magnet:?xt";
    private final static String YOU_GET = "you-get";
    private final static String T_GET = "tget";
    private final static Integer TIMEOUT = 60*30*1000;
    private final static int INIT_CAPACITY = 5;

    private static final int corePoolSize = 1;
    private static final int maxPoolSize = 1;
    private static final int keepAliveTime = 3;

    private ThreadPoolExecutor executor = new ThreadPoolExecutor(corePoolSize, maxPoolSize, keepAliveTime,
            TimeUnit.MILLISECONDS, new PriorityBlockingQueue<>(INIT_CAPACITY));

    private static final Set<String> LINKS = Sets.newConcurrentHashSet();

    public boolean addDownloadTask(DownloadForm form) {
        if (LINKS.contains(form.getUrl())) {
            return false;
        }
        LINKS.add(form.getUrl());
        executor.execute(new DownloadTask(form));
        return true;
    }

    private void downloadByYouGet(String url){
        CommandLine cmdLine = CommandLine.parse(YOU_GET);
        cmdLine.addArgument(url);
        cmdLine.addArgument("-o");
        cmdLine.addArgument(downloadPath);
        executeCommand(cmdLine);
    }

    private void downloadByTget(String url) {
        CommandLine cmdLine = CommandLine.parse("cd");
        cmdLine.addArgument(downloadPath);
        cmdLine.addArgument(T_GET);
        cmdLine.addArgument(url);
        executeCommand(cmdLine);
    }

    private void executeCommand(CommandLine cmdLine) {
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
            LOGGER.info("method=download, download video succeed, command: {}, output: {}", cmdLine.getExecutable(), outputStream.toString() + errorStream.toString());
        } catch (Exception e) {
            LOGGER.error("method=download, download video failed, command: {}, output: {}", cmdLine.getExecutable(), outputStream.toString() + errorStream.toString(), e);
        }
    }

    class DownloadTask implements Runnable, Comparable<DownloadTask> {
        private String url;
        private Integer priority;

        DownloadTask(DownloadForm form) {
            this.url = form.getUrl();
            this.priority = form.getPriority();
        }

        @Override
        public int compareTo(DownloadTask o) {
            return this.priority - o.priority;
        }

        @Override
        public void run() {
            if (url.startsWith(MAGNET_LINK_PREFIX)) {
                downloadByTget(url);
            } else {
                downloadByYouGet(url);
            }
        }
    }
}
