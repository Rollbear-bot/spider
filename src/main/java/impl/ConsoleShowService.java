package impl;

import entity.Page;
import service.IShowService;
import util.MysqlUtil;

import java.util.Scanner;

/**
 * 1 * �ڿ���̨��ʵ������չʾ�ӿ�
 * 2 * @Author: rollbear
 * 3 * @Date: 2019/12/26 17:17
 */
public class ConsoleShowService implements IShowService {
    @Override
    public void show() {
        //�����������ݿ�
        MysqlUtil mysqlUtil = new MysqlUtil();

        String string;
        Scanner scanner = new Scanner(System.in);

        //�����ݿ��н����û��Ĳ�ѯ����Ϣչʾ
        System.out.println("��������Ҫ��ѯ���û����û�����");
        while(scanner.hasNextLine()){
            string = scanner.nextLine();
            Page userPage = MysqlUtil.getRowByKey("entryName", string, "singlepage");
            if(userPage!=null && userPage.getEntryName()!=null){
                System.out.println("���ҵ���¼");
                System.out.println("�û�����" + userPage.getEntryName());
                if(userPage.getDetails().equals("0")){
                    System.out.println("�������������û�û����д��������");
                }else System.out.println("����������" + userPage.getDetails());
                System.out.println("�û���ҳ��ַ��" + userPage.getUrl());
            }else System.out.println("δ�ҵ����û��ļ�¼");
            System.out.println("��������Ҫ��ѯ���û����û�����");
        }
    }
}
