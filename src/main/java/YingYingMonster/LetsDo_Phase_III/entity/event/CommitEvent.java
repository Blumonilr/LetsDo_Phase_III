package YingYingMonster.LetsDo_Phase_III.entity.event;

import YingYingMonster.LetsDo_Phase_III.model.CommitResult;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "commits")
public class CommitEvent {

    //表示提交的状态：审核中，通过，被拒绝
    public static final String EVALUATING="evaluating",PASSED="passed", REJECTED = "rejected";

    @Id @GeneratedValue private long id;

    private long workerid;
    private long projectid;
    private long tagid;
    private long imageid;
    private Date commitTime;
    private double accuracy;
    private String commitMsg = EVALUATING;
    private double efficiency;

    public long getImageid() {
        return imageid;
    }

    public void setImageid(long imageid) {
        this.imageid = imageid;
    }

    public CommitEvent() {
    }

    public long getId() {

        return id;
    }

    public long getWorkerid() {

        return workerid;
    }

    public void setWorkerid(long workerid) {
        this.workerid = workerid;
    }

    public long getProjectid() {
        return projectid;
    }

    public void setProjectid(long projectid) {
        this.projectid = projectid;
    }

    public long getTagid() {
        return tagid;
    }

    public void setTagid(long tagid) {
        this.tagid = tagid;
    }

    public Date getCommitTime() {
        return commitTime;
    }

    public void setCommitTime(Date commitTime) {
        this.commitTime = commitTime;
    }

    public CommitEvent(long workerid, long projectid, long tagid, long imageid, Date commitTime) {
        this.workerid = workerid;
        this.projectid = projectid;
        this.tagid = tagid;
        this.imageid = imageid;
        this.commitTime = commitTime;
    }

    public double getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(double accuracy) {
        this.accuracy = accuracy;
    }

    public String getCommitMsg() {
        return commitMsg;
    }

    public void setCommitMsg(String commitMsg) {
        this.commitMsg = commitMsg;
    }

    public double getEffeciency() {
        return efficiency;
    }

    public void setEffeciency(double efficiency) {
        this.efficiency = efficiency;
    }
}
