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
 * 知乎爬取类
 * @author rollbear
 * 2019.12.16
 */
public class StartZhihu {
    //用于设置模块实现的私有成员变量
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
    public void store(Page page){
        this.storeService.store(page);
    }


    /**
     * 知乎个人主页爬取主方法
     * 加载配置文件"Zhihu"
     * @param args String[]
     * @throws IOException 输入输出异常
     * @throws XPatherException xpath解析异常
     */
    public static void main(String[] args) throws IOException, XPatherException {
        StartZhihu zhihu = new StartZhihu();
        //设置抓取模式，使用httpClient来实现下载器接口
        zhihu.setDownloadService(new HttpClientDownloadService());
        //设置解析模式，使用ZhihuProcess来实现解析接口
        zhihu.setProcessService(new ZhihuProcessService());
        //设置store模式，使用MySQL来实现存储服务
        zhihu.setStoreService(new MysqlStoreService());

        String urlOfZhihu;
        int key = 0; //从url队列中获取url的索引

        //连接数据库（在MysqlUtil的构造函数中）
        MysqlUtil mysqlUtil = new MysqlUtil();

        /*
        说明：一个循环周期包括四个阶段，即取url阶段、下载页面阶段、解析页面阶段和存储页面阶段

        在取url阶段，程序从数据库的urlSet表中根据key的值获取url，key的初始值为零，在一次循环结束时自加

        在下载页面阶段，程序根据上一步中获取的url下载html格式的字符串，存放在Page对象中

        在解析页面阶段，程序根据设定的解析方式对Page对象进行解析，有两个子阶段，
        在第一子阶段，将本页面中的有效信息提取并放入Page对象的相应字段中
        在第二子阶段，将本页面中提取到的子链接（其他用户的个人主页入口）放入待访问队列urlSet表中，
        在每次url入队前，程序会判断该url是否已在队列中，若已存在，则不加入

        在储存页面阶段，程序将Page对象中的有效信息存入数据库，
        在存入前，程序先判断该用户的记录是否已在表中，若有则不存入

        以上阶段结束后，程序进入下一个循环周期并取下一条url
         */

        //循环抓取页面
        while (true){
            //从url队列urlSet中获取一条url
            urlOfZhihu = MysqlUtil.getUrl(key, "urlset");
            //滤去地位为"https://www.zhihu.com/people/"的入口
            if(urlOfZhihu.equals("https://www.zhihu.com/people/"))continue;
            //下载页面
            Page downloadedPage = zhihu.downloadPage(urlOfZhihu);
            //解析页面
            downloadedPage.setUrl(urlOfZhihu);
            downloadedPage = zhihu.process(downloadedPage);
            //存储页面
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