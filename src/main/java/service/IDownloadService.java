package service;

import entity.Page;

import java.io.IOException;

/**
 * ҳ�����ؽӿ�
 * @author rollbear
 * 2019.12.15
 */
public interface IDownloadService {
    //���ؽӿڣ�����ֵΪPage
    Page download(String url) throws IOException;
}
