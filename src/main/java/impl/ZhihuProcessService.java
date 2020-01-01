package impl;
import entity.Page;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;
import service.IProcessService;
import util.HtmlUtil;
import util.LoadPropertyUtil;
import util.MysqlUtil;
import java.io.IOException;
import java.net.URL;

/**
 * @author rollbear
 * 2019.12.16
 * 知乎解析实现类
 */
public class ZhihuProcessService implements IProcessService {
    //将清洗完成的页面数据作为类成员，方便各个成员方法的调用
    private TagNode rootNode;

    /**
     * 知乎页面解析方法
     * @param page 待解析的Page对象
     * @return 解析完成的Page对象文件
     * @throws XPatherException Exception
     * @hidden
     * 2019.12.19
     * 解决了xpath解析失败的问题：配置文件中的引号嵌套
     */
    public Page process(Page page) throws XPatherException {
        HtmlCleaner htmlCleaner = new HtmlCleaner();

        //数据清洗（设置编码格式为UTF-8）
        try {
            URL url = new URL(page.getUrl());
            rootNode = htmlCleaner.clean(url, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

        //判断页面类型来分类解析
        if(page.getUrl().startsWith("https://www.zhihu.com/people")){
            //按照个人主页的方式来解析并返回
            return processPeoplePage(page);
        }else {
            //否则按照提问页的方式来解析
            return processQuestionPage(page);
        }
    }

    /**
     * 解析知乎的个人主页
     * @param page 待解析的Page对象
     * @return 解析完成的Page对象
     */
    public Page processPeoplePage(Page page){
        /*
        通过html格式中的标签名来获取网页标题，
        网页标题的特征是标签名为“name”，根据此标签名获取标题的内容，
        并将它存入Page对象的title字段中
         */
        page.setTitle(HtmlUtil.getFieldByTagName(rootNode,
                "title", 0));

        /*
        获取用户名称（从标题文本中获取）
        由于知乎的个人主页的网站标题中总包含该用户的用户名，
        所以直接从标题中提取用户名，并存入Page对象的entryName字段中
         */
        StringBuilder name = new StringBuilder();
        char flag = page.getTitle().charAt(0);
        int index = 0;
        while(flag!= ' '){
            name.append(flag);
            index++;
            flag = page.getTitle().charAt(index);
        }
        page.setEntryName(name.toString());

        /*
        获取用户信息（教育经历）
        通过该项信息（教育经历）在知乎个人主页中的位置，提取出其统一的XPath路径，
        该XPath字符串存放在知乎爬虫配置文件"Zhihu.properties"中，
        通过LoadPropertyUtil工具包的方法从配置文件中加载XPath来提取教育经历
         */
        page.setDetails(HtmlUtil.getFieldByRegex(rootNode,
                LoadPropertyUtil.loadZhihuProperty("educationXPath"),
                ".+"));
        
        /*
        通过用户动态获取获取其他用户的主页链接
        用户的动态中的用户信息都存放在主页下的itemprop="url"结点中，
        该结点下的content标签中即是url字符串，
        存在问题：一些用户的动态中只有自己，而爬虫加载用户主页时只能加载到4-5条记录，
        可能导致爬虫由于不能获取到新的url而urlSet列表中的url用完而崩溃
        */
        String[] result = HtmlUtil.getNodesByAttValue(rootNode,
                "itemprop", "url", "content");
        //todo::通过用户动态获取可能导致崩溃，尝试在用户关注列表获取其他用户的主页链接
        for(String s: result){
            //todo::暂时只做用户个人主页的爬取
            //将获取到的是个人主页的页面加入待爬取库urlset
            if(s.startsWith("https://www.zhihu.com/people") && !s.equals("https://www.zhihu.com/people")){
                if(s.endsWith("activities")) MysqlUtil.addToTable(s, "urlset");
                else MysqlUtil.addToTable(s+"/activities", "urlset");
            }
        }

        /*
        //todo::通过用户的关注列表获取主页，存在问题：通过标签为UserLink-link的结点似乎获取不到关注的用户
        String[] r = HtmlUtil.getNodesByAttValue(rootNode,
                "class", "UserLink-link", "href");
        for(String s: r){
            s = "https:" + s;
            //将获取到的是个人主页的页面加入待爬取库urlset
            if(s.startsWith("https://www.zhihu.com/people") && !s.equals("https://www.zhihu.com/people")){
                if(s.endsWith("activities")) MysqlUtil.addToTable(s, "urlset");
                else MysqlUtil.addToTable(s + "/activities", "urlset");
            }
        }
         */
        //返回解析完成的Page对象
        return page;
    }

    /**
     * 解析知乎提问页
     * @param page 待解析的Page对象
     * @return 解析完成的Page对象
     * @hidden 弃用的方法
     */
    public Page processQuestionPage(Page page){
        MysqlUtil mysqlUtil = new MysqlUtil();
        //url = "http:" + HtmlUtil.getNodesByAttValue(rootNode,
        //        "class", "UserLink-link",
        //        "href");
        //MysqlUtil.addToTable(url, "urlset");
        return page;
    }
}
