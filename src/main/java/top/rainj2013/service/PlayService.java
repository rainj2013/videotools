package top.rainj2013.service;

import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Map;

/**
 * Author:  rainj2013
 * Email:  yangyujian25@gmail.com
 * Date:  17-07-16
 */

@Service
public class PlayService {

    @Value("${downloadPath}")
    private String downloadPath;
    @Value("${fileSuffix}")
    private String[] fileSuffix;

    private Map<Integer, String> videos = Maps.newConcurrentMap();

    public String getVideo(Integer id) {
        return videos.get(id);
    }

    public Map<Integer, String> getVideos() {
        File downloadFolder = new File(downloadPath);
        File[] files = downloadFolder.listFiles(file -> {
            String filename = file.getName();
            for (String suffix : fileSuffix) {
                if (filename.endsWith(suffix)) {
                    return true;
                }
            }
            return false;
        });

        if (null == files) {
            return videos;
        }
        for (int index = 0; index < files.length; index++) {
            videos.put(index, files[index].getName());
        }
        return videos;
    }
}
