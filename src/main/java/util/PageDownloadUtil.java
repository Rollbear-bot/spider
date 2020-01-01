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
 ҳ�����ع�����
 @author rollbear
 2019.12.15
 */
public class PageDownloadUtil extends Application {
    //���õ�������
    public static void main(String[] args){
        launch(args);
    }

    /**
     * html�ı���ȡ����
     * ͨ��url��ȡ�����ҳ������
     * @param url ҳ��url
     * @return url��html�ı����ַ�����ʽ��
     */
    public static String getPageContent(String url) {
        HttpClientBuilder builder = HttpClients.custom();
        CloseableHttpClient client = builder.build();
        String content = null; //ʹ��content�����淵��ֵ

        //����url
        HttpGet request = new HttpGet(url);
        try {
            CloseableHttpResponse response = client.execute(request);
            HttpEntity entity = response.getEntity();

            //ʹ��content�����淵��ֵ
            content = EntityUtils.toString(entity);

        } catch (IOException e){
            e.printStackTrace();
        }
        return content;
    }

    //���Է���
    @Override
    public void start(Stage primaryStage) throws IOException {
        String url = "https://baike.baidu.com/item/httpclient/5766483?fr=aladdin";
        HttpClientDownloadService down = new HttpClientDownloadService();
        System.out.println(down.download(url).getContent());
    }
}
