package entity;

import java.util.ArrayList;
import java.util.List;

/**
 * ҳ����Ϣʵ����
 * @author rollbear
 * @date 2019.12.15
 */
public class Page {
    //ҳ������(html�ı�)
    private String content;
    //ҳ��url
    private String url;
    //ҳ�����
    private String title;
    //������
    private String entryName;
    //�Ӽ���Ϣ����ҳ��url���ϣ�
    private List<String> urlList = new ArrayList<>();
    //��������
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

    //����ҳ���б�urlList�����url
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
