package top.rainj2013.controller;

import com.google.common.collect.Maps;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import top.rainj2013.aop.LoginCheck;
import top.rainj2013.utils.Constants;
import top.rainj2013.bean.form.DownloadForm;
import top.rainj2013.service.DownloadService;

import java.util.Map;

/**
 * Author:  rainj2013
 * Email:  yangyujian25@gmail.com
 * Date:  17-07-15
 */
@Api(tags = "/videos", description = "查看视频列表|播放视频|提交下载任务")
@Controller
@RequestMapping("/videos")
public class DownloadController {

    private final DownloadService downloadService;

    @Autowired
    public DownloadController(DownloadService downloadService) {
        this.downloadService = downloadService;
    }

    //swagger api config start
    @ApiOperation(value = "下载视频", notes = "调用you-get/tget工具下载视频，支持多个主流视频网站和磁性链接", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "form", value = "下载任务", required = true, dataType = "DownloadForm",
                    paramType = "body")})
    //swagger api config end
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    @LoginCheck
    public Map<String, Object> download(@RequestBody DownloadForm form) {
        boolean status = downloadService.addDownloadTask(form);
        Map<String, Object> result = Maps.newHashMap();
        if (status) {
            result.put(Constants.CODE, 1);
            result.put(Constants.MSG, "Add a download task to the queue successfully.");
        } else {
            result.put(Constants.CODE, 0);
            result.put(Constants.MSG, "The download task is already exist.");
        }
        return result;
    }
}
