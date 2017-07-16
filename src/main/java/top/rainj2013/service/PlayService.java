package top.rainj2013.service;

import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

/**
 * Author:  rainj2013
 * Email:  yangyujian25@gmail.com
 * Date:  17-07-16
 */

@Service
public class PlayService {

    @Value("${downloadPath}")
    private String downloadPath;

    public List<String> getFileList() {
        List<String> list = Lists.newArrayList();
        File downloadFolder = new File(downloadPath);
        File[] files = downloadFolder.listFiles();
        if (null == files) {
            return list;
        }
        for (File file : files) {
            list.add(file.getName());
        }
        return list;
    }
}
