package top.rainj2013.controller;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import top.rainj2013.aop.LoginCheck;
import top.rainj2013.utils.Constants;
import top.rainj2013.service.PlayService;

import java.util.Map;

/**
 * Author:  rainj2013
 * Email:  yangyujian25@gmail.com
 * Date:  17-07-16
 */
@Api(tags = "/videos")
@Controller
@RequestMapping("/videos")
public class PlayController {

    @Autowired
    private PlayService playService;

    @ApiOperation(value = "视频列表", notes = "已经下载的视频列表", httpMethod = "GET")
    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    @LoginCheck
    public Map<String, Object> videos() {
        Map<String, Object> result = Maps.newHashMap();
        result.put(Constants.CODE, 1);
        result.put(Constants.DATA, playService.getVideos());
        return result;
    }

    @ApiOperation(value = "视频播放页", notes = "播放视频页面", httpMethod = "GET", code = 302)
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @LoginCheck(failResult = "player")
    @ApiImplicitParams({@ApiImplicitParam(name = "id",value = "视频id", required = true, paramType = "path",
            defaultValue = "video id")})
    public String play(@PathVariable Integer id, Model model) {
        String video = playService.getVideo(id);
        if (!Strings.isNullOrEmpty(video)) {
            model.addAttribute( Constants.VIDEO, video);
            model.addAttribute(Constants.TYPE, video.substring(video.lastIndexOf(Constants.POINT)+1));
        }
        return "player";
    }
}
