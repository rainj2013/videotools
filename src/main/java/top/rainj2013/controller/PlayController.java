package top.rainj2013.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import top.rainj2013.service.PlayService;

import java.util.List;

/**
 * Author:  rainj2013
 * Email:  yangyujian25@gmail.com
 * Date:  17-07-16
 */
@Controller
@RequestMapping("/video")
public class PlayController {

    @Autowired
    private PlayService playService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public List<String> videos() {
        return playService.getFileList();
    }
}
