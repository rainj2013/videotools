package top.rainj2013.utils;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtils {

    public static String getValue(String name) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        Cookie[] cookies = request.getCookies();
        String value = null;
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(name)) {
                value = cookie.getValue();
                break;
            }
        }
        if (value == null) {
            value = Constants.INVALID_TOKEN;
        }
        return value;
    }

    public static void addCookie(String name, String value) {
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getResponse();
        Cookie cookie = new Cookie(name, value);
        response.addCookie(cookie);
    }
}
