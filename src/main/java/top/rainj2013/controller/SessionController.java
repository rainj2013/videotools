package top.rainj2013.controller;

import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import top.rainj2013.bean.Constants;
import top.rainj2013.service.LoginCheckService;

import java.util.Map;

@Api(tags = "/videos/session", description = "获取token")
@Controller
@RequestMapping("/videos")
public class SessionController {
    @Autowired
    private LoginCheckService loginCheckService;

    @ApiOperation(value = "获取token", notes = "输入暗号，领取token一枚", httpMethod = "GET")
    @ApiImplicitParams(@ApiImplicitParam(name = "password",value = "密码", required = true, paramType = "query",
            defaultValue = "please input your password"))
    @RequestMapping(value = "/session", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> session(String password) {
        Map<String, Object> result = Maps.newHashMapWithExpectedSize(2);
        String token = loginCheckService.getToken(password);
        if (token.equals(Constants.INVALID_TOKEN)) {
            result.put(Constants.CODE, 0);
            result.put(Constants.MSG, "Get token fail, please check your password.");
            return result;
        }
        result.put(Constants.CODE, 1);
        result.put(Constants.TOKEN, token);
        return result;
    }
}