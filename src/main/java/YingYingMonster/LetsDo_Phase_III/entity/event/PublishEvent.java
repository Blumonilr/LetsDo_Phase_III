package YingYingMonster.LetsDo_Phase_III.entity.event;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "publish_events")
public class PublishEvent {

    @Id
    @GeneratedValue
    private long id;

    private long publisherId;
    private long projectId;
    private Date date;
    private String inviteCode;

    public long getId() {
        return id;
    }

    public long getPublisherId() {

        return publisherId;
    }

    public void setPublisherId(long publisherId) {
        this.publisherId = publisherId;
    }

    public long getProjectId() {
        return projectId;
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public PublishEvent() {

    }

    public PublishEvent(long publisherId, long projectId, Date date, String inviteCode) {

        this.publisherId = publisherId;
        this.projectId = projectId;
        this.date = date;
        this.inviteCode = inviteCode;
    }
}
