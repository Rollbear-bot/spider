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
 * ֪������ʵ����
 */
public class ZhihuProcessService implements IProcessService {
    //����ϴ��ɵ�ҳ��������Ϊ���Ա�����������Ա�����ĵ���
    private TagNode rootNode;

    /**
     * ֪��ҳ���������
     * @param page ��������Page����
     * @return ������ɵ�Page�����ļ�
     * @throws XPatherException Exception
     * @hidden
     * 2019.12.19
     * �����xpath����ʧ�ܵ����⣺�����ļ��е�����Ƕ��
     */
    public Page process(Page page) throws XPatherException {
        HtmlCleaner htmlCleaner = new HtmlCleaner();

        //������ϴ�����ñ����ʽΪUTF-8��
        try {
            URL url = new URL(page.getUrl());
            rootNode = htmlCleaner.clean(url, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

        //�ж�ҳ���������������
        if(page.getUrl().startsWith("https://www.zhihu.com/people")){
            //���ո�����ҳ�ķ�ʽ������������
            return processPeoplePage(page);
        }else {
            //����������ҳ�ķ�ʽ������
            return processQuestionPage(page);
        }
    }

    /**
     * ����֪���ĸ�����ҳ
     * @param page ��������Page����
     * @return ������ɵ�Page����
     */
    public Page processPeoplePage(Page page){
        /*
        ͨ��html��ʽ�еı�ǩ������ȡ��ҳ���⣬
        ��ҳ����������Ǳ�ǩ��Ϊ��name�������ݴ˱�ǩ����ȡ��������ݣ�
        ����������Page�����title�ֶ���
         */
        page.setTitle(HtmlUtil.getFieldByTagName(rootNode,
                "title", 0));

        /*
        ��ȡ�û����ƣ��ӱ����ı��л�ȡ��
        ����֪���ĸ�����ҳ����վ�������ܰ������û����û�����
        ����ֱ�Ӵӱ�������ȡ�û�����������Page�����entryName�ֶ���
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
        ��ȡ�û���Ϣ������������
        ͨ��������Ϣ��������������֪��������ҳ�е�λ�ã���ȡ����ͳһ��XPath·����
        ��XPath�ַ��������֪�����������ļ�"Zhihu.properties"�У�
        ͨ��LoadPropertyUtil���߰��ķ����������ļ��м���XPath����ȡ��������
         */
        page.setDetails(HtmlUtil.getFieldByRegex(rootNode,
                LoadPropertyUtil.loadZhihuProperty("educationXPath"),
                ".+"));
        
        /*
        ͨ���û���̬��ȡ��ȡ�����û�����ҳ����
        �û��Ķ�̬�е��û���Ϣ���������ҳ�µ�itemprop="url"����У�
        �ý���µ�content��ǩ�м���url�ַ�����
        �������⣺һЩ�û��Ķ�̬��ֻ���Լ�������������û���ҳʱֻ�ܼ��ص�4-5����¼��
        ���ܵ����������ڲ��ܻ�ȡ���µ�url��urlSet�б��е�url���������
        */
        String[] result = HtmlUtil.getNodesByAttValue(rootNode,
                "itemprop", "url", "content");
        //todo::ͨ���û���̬��ȡ���ܵ��±������������û���ע�б��ȡ�����û�����ҳ����
        for(String s: result){
            //todo::��ʱֻ���û�������ҳ����ȡ
            //����ȡ�����Ǹ�����ҳ��ҳ��������ȡ��urlset
            if(s.startsWith("https://www.zhihu.com/people") && !s.equals("https://www.zhihu.com/people")){
                if(s.endsWith("activities")) MysqlUtil.addToTable(s, "urlset");
                else MysqlUtil.addToTable(s+"/activities", "urlset");
            }
        }

        /*
        //todo::ͨ���û��Ĺ�ע�б��ȡ��ҳ���������⣺ͨ����ǩΪUserLink-link�Ľ���ƺ���ȡ������ע���û�
        String[] r = HtmlUtil.getNodesByAttValue(rootNode,
                "class", "UserLink-link", "href");
        for(String s: r){
            s = "https:" + s;
            //����ȡ�����Ǹ�����ҳ��ҳ��������ȡ��urlset
            if(s.startsWith("https://www.zhihu.com/people") && !s.equals("https://www.zhihu.com/people")){
                if(s.endsWith("activities")) MysqlUtil.addToTable(s, "urlset");
                else MysqlUtil.addToTable(s + "/activities", "urlset");
            }
        }
         */
        //���ؽ�����ɵ�Page����
        return page;
    }

    /**
     * ����֪������ҳ
     * @param page ��������Page����
     * @return ������ɵ�Page����
     * @hidden ���õķ���
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
