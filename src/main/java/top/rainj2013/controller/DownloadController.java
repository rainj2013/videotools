package top.rainj2013.controller;

import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import top.rainj2013.bean.form.DownloadForm;
import top.rainj2013.service.DownloadService;

import java.util.Map;

/**
 * Author:  rainj2013
 * Email:  yangyujian25@gmail.com
 * Date:  17-07-15
 */
@Controller
@RequestMapping("/video")
public class DownloadController {

    @Autowired
    private DownloadService downloadService;

    @RequestMapping(value = "/download", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> download(@RequestBody DownloadForm form) {
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
