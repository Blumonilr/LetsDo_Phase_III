package YingYingMonster.LetsDo_Phase_III.entity.json;

import YingYingMonster.LetsDo_Phase_III.entity.role.Publisher;
import YingYingMonster.LetsDo_Phase_III.entity.role.Worker;
import YingYingMonster.LetsDo_Phase_III.service.AdminService;
import com.google.gson.annotations.Expose;

import java.util.Calendar;
import java.util.List;

public class SystemInfo {
    @Expose private int publisherNum;
    @Expose private int workerNum;
    @Expose private int historyProjectNum;
    @Expose private int ongoingProjectNum;
    @Expose private String[][] workerTop100;
    @Expose private List<Worker> workerList;
    @Expose private List<Publisher> publisherList;
    @Expose private String[][] customerNum;

    public SystemInfo(AdminService adminService) {
        this.workerList=adminService.viewAllWorkers();
        this.publisherList=adminService.viewAllPublishers();
        this.publisherNum = this.publisherList.size();
        this.workerNum = this.workerList.size();
        this.historyProjectNum = adminService.viewDoneProject().size();
        this.ongoingProjectNum = adminService.viewDoingProject().size();

        //workerTop100
        List<Worker> list=adminService.workerAccuracyRank();
        int length=list.size()>=100?100:list.size();
        this.workerTop100=new String[length][6];
        toArray(adminService,list,this.workerTop100,length);

        //用户人数图表
        customerNum=new String[13][3];
        customerNum[0][0]="月份";
        customerNum[0][1]="当月注册人数";
        customerNum[0][2]="当月总人数";
        Calendar temp=Calendar.getInstance();
        temp.set(Calendar.DAY_OF_MONTH, 1);  //设置日期
        int currentNum=this.publisherNum+this.workerNum;
        for(int i=12;i>0;i--) {
            customerNum[i][0] = (temp.get(Calendar.MONTH) + 1) + "月";
            int monthNum = adminService.registerNum(temp);
            customerNum[i][1] = Integer.toString(monthNum);
            customerNum[i][2] = Integer.toString(currentNum);
            currentNum -= monthNum;
            temp.add(Calendar.MONTH, -1);
        }
    }

    private void toArray(AdminService adminService,List<Worker> list,String[][] top100,int length){
        for(int i=0;i<length;i++){
            Worker worker=list.get(i);
            top100[i][0]=Integer.toString(i);
            top100[i][1]=worker.getName();
            top100[i][2]=worker.getStringAbilities();
            top100[i][3]=Double.toString(worker.getAccuracy());
            top100[i][4]=Integer.toString(adminService.workInProjectNum(worker.getId()));
            top100[i][5]=Integer.toString(adminService.workInProjectNum(worker.getId()));
        }
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
