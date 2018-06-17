package YingYingMonster.LetsDo_Phase_III.entity.json;

import YingYingMonster.LetsDo_Phase_III.service.AdminService;

public class SystemInfo {
    private int publisherNum;
    private int workerNum;
    private int historyProjectNum;
    private int ongoingProjectNum;
    private String[][] workerTop100;

    public SystemInfo(AdminService adminService) {
        this.publisherNum = adminService.viewAllPublishers().size();
        this.workerNum = adminService.viewAllWorkers().size();
        this.historyProjectNum = adminService.viewDoneProject().size();
        this.ongoingProjectNum = adminService.viewDoingProject().size();
    }

    public int getPublisherNum() {
        return publisherNum;
    }

    public void setPublisherNum(int publisherNum) {
        this.publisherNum = publisherNum;
    }

    public int getWorkerNum() {
        return workerNum;
    }

    public void setWorkerNum(int workerNum) {
        this.workerNum = workerNum;
    }

    public int getHistoryProjectNum() {
        return historyProjectNum;
    }

    public void setHistoryProjectNum(int historyProjectNum) {
        this.historyProjectNum = historyProjectNum;
    }

    public int getOngoingProjectNum() {
        return ongoingProjectNum;
    }

    public void setOngoingProjectNum(int ongoingProjectNum) {
        this.ongoingProjectNum = ongoingProjectNum;
    }

    public String[][] getWorkerTop100() {
        return workerTop100;
    }

    public void setWorkerTop100(String[][] workerTop100) {
        this.workerTop100 = workerTop100;
    }
}
