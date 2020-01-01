package impl;

import entity.Page;
import service.IShowService;
import util.MysqlUtil;

import java.util.Scanner;

/**
 * 1 * 在控制台中实现数据展示接口
 * 2 * @Author: rollbear
 * 3 * @Date: 2019/12/26 17:17
 */
public class ConsoleShowService implements IShowService {
    @Override
    public void show() {
        //首先连接数据库
        MysqlUtil mysqlUtil = new MysqlUtil();

        String string;
        Scanner scanner = new Scanner(System.in);

        //在数据库中进行用户的查询和信息展示
        System.out.println("请输入你要查询的用户的用户名：");
        while(scanner.hasNextLine()){
            string = scanner.nextLine();
            Page userPage = MysqlUtil.getRowByKey("entryName", string, "singlepage");
            if(userPage!=null && userPage.getEntryName()!=null){
                System.out.println("已找到记录");
                System.out.println("用户名：" + userPage.getEntryName());
                if(userPage.getDetails().equals("0")){
                    System.out.println("教育经历：该用户没有填写教育经历");
                }else System.out.println("教育经历：" + userPage.getDetails());
                System.out.println("用户主页地址：" + userPage.getUrl());
            }else System.out.println("未找到该用户的记录");
            System.out.println("请输入你要查询的用户的用户名：");
        }
    }
}
