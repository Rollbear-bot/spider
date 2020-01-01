package util;

import entity.Page;

import java.sql.*;

/**
 * Mysql���ӹ���
 * @author rollbear
 * 2019.12.17
 */
public class MysqlUtil {
    /**
     * �û�����������ݿ���������
     * ���������޸�
     */
    static final String USER = "root";
    static final String PASS = "psd_123456";
    static final String databaseName = "spiderdatabase";


    //�������ݿ�������
    static final String sqlInsertPre = "insert into "; //���ݿ������䣨���룩ǰ׺������ʱҪ���ϱ���
    static final String sqlInsertPos = " values(?,?,?)"; //���ݿ������䣨���룩��׺������ʱҪ���ϱ���
    static final String sqlGet = "SELECT entryName, details, url FROM "; //���ݿ������䣨��ȡ��������ʱҪ���ϱ���

    static Connection conn = null;
    static Statement stmt = null;

    /**
     * ���캯��
     * �ڹ��캯���н��������ݿ������
     * ��Ҫ���ڵ��øù��߰��ķ���ǰ������newһ������Ķ��󣬷�����Ϊδ�������ݿ����ֳ�ʼ��ʧ��
     */
    public MysqlUtil(){
        try{
            // ע�� JDBC ����
            Class.forName("com.mysql.cj.jdbc.Driver");
            // ������
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/"
                    + databaseName
                    +"?useSSL=false&serverTimezone=UTC",
                    USER,PASS);
            //ʵ��������
            stmt = conn.createStatement();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    /**
     * ���һ����¼�����ݱ�
     * ��ӳɹ�����true�����ʧ�ܷ���false
     * @param page Page
     * @return boolean
     */
    public static boolean addToTable(Page page, String tableName){
        try {
            //��sql������Ԥ���봦��
            PreparedStatement preparedStatement = conn.prepareStatement(sqlInsertPre + tableName + sqlInsertPos);
            //��¼�ĵ�һ��Ϊ����������
            preparedStatement.setString(1, page.getEntryName());
            //��¼�ĵڶ���Ϊ���������͡�
            preparedStatement.setString(2, page.getDetails());
            //��¼�ĵ�����Ϊ������url��
            preparedStatement.setString(3, page.getUrl());
            //ִ��sql��䣬�����ݴ��䵽���ݿ�
            preparedStatement.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * ���һ��url��ָ���� urlPusher
     * ���ز��뷽��ʵ��
     * @param url �����ʵ�url
     * @param tableName Ҫ���뵽�ı���
     * @return ִ�����������ֵ��
     */
    public static boolean addToTable(String url, String tableName){
        //�������url�Ѿ��ڱ��У������
        if(urlIsInTable(url, tableName))return false;

        String sqlInsertUrl = "insert into " + tableName + " values(?,?)";
        String sqlGetIndex = "SELECT * FROM " + tableName;
        int index = 0;
        try{
            //��ȡ�������µ�����
            PreparedStatement preparedStatement = conn.prepareStatement(sqlGetIndex);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                index = resultSet.getInt("RowKey");
            }

            //����+1���������µ�url
            preparedStatement = conn.prepareStatement(sqlInsertUrl);
            //��¼�ĵ�һ��Ϊ����
            preparedStatement.setInt(1, index+1);
            //��¼�ĵڶ���Ϊurl
            preparedStatement.setString(2, url);

            //ִ��sql��䣬�����ݴ��䵽���ݿ�
            preparedStatement.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * �����ݿ��ȡһ����¼
     * ��ȡ�ɹ�����һ��page���򷵻�null
     * @param key indexString
     * @return thePageYouGot or null
     */
    public static Page getRow(String key, String tableName){
        try {
            //��sql������Ԥ���봦��
            PreparedStatement preparedStatement = conn.prepareStatement(sqlGet + tableName);
            //��ȡ������ݼ�
            ResultSet resultSet = preparedStatement.executeQuery();
            //չ�����ݼ�
            Page page = new Page();
            while (resultSet.next()){
                page.setEntryName(resultSet.getString("entryName"));
                page.setDetails(resultSet.getString("details"));
                page.setUrl(resultSet.getString("url"));
                //ʹ�ô���������Ϊ������key
                if(page.getEntryName().equals(key))return page;
            }
            return null;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * ʹ�����������ݿ��ȡһ����¼
     * ��ȡ�ɹ�����һ��page���򷵻�null
     * @param keyType ������
     * @param key ����ֵ
     * @param tableName ����
     * @return ����ָ���ļ�¼���ɵ�Page����
     */
    public static Page getRowByKey(String keyType, String key, String tableName){
        String keyStr = "" + "'" + key + "'";
        //Sql��䣺���ݼ�ֵ����
        String sqlSelect = "SELECT * FROM "+ tableName +" WHERE "+ keyType +"=" + keyStr;
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sqlSelect);
            ResultSet resultSet = preparedStatement.executeQuery();
            //չ�����ݼ�
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
     * ͨ��������url���л�ȡһ��url
     * @param key ����ֵ
     * @param tableName ����
     * @return ��ȡ����url���߻�ȡʧ�ܷ��ء�0��
     */
    public static String getUrl(int key, String tableName){
        //String keyStr = "" + "'" + key + "'";
        //Sql��䣺���ݼ�ֵ����
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
     * ����һ��url�Ƿ��ڱ���
     * @param url Ҫ���ҵ�url
     * @param tableName Ҫ���ҵı���
     * @return ���ز���ֵ���ǻ��
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
        //todo::�½���
        //String sqlCreateTable = "create table " + tableName
        return true;
    }

    public boolean deleteTable(String key, String tableName){
        //todo::�ο��̳���ɱ�ɾ����������Ҫ��
        return true;
    }


    /**
     * ��������
     * @param args args
     */
    public static void main(String[] args) {
        MysqlUtil mysqlUtil = new MysqlUtil();
        String s = getUrl(0, "urlset");
    }
}