package top.rainj2013.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import top.rainj2013.Application;
import top.rainj2013.bean.form.DownloadForm;

import java.util.concurrent.TimeUnit;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class DownloadServiceTest {
    @Autowired
    private DownloadService downloadService;
    @Test
    public void addDownloadTask() throws Exception {
        DownloadForm form = new DownloadForm();
        form.setUrl("https://www.bilibili.com/video/av12960592/");
        form.setPriority(5);
        downloadService.addDownloadTask(form);
        TimeUnit.MINUTES.sleep(5);
    }

}