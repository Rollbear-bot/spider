package util;

import impl.HttpClientDownloadService;
import javafx.application.Application;
import javafx.stage.Stage;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import java.io.IOException;

/**
 页面下载工具类
 @author rollbear
 2019.12.15
 */
public class PageDownloadUtil extends Application {
    //无用的主函数
    public static void main(String[] args){
        launch(args);
    }

    /**
     * html文本获取方法
     * 通过url获取所需的页面内容
     * @param url 页面url
     * @return url的html文本（字符串格式）
     */
    public static String getPageContent(String url) {
        HttpClientBuilder builder = HttpClients.custom();
        CloseableHttpClient client = builder.build();
        String content = null; //使用content来储存返回值

        //传入url
        HttpGet request = new HttpGet(url);
        try {
            CloseableHttpResponse response = client.execute(request);
            HttpEntity entity = response.getEntity();

            //使用content来储存返回值
            content = EntityUtils.toString(entity);

        } catch (IOException e){
            e.printStackTrace();
        }
        return content;
    }

    //测试方法
    @Override
    public void start(Stage primaryStage) throws IOException {
        String url = "https://baike.baidu.com/item/httpclient/5766483?fr=aladdin";
        HttpClientDownloadService down = new HttpClientDownloadService();
        System.out.println(down.download(url).getContent());
    }
}
