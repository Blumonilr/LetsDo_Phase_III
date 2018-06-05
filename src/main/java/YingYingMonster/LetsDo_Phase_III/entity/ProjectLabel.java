package YingYingMonster.LetsDo_Phase_III.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("PROL")
public class ProjectLabel extends Label{

    long projectId;

    public ProjectLabel(String name, long projectId) {
        super(name);
        this.projectId = projectId;
    }

    public ProjectLabel() {
    }

    public long getProjectId() {
        return projectId;
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }
}
