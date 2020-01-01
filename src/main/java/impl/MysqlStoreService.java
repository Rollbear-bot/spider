package impl;

import entity.Page;
import service.IStoreService;
import util.MysqlUtil;

/**
 * Mysql存储实现类
 * 使用Mysql存储来实现存储接口IStoreService，使用MysqlUtil工具包
 * @author rollbear
 * 2019.12.18
 */
public class MysqlStoreService implements IStoreService {
    //插入记录的表名，根据需求修改
    static final String tableName = "singlepage";

    @Override
    public void store(Page page) {
        MysqlUtil.addToTable(page, tableName);
    }
}
