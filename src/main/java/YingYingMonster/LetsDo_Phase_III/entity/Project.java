package YingYingMonster.LetsDo_Phase_III.entity;

import YingYingMonster.LetsDo_Phase_III.model.MarkMode;
import YingYingMonster.LetsDo_Phase_III.model.ProjectState;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="projects")
public class Project {
    @Id
    @GeneratedValue
    private long id;

    private MarkMode type;

    @Enumerated(EnumType.STRING)
    private ProjectState projectState;

    private long publisherId;

    private String projectName;//发布者id，项目id

    private int currWorkerNum,picNum;//当前人数，图片数

    private int maxNumPerPic,minNumPerPic;

    private String startDate,endDate;//yyyy-MM-dd

    @Column(length=10*1024)
    private String tagRequirement;//改成String

    private int workerMinLevel;//worker最低等级

    private double testAccuracy;//测试通过的准确率

    private int money;//任务赏金

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name="label_id")
    List<ProjectLabel> labels;

    @OneToOne
    @JoinColumn(name = "testProject_id")
    private TestProject testProject;

    public Project(MarkMode type, long publisherId, String projectName,
                   int maxNumPerPic, int minNumPerPic, String endDate, String tagRequirement,
                   int workerMinLevel, double testAccuracy, int money,List<ProjectLabel> labels) {
        this.type = type;
        this.publisherId = publisherId;
        this.projectName = projectName;
        this.maxNumPerPic = maxNumPerPic;
        this.minNumPerPic = minNumPerPic;
        this.endDate = endDate;
        this.tagRequirement = tagRequirement;
        this.workerMinLevel = workerMinLevel;
        this.testAccuracy = testAccuracy;
        this.money = money;
        this.currWorkerNum=0;
        this.labels=labels;
    }

    public ProjectState getProjectState() {
        return projectState;
    }

    public void setProjectState(ProjectState projectState) {
        this.projectState = projectState;
    }

    public int getWorkerMinLevel() {
        return workerMinLevel;
    }

    public void setWorkerMinLevel(int workerMinLevel) {
        this.workerMinLevel = workerMinLevel;
    }

    public double getTestAccuracy() {
        return testAccuracy;
    }

    public void setTestAccuracy(double testAccuracy) {
        this.testAccuracy = testAccuracy;
    }



    public Project() {
    }

    public TestProject getTestProject() {
        return testProject;
    }

    public void setTestProject(TestProject testProject) {
        this.testProject = testProject;
    }

    public long getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(long publisherId) {
        this.publisherId = publisherId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
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
        return String.format("Project[id=%d , projectId=%s , publisherId=%d]", id,projectName,publisherId);
    }

    public List<ProjectLabel> getLabels() {
        return labels;
    }

    public void setLabels(List<ProjectLabel> labels) {
        this.labels = labels;
    }
}
