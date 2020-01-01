package impl;

import service.IShowService;
import util.MysqlUtil;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

/**
 * 1 * 通过图形界面的方式来实现展示接口
 * 2 * @Author: rollbear
 * 3 * @Date: 2019/12/27 11:18
 */
public class GUIShowService implements IShowService {
    private static String enterName;
    private static String education;
    private static String url;
    private static final String tableName = "singlepage";

    /**
     * 重写展示方法show，实现通过GUI来展示
     */
    @Override
    public void show() {
        //连接数据库，不连接数据库就不能获取到数据
        MysqlUtil mysqlUtil = new MysqlUtil();

        //创建一个窗体
        JFrame jFrame = new JFrame();
        jFrame.setSize(500, 150);
        jFrame.setLocationRelativeTo(null);

        //创建一个垂直的箱状结构，用于垂直放置信息面板和输入框
        Box box = Box.createVerticalBox();

        //创建展示信息的面板
        JLabel infoBoard = new JLabel("hello!");
        JLabel nameBoard = new JLabel("");
        JLabel urlboard = new JLabel("");

        //创建一个用于输入用户名称的文本框及确认按钮
        TextField nameInputField = new TextField("在此输入用户名");
        Button enterButton = new Button("确认");
        enterButton.addActionListener(actionEvent -> {
            enterName = nameInputField.getText();
            education = Objects.requireNonNull(MysqlUtil.getRowByKey(
                    "entryName",
                    enterName,
                    tableName)).getDetails();
            url = Objects.requireNonNull(MysqlUtil.getRowByKey(
                    "entryName",
                    enterName,
                    tableName)).getUrl();
            //若数据表
            infoBoard.setText((education.equals("0"))?
                    "该用户没有填写教育经历":
                    education);
            nameBoard.setText(enterName);
            urlboard.setText(url);
        });

        //整合界面
        box.add(nameBoard);
        box.add(infoBoard);
        box.add(urlboard);
        box.add(nameInputField);
        box.add(enterButton);
        jFrame.add(box);

        jFrame.setVisible(true);
    }
}
