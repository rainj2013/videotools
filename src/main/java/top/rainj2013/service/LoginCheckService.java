package top.rainj2013.service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class LoginCheckService {
    private static final long DURATION = 2;
    LoadingCache<String, String> cache0;

    {
        cache0 = CacheBuilder.newBuilder()
                .expireAfterWrite(DURATION, TimeUnit.HOURS)
                .maximumSize(1)
                .build(
                        new CacheLoader<String, String>() {
                            public String load(String key) throws Exception {
                                System.out.println("load from db");
                                return "data";
                            }
                        });
    }

    public boolean check(String token){
        return false;
    }
}
