package util;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;
import java.util.regex.Pattern;

/**
 * @author rollbear
 * 2019.12.15
 * html���������ࣺ�������������󵽴�
 */
public class HtmlUtil {
    /**
     * ͨ������ֵ��ȡ�ֶ�
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
     * ͨ��xpath��������ʽ��ȡ�ֶ�
     * @param tagNode TagNode
     * @param XPath xpath string
     * @param regexRule String
     * @return String
     * @hidden
     * 2019.12.19
     * ���Բ�ͨ��node��ת����ֱ�ӽ�evaluateXPath[0]��Ϊ�����Ľ��
     */
    public static String getFieldByRegex(TagNode tagNode, String XPath, String regexRule) {
        //����ƥ��ɹ�����groupNumָ����ƥ��ֵ��ƥ��ʧ�ܷ����ַ�0
        //return RegexUtil.getPageInfoByRegex(tagNode.getText().toString(), regexRule, groupNum);
        String result = "0";
        Object[] evaluateXPath;
        try{
            evaluateXPath = tagNode.evaluateXPath(XPath);

            if(evaluateXPath.length>0){
                //todo::xpath�Ľ���ʧ�ܻᵼ��evaluateXPath�޷�ת����TagNode����
                //TagNode node = (TagNode) evaluateXPath[0];

                //Pattern.DOTALL������dotallģʽ��������ģʽ�£����ʽ��.������ƥ�������ַ���
                //������ʾһ�еĽ�������Ĭ������£����ʽ��.����ƥ���еĽ�������
                Pattern pattern = Pattern.compile(regexRule, Pattern.DOTALL);

                //���Բ�ͨ��node��ת����ֱ�ӽ�evaluateXPath[0]��Ϊ�����Ľ��
                result = RegexUtil.getPageInfoByRegex(evaluateXPath[0].toString(), pattern, 0);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    /**
     * ͨ����ǩ������ȡ�ֶ�
     * @param tagNode ҳ����ϴ���TagNode����
     * @param tagName Ҫ��ȡ�ı�ǩ��
     * @param groupNum ƥ�������
     * @return ���ر�ǩ�ڵ��ı�
     */
    public static String getFieldByTagName(TagNode tagNode, String tagName, int groupNum){
        TagNode[] nodes = tagNode.getElementsByName(tagName, true);
        return nodes[groupNum].getText().toString();
    }

    /**
     * ͨ������ֵ����ȡ�ֶ�
     * @param tagNode ҳ����ϴ���TagNode����
     * @param att ������
     * @param value ����ֵ
     * @return ���з����������ַ����ļ���
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
     * ���Է���
     * @param args String
     */
    public static void main(String[] args) {
        String xpath = "/html/head/meta[7]";
        //getAttributeByName()
    }
}
