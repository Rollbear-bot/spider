package start;

import entity.Page;
import impl.BaikeProcessService;
import impl.ConsoleStoreService;
import impl.HttpClientDownloadService;
import org.htmlcleaner.XPatherException;
import service.IDownloadService;
import service.IProcessService;
import service.IStoreService;
import java.io.IOException;

/**
 * 百度百科爬取类
 * @author rollbear
 * 2019.12.15
 * @hidden
 * 注意：该类不是本项目的主任务，只是作为耦合度的测试
 * 该项目的主任务是爬取知乎，入口在StartZhihu中
 */
public class StartBaike {
    //百科url地址
    private static final String urlOfBaiduBaike = "https://baike.baidu.com/wikitag/taglist?tagId=76607";

    private IDownloadService downloadService;
    private IProcessService processService;
    private IStoreService storeService;

    //页面下载器
    public Page downloadPage(String url) throws IOException {
        return downloadService.download(url);
    }

    //页面解析器
    public Page process(Page page) throws XPatherException {
        return this.processService.process(page);
    }

    //页面存储器
    public void storePage(Page page){
        this.storeService.store(page);
    }

    /**
     * 百度百科爬取主方法
     * 下载模式设置为httpClient，解析模式设置为BaikeProcess
     * @param args String[]
     * @throws IOException Exception
     * @throws XPatherException Exception
     */
    public static void main(String[] args) throws IOException, XPatherException {
        StartBaike bk = new StartBaike();

        //设置抓取模式，使用httpClient来实现下载器接口
        bk.setDownloadService(new HttpClientDownloadService());
        //设置解析模式，使用BaikeProcess来实现解析接口
        bk.setProcessService(new BaikeProcessService());

        //设置store模式，控制台输出
        bk.setStoreService(new ConsoleStoreService());

        //下载页面
        Page downloadedPage = bk.downloadPage(urlOfBaiduBaike);

        //解析页面
        downloadedPage.setUrl(urlOfBaiduBaike);
        downloadedPage = bk.process(downloadedPage);

        bk.storePage(downloadedPage);
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