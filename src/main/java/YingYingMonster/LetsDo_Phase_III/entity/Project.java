package YingYingMonster.LetsDo_Phase_III.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="projects")
public class Project {
    @Id
    @GeneratedValue
    public long id;

    private  long publisherId;
    private String projectId;//发布者id，项目id

    private int currWorkerNum,picNum;//当前人数，图片数

    private int maxNumPerPic,minNumPerPic;

    private String startDate,endDate;//yyyy-MM-dd

    private String tagRequirement;//改成String
    private String workerRequirement;//改成String

    private int money;//任务赏金
}
