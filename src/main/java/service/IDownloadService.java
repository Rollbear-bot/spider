package service;

import entity.Page;

import java.io.IOException;

/**
 * 页面下载接口
 * @author rollbear
 * 2019.12.15
 */
public interface IDownloadService {
    //下载接口，返回值为Page
    Page download(String url) throws IOException;
}
