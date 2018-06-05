package YingYingMonster.LetsDo_Phase_III.entity;

import YingYingMonster.LetsDo_Phase_III.model.MarkMode;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="projects")
public class Project {
    @Id
    @GeneratedValue
    private long id;

    private MarkMode type;

    private long publisherId;
    private String projectId;//发布者id，项目id

    private int currWorkerNum,picNum;//当前人数，图片数

    private int maxNumPerPic,minNumPerPic;

    private String startDate,endDate;//yyyy-MM-dd

    private String tagRequirement;//改成String
    private String workerRequirement;//改成String

    private int money;//任务赏金

    public Project(long publisherId, String projectId, int currWorkerNum, int picNum, int maxNumPerPic, int minNumPerPic, String startDate, String endDate, String tagRequirement, String workerRequirement, int money,MarkMode type) {
        this.publisherId = publisherId;
        this.projectId = projectId;
        this.currWorkerNum = currWorkerNum;
        this.picNum = picNum;
        this.maxNumPerPic = maxNumPerPic;
        this.minNumPerPic = minNumPerPic;
        this.startDate = startDate;
        this.endDate = endDate;
        this.tagRequirement = tagRequirement;
        this.workerRequirement = workerRequirement;
        this.money = money;
        this.type=type;
    }

    public Project() {
    }

    public long getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(long publisherId) {
        this.publisherId = publisherId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public int getCurrWorkerNum() {
        return currWorkerNum;
    }

    public void setCurrWorkerNum(int currWorkerNum) {
        this.currWorkerNum = currWorkerNum;
    }

    public int getPicNum() {
        return picNum;
    }

    public void setPicNum(int picNum) {
        this.picNum = picNum;
    }

    public int getMaxNumPerPic() {
        return maxNumPerPic;
    }

    public void setMaxNumPerPic(int maxNumPerPic) {
        this.maxNumPerPic = maxNumPerPic;
    }

    public int getMinNumPerPic() {
        return minNumPerPic;
    }

    public void setMinNumPerPic(int minNumPerPic) {
        this.minNumPerPic = minNumPerPic;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getTagRequirement() {
        return tagRequirement;
    }

    public void setTagRequirement(String tagRequirement) {
        this.tagRequirement = tagRequirement;
    }

    public String getWorkerRequirement() {
        return workerRequirement;
    }

    public void setWorkerRequirement(String workerRequirement) {
        this.workerRequirement = workerRequirement;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public long getId() {
        return id;
    }

    public MarkMode getType() {
        return type;
    }

    public void setType(MarkMode type) {
        this.type = type;
    }

    public String toString(){
        return String.format("Project[id=%d , projectId=%s , publisherId=%d]", id,projectId,publisherId);
    }
}
