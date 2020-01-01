package impl;

import entity.Page;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import service.IProcessService;
import util.HtmlUtil;
import util.LoadPropertyUtil;
import java.io.IOException;
import java.net.URL;


/**
 * @author rollbear
 * 2019.12.15
 * 百度百科解析接口实现类
 * @hidden
 * 注意：该类不是本项目的主任务，只是作为耦合度的测试
 * 该项目的主任务是爬取知乎，入口在StartZhihu中
 */
public class BaikeProcessService implements IProcessService {
    //数据清洗后的TagNode成员，方便各个解析方法调用
    private TagNode rootNode;

    /**
     * 百度百科解析方法
     * 使用htmlCleaner进行数据清洗
     * 从百度百科配置文件中获取XPath和正则表达式
     * @param page 需要解析的页面对象
     * @return page Page
     * @hidden
     * 2019.12.19
     * 解决了xpath解析失败的问题：配置文件中的引号嵌套
     * @hidden
     *  * 注意：该类不是本项目的主任务，只是作为耦合度的测试
     *  * 该项目的主任务是爬取知乎，入口在StartZhihu中
     */
    public Page process(Page page) {
        HtmlCleaner htmlCleaner = new HtmlCleaner();

        try {
            //数据清洗
            URL url = new URL(page.getUrl());
            //todo::clean(url,charset)是过时的方法
            //在数据清洗时使用URL对象的方式来清洗可以设置字符编码方式，
            //设置编码方式为utf-8可以解决中文乱码的问题
            rootNode = htmlCleaner.clean(url, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

        //分类解析
        if(page.getUrl().startsWith("https://baike.baidu.com/item")){
            //如果要解析的页面是词条页，则使用词条解析方法
            return processEntryPage(page);
        }else{
            //否则使用分类页面解析方法
            return processIndexPage(page);
        }
    }


    /**
     * 词条页解析方法封装
     * @param page Page
     * @return page Page
     */
    private Page processEntryPage(Page page){
        String entryName = HtmlUtil.getFieldByRegex(rootNode,
                LoadPropertyUtil.loadBaikeProperty("entryXPath"),
                LoadPropertyUtil.loadBaikeProperty("regexRule"));
        //entryName = HtmlUtil.getAttributeByName(rootNode,
        //        LoadPropertyUtil.loadBaikeProperty("entryXPath"),
        //                "charset");
        //将解析完成的entry返回页面对象中

        page.setEntryName(entryName);
        //todo::解析其他信息并存放回page中

        return page;
    }


    /**
     * 分类页解析方法封装
     * 将分类页中索引的词条url放入urlSet中等待访问
     * @param page 一个未解析的Page对象
     * @return 一个解析完成的Page对象
     * @hidden
     * 2019.12.19
     * 放弃用Object数组，而用TagNode数组来接收getElementsByName可以解决解析失败的问题
     * 可能是因为Object对象的初始化出了问题
     */
    private Page processIndexPage(Page page){
        try {
            //Object[] objects = rootNode.evaluateXPath(LoadPropertyUtil.loadBaikeProperty("indexXPath"));
            TagNode[] objects = rootNode.getElementsByName("a", true);
            if(objects.length>0){
                TagNode indexNode = (TagNode) objects[0];
                //String result = indexNode.getAttributeByName("herf");
                //List result = objects[0].getChildTagList();
                //todo::在其他模块中使用TagNode[]来接收，尝试解决解析失败的问题
                System.out.println(indexNode.getText().toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return page;
    }
}