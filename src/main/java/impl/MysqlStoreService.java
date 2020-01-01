package impl;

import entity.Page;
import service.IStoreService;
import util.MysqlUtil;

/**
 * Mysql�洢ʵ����
 * ʹ��Mysql�洢��ʵ�ִ洢�ӿ�IStoreService��ʹ��MysqlUtil���߰�
 * @author rollbear
 * 2019.12.18
 */
public class MysqlStoreService implements IStoreService {
    //�����¼�ı��������������޸�
    static final String tableName = "singlepage";

    @Override
    public void store(Page page) {
        MysqlUtil.addToTable(page, tableName);
    }
}
