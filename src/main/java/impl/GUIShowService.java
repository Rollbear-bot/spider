package impl;

import service.IShowService;
import util.MysqlUtil;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

/**
 * 1 * ͨ��ͼ�ν���ķ�ʽ��ʵ��չʾ�ӿ�
 * 2 * @Author: rollbear
 * 3 * @Date: 2019/12/27 11:18
 */
public class GUIShowService implements IShowService {
    private static String enterName;
    private static String education;
    private static String url;
    private static final String tableName = "singlepage";

    /**
     * ��дչʾ����show��ʵ��ͨ��GUI��չʾ
     */
    @Override
    public void show() {
        //�������ݿ⣬���������ݿ�Ͳ��ܻ�ȡ������
        MysqlUtil mysqlUtil = new MysqlUtil();

        //����һ������
        JFrame jFrame = new JFrame();
        jFrame.setSize(500, 150);
        jFrame.setLocationRelativeTo(null);

        //����һ����ֱ����״�ṹ�����ڴ�ֱ������Ϣ���������
        Box box = Box.createVerticalBox();

        //����չʾ��Ϣ�����
        JLabel infoBoard = new JLabel("hello!");
        JLabel nameBoard = new JLabel("");
        JLabel urlboard = new JLabel("");

        //����һ�����������û����Ƶ��ı���ȷ�ϰ�ť
        TextField nameInputField = new TextField("�ڴ������û���");
        Button enterButton = new Button("ȷ��");
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
            //�����ݱ�
            infoBoard.setText((education.equals("0"))?
                    "���û�û����д��������":
                    education);
            nameBoard.setText(enterName);
            urlboard.setText(url);
        });

        //���Ͻ���
        box.add(nameBoard);
        box.add(infoBoard);
        box.add(urlboard);
        box.add(nameInputField);
        box.add(enterButton);
        jFrame.add(box);

        jFrame.setVisible(true);
    }
}
