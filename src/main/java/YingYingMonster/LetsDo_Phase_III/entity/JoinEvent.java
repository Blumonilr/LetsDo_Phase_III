package YingYingMonster.LetsDo_Phase_III.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "join_events")
public class JoinEvent {

    @Id @GeneratedValue private long id;

    private long workerId,projectId;

    private Date date;

    private boolean active;//工人是否还在参与项目

    public JoinEvent() {
    }

    public JoinEvent(long workerId, long projectId, Date date, boolean active) {
        this.workerId = workerId;
        this.projectId = projectId;
        this.date = date;
        this.active = active;
    }

    public boolean isActive() {

        return active;
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
