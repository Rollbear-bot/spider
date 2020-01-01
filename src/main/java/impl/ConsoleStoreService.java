package impl;

import entity.Page;
import service.IStoreService;

/**
 * ¿ØÖÆÌ¨Êä³ö
 * @author rollbear
 * 2019.12.16
 */
public class ConsoleStoreService implements IStoreService {
    @Override
    public void store(Page page) {
        System.out.println("PAGEINFO");
        System.out.println("PageTitle:" + page.getTitle());
        System.out.println("EntryName:" + page.getEntryName());
        System.out.println("Details:" + page.getDetails());
        System.out.println("PageUrl:" + page.getUrl());
    }
}
