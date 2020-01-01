package entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 页面信息实体类
 * @author rollbear
 * @date 2019.12.15
 */
public class Page {
    //页面内容(html文本)
    private String content;
    //页面url
    private String url;
    //页面标题
    private String title;
    //词条名
    private String entryName;
    //子集信息（子页面url集合）
    private List<String> urlList = new ArrayList<>();
    //词条解释
    private String details;

    //-------------------------------------
    //getter and setter
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEntryName() {
        return entryName;
    }

    public void setEntryName(String entryName) {
        this.entryName = entryName;
    }

    public List<String> getUrlList() {
        return urlList;
    }

    //向子页面列表urlList中添加url
    public void addSubUrl(String url){
        this.urlList.add(url);
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
