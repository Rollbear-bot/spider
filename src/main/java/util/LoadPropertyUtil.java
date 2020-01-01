package util;

import java.util.Locale;
import java.util.MissingFormatArgumentException;
import java.util.ResourceBundle;

/**
 * @author rollbear
 * 2019.12.16
 * ���������ļ�����
 */
public class LoadPropertyUtil {
    /**
     * �ٶȰٿ������ļ����ط���
     * @param key �����ļ��е���
     * @return �����ļ��и����ֵ
     * @hidden
     *  * ע�⣺���಻�Ǳ���Ŀ��������ֻ����Ϊ��϶ȵĲ���
     *  * ����Ŀ������������ȡ֪���������StartZhihu��
     */
    public static String loadBaikeProperty(String key){
        String value = "";
        Locale locale = Locale.getDefault();
        try{
            //������ΪBaiduBaike�������ļ�
            ResourceBundle localeResource = ResourceBundle.getBundle("BaiduBaike", locale);
            value = localeResource.getString(key);
        }catch (MissingFormatArgumentException e){
            //û�и������ļ�������ʧ�ܣ���valueΪ��
            value = "";
        }
        return value;
    }


    /**
     * ֪�������ļ����ط���
     * @param key �����ļ��е���
     * @return �����ļ��и����ֵ
     */
    public static String loadZhihuProperty(String key){
        String value = "";
        Locale locale = Locale.getDefault();
        try{
            //������ΪZhihu�������ļ�
            ResourceBundle localeResource = ResourceBundle.getBundle("Zhihu", locale);
            value = localeResource.getString(key);
        }catch (MissingFormatArgumentException e){
            //û�и������ļ�������ʧ�ܣ���valueΪ��
            value = "";
        }
        return value;
    }
}
