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
 * �ٶȰٿƽ����ӿ�ʵ����
 * @hidden
 * ע�⣺���಻�Ǳ���Ŀ��������ֻ����Ϊ��϶ȵĲ���
 * ����Ŀ������������ȡ֪���������StartZhihu��
 */
public class BaikeProcessService implements IProcessService {
    //������ϴ���TagNode��Ա���������������������
    private TagNode rootNode;

    /**
     * �ٶȰٿƽ�������
     * ʹ��htmlCleaner����������ϴ
     * �ӰٶȰٿ������ļ��л�ȡXPath��������ʽ
     * @param page ��Ҫ������ҳ�����
     * @return page Page
     * @hidden
     * 2019.12.19
     * �����xpath����ʧ�ܵ����⣺�����ļ��е�����Ƕ��
     * @hidden
     *  * ע�⣺���಻�Ǳ���Ŀ��������ֻ����Ϊ��϶ȵĲ���
     *  * ����Ŀ������������ȡ֪���������StartZhihu��
     */
    public Page process(Page page) {
        HtmlCleaner htmlCleaner = new HtmlCleaner();

        try {
            //������ϴ
            URL url = new URL(page.getUrl());
            //todo::clean(url,charset)�ǹ�ʱ�ķ���
            //��������ϴʱʹ��URL����ķ�ʽ����ϴ���������ַ����뷽ʽ��
            //���ñ��뷽ʽΪutf-8���Խ���������������
            rootNode = htmlCleaner.clean(url, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

        //�������
        if(page.getUrl().startsWith("https://baike.baidu.com/item")){
            //���Ҫ������ҳ���Ǵ���ҳ����ʹ�ô�����������
            return processEntryPage(page);
        }else{
            //����ʹ�÷���ҳ���������
            return processIndexPage(page);
        }
    }


    /**
     * ����ҳ����������װ
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
        //��������ɵ�entry����ҳ�������

        page.setEntryName(entryName);
        //todo::����������Ϣ����Ż�page��

        return page;
    }


    /**
     * ����ҳ����������װ
     * ������ҳ�������Ĵ���url����urlSet�еȴ�����
     * @param page һ��δ������Page����
     * @return һ��������ɵ�Page����
     * @hidden
     * 2019.12.19
     * ������Object���飬����TagNode����������getElementsByName���Խ������ʧ�ܵ�����
     * ��������ΪObject����ĳ�ʼ����������
     */
    private Page processIndexPage(Page page){
        try {
            //Object[] objects = rootNode.evaluateXPath(LoadPropertyUtil.loadBaikeProperty("indexXPath"));
            TagNode[] objects = rootNode.getElementsByName("a", true);
            if(objects.length>0){
                TagNode indexNode = (TagNode) objects[0];
                //String result = indexNode.getAttributeByName("herf");
                //List result = objects[0].getChildTagList();
                //todo::������ģ����ʹ��TagNode[]�����գ����Խ������ʧ�ܵ�����
                System.out.println(indexNode.getText().toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return page;
    }
}