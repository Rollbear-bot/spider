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
     * 主方法：在此启动数据展示界面
     * @param args String[]
     */
    public static void main(String[] args) {
        StartShow show = new StartShow();

        //设置数据展示方式为GUI模式
        show.setShowService(new GUIShowService());
        //启动数据展示
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
