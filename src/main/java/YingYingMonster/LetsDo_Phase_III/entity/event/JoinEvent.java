package YingYingMonster.LetsDo_Phase_III.entity.event;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "join_events")
public class JoinEvent {

    public static final String TEST_FINISHED="test finished",TEST_NOT_FINISHED="test not finished"
            ,TEST_NOT_PASSED="test not passed",WORKING="working",
            WORK_Finished="work finished";

    @Id @GeneratedValue private long id;

    private long workerId,projectId;

    private Date date;

    private String workState=TEST_NOT_FINISHED;

    private double testScore;

    public JoinEvent() {
    }

    public JoinEvent(long workerId, long projectId, Date date) {
        this.workerId = workerId;
        this.projectId = projectId;
        this.date = date;
    }

    public double getTestScore() {
        return testScore;
    }

    public void setTestScore(double testScore) {
        this.testScore = testScore;
    }

    public String getWorkState() {
        return workState;
    }

    public void setWorkState(String workState) {
        this.workState = workState;
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
