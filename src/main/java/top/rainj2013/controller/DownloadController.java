package top.rainj2013.controller;

import com.google.common.collect.Maps;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import top.rainj2013.aop.LoginCheck;
import top.rainj2013.bean.form.DownloadForm;
import top.rainj2013.service.DownloadService;

import java.util.Map;

/**
 * Author:  rainj2013
 * Email:  yangyujian25@gmail.com
 * Date:  17-07-15
 */
@Controller
@RequestMapping("/videos")
public class DownloadController {

    @Autowired
    private DownloadService downloadService;

    //swagger api config start
    @ApiOperation(value = "下载视频", notes = "调用you-get/tget工具下载视频，支持多个主流视频网站和磁性链接", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "form", value = "下载任务", required = true, dataType = "DownloadForm"),
            @ApiImplicitParam(name = "token", value = "令牌", required = true, dataType = "String", paramType = "query")})
    //swagger api config end
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    @LoginCheck
    public Map<String, Object> download(@RequestBody DownloadForm form, String token) {
        boolean status = downloadService.addDownloadTask(form);
        Map<String, Object> result = Maps.newHashMap();
        if (status) {
            result.put("code", 1);
            result.put("msg", "Add a download task to the queue successfully.");
        } else {
            result.put("code", 0);
            result.put("msg", "The download task is already exist.");
        }
        return result;
    }
}
