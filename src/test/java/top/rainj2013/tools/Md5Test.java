package top.rainj2013.tools;

import org.joda.time.DateTime;
import org.junit.Test;
import org.springframework.util.DigestUtils;

public class Md5Test {
    @Test
    public void Md5Test() {
        String SALT = System.getProperty("SALT");
        String PASSWORD = System.getProperty("VIDEO_TOOLS_PASSWORD");
        String pwdKey = DateTime.now()+SALT+PASSWORD;
        System.out.println(DigestUtils.md5DigestAsHex(pwdKey.getBytes()).length());
    }
}
