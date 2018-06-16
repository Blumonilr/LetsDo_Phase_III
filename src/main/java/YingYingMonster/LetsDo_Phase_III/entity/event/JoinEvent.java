package YingYingMonster.LetsDo_Phase_III.entity.event;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "join_events")
public class JoinEvent {

    public static final String TESTFINISHED="test finished",TESTNOTFINISHED="test not finished"
            ,TESTPASSED="test passed",TESTNOTPASSED="test not passed",WORKING="working";

    @Id @GeneratedValue private long id;

    private long workerId,projectId;

    private Date date;

    private boolean active;//工人是否还在参与项目

    private String workState=TESTNOTFINISHED;

    private double testScore;

    public JoinEvent() {
    }

    public JoinEvent(long workerId, long projectId, Date date, boolean active) {
        this.workerId = workerId;
        this.projectId = projectId;
        this.date = date;
        this.active = active;
    }

    public double getTestScore() {
        return testScore;
    }

    public void setTestScore(double testScore) {
        this.testScore = testScore;
    }

    public boolean isActive() {
        return active;
    }

    public String getWorkState() {
        return workState;
    }

    public void setWorkState(String workState) {
        this.workState = workState;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getId() {

        return id;
    }

    public long getWorkerId() {

        return workerId;
    }

    public void setWorkerId(long workerId) {
        this.workerId = workerId;
    }

    public long getProjectId() {
        return projectId;
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }
}
