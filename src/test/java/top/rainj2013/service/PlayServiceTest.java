package top.rainj2013.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import top.rainj2013.Application;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class PlayServiceTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(PlayServiceTest.class);

    @Autowired
    private PlayService playService;

    @Before
    public void before() throws Exception {
        playService.getVideos();
    }

    @Test
    public void getVideo() throws Exception {
        LOGGER.info("video info : {}", playService.getVideo(0));
    }

    @Test
    public void getVideos() throws Exception {
        LOGGER.info("videos list : {}", playService.getVideos());
    }

}