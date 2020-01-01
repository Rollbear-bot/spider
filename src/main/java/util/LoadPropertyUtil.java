package util;

import java.util.Locale;
import java.util.MissingFormatArgumentException;
import java.util.ResourceBundle;

/**
 * @author rollbear
 * 2019.12.16
 * 加载配置文件工具
 */
public class LoadPropertyUtil {
    /**
     * 百度百科配置文件加载方法
     * @param key 配置文件中的项
     * @return 配置文件中该项的值
     * @hidden
     *  * 注意：该类不是本项目的主任务，只是作为耦合度的测试
     *  * 该项目的主任务是爬取知乎，入口在StartZhihu中
     */
    public static String loadBaikeProperty(String key){
        String value = "";
        Locale locale = Locale.getDefault();
        try{
            //查找名为BaiduBaike的配置文件
            ResourceBundle localeResource = ResourceBundle.getBundle("BaiduBaike", locale);
            value = localeResource.getString(key);
        }catch (MissingFormatArgumentException e){
            //没有该配置文件（查找失败）则value为空
            value = "";
        }
        return value;
    }


    /**
     * 知乎配置文件加载方法
     * @param key 配置文件中的项
     * @return 配置文件中该项的值
     */
    public static String loadZhihuProperty(String key){
        String value = "";
        Locale locale = Locale.getDefault();
        try{
            //查找名为Zhihu的配置文件
            ResourceBundle localeResource = ResourceBundle.getBundle("Zhihu", locale);
            value = localeResource.getString(key);
        }catch (MissingFormatArgumentException e){
            //没有该配置文件（查找失败）则value为空
            value = "";
        }
        return value;
    }
}
