package top.rainj2013.service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import top.rainj2013.bean.Constants;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Author:  rainj2013
 * Email:  yangyujian25@gmail.com
 * Date:  17-07-19
 */
@Service
public class LoginCheckService {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginCheckService.class);

    private static final String SALT;
    private static final String PASSWORD;
    private static final long DURATION = 2;
    private static LoadingCache<String, String> tokenCache;

    static {
        SALT = System.getenv("SALT");
        PASSWORD = System.getenv("VIDEO_TOOLS_PASSWORD");
        tokenCache = CacheBuilder.newBuilder()
                .expireAfterWrite(DURATION, TimeUnit.HOURS)
                .maximumSize(1)
                .build(new CacheLoader<String, String>() {
                    @Override
                    @ParametersAreNonnullByDefault
                    public String load(String key) throws Exception {
                        if (key.equals(PASSWORD)) {
                            String pwdKey = DateTime.now().getMillis()+SALT+PASSWORD;
                            return DigestUtils.md5DigestAsHex(pwdKey.getBytes());
                        }
                        return Constants.INVALID_TOKEN;
                    }
                });
    }

    public boolean check(String token){
        Map<String, String> tokenMap = tokenCache.asMap();
        return tokenMap.values().contains(token);
    }

    public String getToken(String password) {
        try {
            return tokenCache.get(password);
        } catch (ExecutionException e) {
            LOGGER.error("get token error", e);
            return Constants.INVALID_TOKEN;
        }
    }
}
