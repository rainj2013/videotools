package top.rainj2013.controller;

import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import top.rainj2013.bean.Constants;
import top.rainj2013.service.LoginCheckService;

import java.util.Map;

@Controller
@RequestMapping("videos")
public class SessionController {
    @Autowired
    private LoginCheckService loginCheckService;

    @RequestMapping(value = "/session", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> session(String password) {
        Map<String, Object> result = Maps.newHashMapWithExpectedSize(2);
        String token = loginCheckService.getToken(password);
        if (token.equals(Constants.INVALID_TOKEN)) {
            result.put("code", 0);
            result.put("msg", "Get token fail, please check your password.");
            return result;
        }
        result.put("code", 1);
        result.put("token", token);
        return result;
    }
}