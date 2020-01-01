package start;

import entity.Page;
import impl.HttpClientDownloadService;
import impl.MysqlStoreService;
import impl.ZhihuProcessService;
import org.htmlcleaner.XPatherException;
import service.IDownloadService;
import service.IProcessService;
import service.IStoreService;
import util.MysqlUtil;
import java.io.IOException;

/**
 * ֪����ȡ��
 * @author rollbear
 * 2019.12.16
 */
public class StartZhihu {
    //��������ģ��ʵ�ֵ�˽�г�Ա����
    private IDownloadService downloadService;
    private IProcessService processService;
    private IStoreService storeService;

    //ҳ��������
    public Page downloadPage(String url) throws IOException {
        return downloadService.download(url);
    }

    //ҳ�������
    public Page process(Page page) throws XPatherException {
        return this.processService.process(page);
    }

    //ҳ��洢��
    public void store(Page page){
        this.storeService.store(page);
    }


    /**
     * ֪��������ҳ��ȡ������
     * ���������ļ�"Zhihu"
     * @param args String[]
     * @throws IOException ��������쳣
     * @throws XPatherException xpath�����쳣
     */
    public static void main(String[] args) throws IOException, XPatherException {
        StartZhihu zhihu = new StartZhihu();
        //����ץȡģʽ��ʹ��httpClient��ʵ���������ӿ�
        zhihu.setDownloadService(new HttpClientDownloadService());
        //���ý���ģʽ��ʹ��ZhihuProcess��ʵ�ֽ����ӿ�
        zhihu.setProcessService(new ZhihuProcessService());
        //����storeģʽ��ʹ��MySQL��ʵ�ִ洢����
        zhihu.setStoreService(new MysqlStoreService());

        String urlOfZhihu;
        int key = 0; //��url�����л�ȡurl������

        //�������ݿ⣨��MysqlUtil�Ĺ��캯���У�
        MysqlUtil mysqlUtil = new MysqlUtil();

        /*
        ˵����һ��ѭ�����ڰ����ĸ��׶Σ���ȡurl�׶Ρ�����ҳ��׶Ρ�����ҳ��׶κʹ洢ҳ��׶�

        ��ȡurl�׶Σ���������ݿ��urlSet���и���key��ֵ��ȡurl��key�ĳ�ʼֵΪ�㣬��һ��ѭ������ʱ�Լ�

        ������ҳ��׶Σ����������һ���л�ȡ��url����html��ʽ���ַ����������Page������

        �ڽ���ҳ��׶Σ���������趨�Ľ�����ʽ��Page������н������������ӽ׶Σ�
        �ڵ�һ�ӽ׶Σ�����ҳ���е���Ч��Ϣ��ȡ������Page�������Ӧ�ֶ���
        �ڵڶ��ӽ׶Σ�����ҳ������ȡ���������ӣ������û��ĸ�����ҳ��ڣ���������ʶ���urlSet���У�
        ��ÿ��url���ǰ��������жϸ�url�Ƿ����ڶ����У����Ѵ��ڣ��򲻼���

        �ڴ���ҳ��׶Σ�����Page�����е���Ч��Ϣ�������ݿ⣬
        �ڴ���ǰ���������жϸ��û��ļ�¼�Ƿ����ڱ��У������򲻴���

        ���Ͻ׶ν����󣬳��������һ��ѭ�����ڲ�ȡ��һ��url
         */

        //ѭ��ץȡҳ��
        while (true){
            //��url����urlSet�л�ȡһ��url
            urlOfZhihu = MysqlUtil.getUrl(key, "urlset");
            //��ȥ��λΪ"https://www.zhihu.com/people/"�����
            if(urlOfZhihu.equals("https://www.zhihu.com/people/"))continue;
            //����ҳ��
            Page downloadedPage = zhihu.downloadPage(urlOfZhihu);
            //����ҳ��
            downloadedPage.setUrl(urlOfZhihu);
            downloadedPage = zhihu.process(downloadedPage);
            //�洢ҳ��
            zhihu.store(downloadedPage);
            key++;
        }
    }

    //setter and getter
    //-----------------------------------------
    public IDownloadService getDownloadService() {
        return downloadService;
    }

    public void setDownloadService(IDownloadService downloadService) {
        this.downloadService = downloadService;
    }

    public IProcessService getProcessService() {
        return processService;
    }

    public void setProcessService(IProcessService processService) {
        this.processService = processService;
    }

    public IStoreService getStoreService() {
        return storeService;
    }

    public void setStoreService(IStoreService storeService) {
        this.storeService = storeService;
    }
}