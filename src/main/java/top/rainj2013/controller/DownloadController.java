package top.rainj2013.controller;

import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import top.rainj2013.bean.DownloadForm;
import top.rainj2013.service.DownloadService;

import java.util.Map;

/**
 * Created by yangyujian on 2017/7/15 20:11.
 */
@Controller
public class DownloadController {

    @Autowired
    private DownloadService downloadService;

    @RequestMapping(value = "/download", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> download(@RequestBody DownloadForm form) {
        downloadService.addDownloadTask(form);
        Map<String, Object> result = Maps.newHashMap();
        result.put("code", 1);
        result.put("msg", "add the download task to the queue");
        return result;
    }
}
