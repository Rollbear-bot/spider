package util;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;
import java.util.regex.Pattern;

/**
 * @author rollbear
 * 2019.12.15
 * html解析工具类：将解析方法抽象到此
 */
public class HtmlUtil {
    /**
     * 通过属性值获取字段
     * @param tagNode TagNode
     * @param XPath String
     * @param att String
     * @return String
     */
    public static String getAttributeByName(TagNode tagNode, String XPath, String att){
        String result = "0";
        Object[] evaluateXPath;
        try{
            evaluateXPath = tagNode.evaluateXPath(XPath);
            if(evaluateXPath.length>0){
                TagNode node = (TagNode)evaluateXPath[0];
                result = node.getAttributeByName(att);
            }
        }catch (XPatherException e){
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 通过xpath和正则表达式获取字段
     * @param tagNode TagNode
     * @param XPath xpath string
     * @param regexRule String
     * @return String
     * @hidden
     * 2019.12.19
     * 尝试不通过node的转换，直接将evaluateXPath[0]作为解析的结果
     */
    public static String getFieldByRegex(TagNode tagNode, String XPath, String regexRule) {
        //正则匹配成功返回groupNum指定的匹配值，匹配失败返回字符0
        //return RegexUtil.getPageInfoByRegex(tagNode.getText().toString(), regexRule, groupNum);
        String result = "0";
        Object[] evaluateXPath;
        try{
            evaluateXPath = tagNode.evaluateXPath(XPath);

            if(evaluateXPath.length>0){
                //todo::xpath的解析失败会导致evaluateXPath无法转换成TagNode类型
                //TagNode node = (TagNode) evaluateXPath[0];

                //Pattern.DOTALL：启用dotall模式。在这种模式下，表达式‘.’可以匹配任意字符，
                //包括表示一行的结束符。默认情况下，表达式‘.’不匹配行的结束符。
                Pattern pattern = Pattern.compile(regexRule, Pattern.DOTALL);

                //尝试不通过node的转换，直接将evaluateXPath[0]作为解析的结果
                result = RegexUtil.getPageInfoByRegex(evaluateXPath[0].toString(), pattern, 0);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 通过标签名来获取字段
     * @param tagNode 页面清洗后的TagNode对象
     * @param tagName 要获取的标签名
     * @param groupNum 匹配的组数
     * @return 返回标签内的文本
     */
    public static String getFieldByTagName(TagNode tagNode, String tagName, int groupNum){
        TagNode[] nodes = tagNode.getElementsByName(tagName, true);
        return nodes[groupNum].getText().toString();
    }

    /**
     * 通过属性值来获取字段
     * @param tagNode 页面清洗后的TagNode对象
     * @param att 属性名
     * @param value 属性值
     * @return 所有符合条件的字符串的集合
     */
    public static String[] getNodesByAttValue(TagNode tagNode, String att, String value, String attYouNeed){
        TagNode[] nodes = tagNode.getElementsByAttValue(att, value, true, true);
        String[] result = new String[nodes.length];
        for(int i = 0; i< nodes.length; i++){
            result[i] = nodes[i].getAttributeByName(attYouNeed);
        }
        return result;
    }

    /**
     * 测试方法
     * @param args String
     */
    public static void main(String[] args) {
        String xpath = "/html/head/meta[7]";
        //getAttributeByName()
    }
}
