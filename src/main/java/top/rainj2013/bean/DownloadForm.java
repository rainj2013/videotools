package top.rainj2013.bean;

/**
 * Created by yangyujian on 2017/7/15 20:37.
 */
public class DownloadForm {

    private String url;
    private Integer priority;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        return "DownloadTask{" +
                "url='" + url + '\'' +
                ", priority=" + priority +
                '}';
    }
}
