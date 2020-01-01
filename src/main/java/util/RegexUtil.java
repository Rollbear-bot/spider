package util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author rollbear
 * 正则表达式工具类
 * 2019.12.15
 */
public class RegexUtil {
    /**
     * 正则表达式匹配方法
     * @param content 待匹配的文本
     * @param pattern 正则表达式对象
     * @param groupNum 匹配组数
     * @return 匹配成功的结果，或者失配返回0
     */
    public static String getPageInfoByRegex(String content, Pattern pattern, int groupNum){
        //Pattern pattern = Pattern.compile(regexRule);
        Matcher matcher = pattern.matcher(content);
        //匹配成功返回匹配值，匹配失败返回字符0
        if(matcher.find()) return matcher.group(groupNum);
        else return "0";
    }
}
