package YingYingMonster.LetsDo_Phase_III.entity.json;

import YingYingMonster.LetsDo_Phase_III.entity.role.Publisher;
import YingYingMonster.LetsDo_Phase_III.entity.role.Worker;
import YingYingMonster.LetsDo_Phase_III.service.AdminService;
import com.google.gson.annotations.Expose;

import java.util.List;

public class SystemInfo {
    @Expose private int publisherNum;
    @Expose private int workerNum;
    @Expose private int historyProjectNum;
    @Expose private int ongoingProjectNum;
    @Expose private String[][] workerTop100;
    @Expose private List<Worker> workerList;
    @Expose private List<Publisher> publisherList;

    public SystemInfo(AdminService adminService) {
        this.workerList=adminService.viewAllWorkers();
        this.publisherList=adminService.viewAllPublishers();
        this.publisherNum = this.publisherList.size();
        this.workerNum = this.workerList.size();
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

    public List<Worker> getWorkerList() {
        return workerList;
    }

    public void setWorkerList(List<Worker> workerList) {
        this.workerList = workerList;
    }

    public List<Publisher> getPublisherList() {
        return publisherList;
    }

    public void setPublisherList(List<Publisher> publisherList) {
        this.publisherList = publisherList;
    }
}
