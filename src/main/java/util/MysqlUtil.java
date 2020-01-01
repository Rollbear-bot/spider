package util;

import entity.Page;

import java.sql.*;

/**
 * Mysql连接工具
 * @author rollbear
 * 2019.12.17
 */
public class MysqlUtil {
    /**
     * 用户、密码和数据库名、表名
     * 根据需求修改
     */
    static final String USER = "root";
    static final String PASS = "psd_123456";
    static final String databaseName = "spiderdatabase";


    //部分数据库操作语句
    static final String sqlInsertPre = "insert into "; //数据库操作语句（插入）前缀，操作时要加上表名
    static final String sqlInsertPos = " values(?,?,?)"; //数据库操作语句（插入）后缀，操作时要加上表名
    static final String sqlGet = "SELECT entryName, details, url FROM "; //数据库操作语句（读取），操作时要加上表名

    static Connection conn = null;
    static Statement stmt = null;

    /**
     * 构造函数
     * 在构造函数中建立与数据库的连接
     * 重要：在调用该工具包的方法前必须先new一个该类的对象，否则因为未连接数据库会出现初始化失败
     */
    public MysqlUtil(){
        try{
            // 注册 JDBC 驱动
            Class.forName("com.mysql.cj.jdbc.Driver");
            // 打开链接
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/"
                    + databaseName
                    +"?useSSL=false&serverTimezone=UTC",
                    USER,PASS);
            //实例化对象
            stmt = conn.createStatement();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    /**
     * 添加一条记录到数据表
     * 添加成功返回true，添加失败返回false
     * @param page Page
     * @return boolean
     */
    public static boolean addToTable(Page page, String tableName){
        try {
            //对sql语句进行预编译处理
            PreparedStatement preparedStatement = conn.prepareStatement(sqlInsertPre + tableName + sqlInsertPos);
            //记录的第一列为“词条名”
            preparedStatement.setString(1, page.getEntryName());
            //记录的第二列为“词条解释”
            preparedStatement.setString(2, page.getDetails());
            //记录的第三列为“词条url”
            preparedStatement.setString(3, page.getUrl());
            //执行sql语句，将数据传输到数据库
            preparedStatement.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 添加一条url到指定表 urlPusher
     * 重载插入方法实现
     * @param url 待访问的url
     * @param tableName 要插入到的表名
     * @return 执行情况（布尔值）
     */
    public static boolean addToTable(String url, String tableName){
        //如果这条url已经在表中，则不添加
        if(urlIsInTable(url, tableName))return false;

        String sqlInsertUrl = "insert into " + tableName + " values(?,?)";
        String sqlGetIndex = "SELECT * FROM " + tableName;
        int index = 0;
        try{
            //获取表中最新的索引
            PreparedStatement preparedStatement = conn.prepareStatement(sqlGetIndex);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                index = resultSet.getInt("RowKey");
            }

            //索引+1，并存入新的url
            preparedStatement = conn.prepareStatement(sqlInsertUrl);
            //记录的第一列为索引
            preparedStatement.setInt(1, index+1);
            //记录的第二列为url
            preparedStatement.setString(2, url);

            //执行sql语句，将数据传输到数据库
            preparedStatement.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 从数据库读取一条记录
     * 读取成功返回一个page否则返回null
     * @param key indexString
     * @return thePageYouGot or null
     */
    public static Page getRow(String key, String tableName){
        try {
            //对sql语句进行预编译处理
            PreparedStatement preparedStatement = conn.prepareStatement(sqlGet + tableName);
            //获取结果数据集
            ResultSet resultSet = preparedStatement.executeQuery();
            //展开数据集
            Page page = new Page();
            while (resultSet.next()){
                page.setEntryName(resultSet.getString("entryName"));
                page.setDetails(resultSet.getString("details"));
                page.setUrl(resultSet.getString("url"));
                //使用词条名来作为检索的key
                if(page.getEntryName().equals(key))return page;
            }
            return null;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 使用索引从数据库读取一条记录
     * 读取成功返回一个page否则返回null
     * @param keyType 索引名
     * @param key 索引值
     * @param tableName 表名
     * @return 返回指定的记录生成的Page对象
     */
    public static Page getRowByKey(String keyType, String key, String tableName){
        String keyStr = "" + "'" + key + "'";
        //Sql语句：根据键值检索
        String sqlSelect = "SELECT * FROM "+ tableName +" WHERE "+ keyType +"=" + keyStr;
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sqlSelect);
            ResultSet resultSet = preparedStatement.executeQuery();
            //展开数据集
            Page page = new Page();
            while(resultSet.next()){
                page.setEntryName(resultSet.getString("entryName"));
                page.setDetails(resultSet.getString("details"));
                page.setUrl(resultSet.getString("url"));
            }
            return page;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 通过索引从url库中获取一条url
     * @param key 索引值
     * @param tableName 表名
     * @return 获取到的url或者获取失败返回“0”
     */
    public static String getUrl(int key, String tableName){
        //String keyStr = "" + "'" + key + "'";
        //Sql语句：根据键值检索
        String sqlSelect = "SELECT url FROM "+ tableName +" WHERE RowKey " + "= " + key;
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sqlSelect);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return resultSet.getString("url");
            }
            else return "0";
        }
        catch (SQLException e) {
            e.printStackTrace();
            return "0";
        }
    }

    /**
     * 查找一条url是否在表中
     * @param url 要查找的url
     * @param tableName 要查找的表名
     * @return 返回布尔值，是或否
     */
    public static boolean urlIsInTable(String url, String tableName){

        String keyStr = "" + "'" + url + "'";
        String sqlSelect = "SELECT url FROM "+ tableName +" WHERE url " + " = " + keyStr;
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sqlSelect);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }


    }


    public boolean createTable(String tableName){
        //todo::新建表
        //String sqlCreateTable = "create table " + tableName
        return true;
    }

    public boolean deleteTable(String key, String tableName){
        //todo::参考教程完成表删除方法（次要）
        return true;
    }


    /**
     * 测试主类
     * @param args args
     */
    public static void main(String[] args) {
        MysqlUtil mysqlUtil = new MysqlUtil();
        String s = getUrl(0, "urlset");
    }
}