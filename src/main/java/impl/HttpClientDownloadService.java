package impl;

import entity.Page;
import service.IDownloadService;
import util.PageDownloadUtil;

public class HttpClientDownloadService implements IDownloadService {
    @Override
    public Page download(String url) {
        //��дdownload����
        Page page = new Page();
        page.setContent(PageDownloadUtil.getPageContent(url));
        return page;
    }
}
