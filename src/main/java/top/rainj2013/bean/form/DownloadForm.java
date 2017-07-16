package top.rainj2013.bean.form;

/**
 * Author:  rainj2013
 * Email:  yangyujian25@gmail.com
 * Date:  17-07-15
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
