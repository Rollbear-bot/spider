package start;

import impl.GUIShowService;
import service.IShowService;

/**
 * 2 * @Author: rollbear
 * 3 * @Date: 2019/12/26 17:25
 * 4
 */
public class StartShow {
    /**
     * ���������ڴ���������չʾ����
     * @param args String[]
     */
    public static void main(String[] args) {
        StartShow show = new StartShow();

        //��������չʾ��ʽΪGUIģʽ
        show.setShowService(new GUIShowService());
        //��������չʾ
        show.getShowService().show();
    }

    private IShowService showService;

    public IShowService getShowService() {
        return showService;
    }

    public void setShowService(IShowService showService) {
        this.showService = showService;
    }
}
