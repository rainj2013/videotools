package top.rainj2013.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import top.rainj2013.Application;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class LoginCheckServiceTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginCheckServiceTest.class);

    @Autowired
    private LoginCheckService loginCheckService;

    private String password = "password";

    @Test
    public void check() throws Exception {
        String token = loginCheckService.getToken(password);
        LOGGER.info("check token result : {}",loginCheckService.check(token));
    }

    @Test
    public void getToken() throws Exception {
        LOGGER.info("get a token : {}", loginCheckService.getToken(password));
    }

}