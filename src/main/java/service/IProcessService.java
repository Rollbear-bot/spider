package service;

import entity.Page;
import org.htmlcleaner.XPatherException;

import java.sql.SQLException;

/**
 * ҳ������ӿ�
 * @author rollbear
 * 2019.12.15
 */
public interface IProcessService {
    public Page process(Page page) throws XPatherException;
}
