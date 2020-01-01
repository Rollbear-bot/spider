package util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author rollbear
 * ������ʽ������
 * 2019.12.15
 */
public class RegexUtil {
    /**
     * ������ʽƥ�䷽��
     * @param content ��ƥ����ı�
     * @param pattern ������ʽ����
     * @param groupNum ƥ������
     * @return ƥ��ɹ��Ľ��������ʧ�䷵��0
     */
    public static String getPageInfoByRegex(String content, Pattern pattern, int groupNum){
        //Pattern pattern = Pattern.compile(regexRule);
        Matcher matcher = pattern.matcher(content);
        //ƥ��ɹ�����ƥ��ֵ��ƥ��ʧ�ܷ����ַ�0
        if(matcher.find()) return matcher.group(groupNum);
        else return "0";
    }
}
