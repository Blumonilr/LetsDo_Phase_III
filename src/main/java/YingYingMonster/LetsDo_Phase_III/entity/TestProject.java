package YingYingMonster.LetsDo_Phase_III.entity;

import YingYingMonster.LetsDo_Phase_III.model.MarkMode;

import javax.persistence.*;

@Entity
@Table(name = "test_projects")
public class TestProject {

    @Id @GeneratedValue
    private long id;

    private MarkMode markMode;

    private int picNum;

    @OneToOne(mappedBy = "testProject")
    private Project project;

    private String inviteCode;  //内测邀请码

    public TestProject() {
    }

    public TestProject(MarkMode markMode) {

        this.markMode = markMode;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public long getId() {

        return id;
    }

    public MarkMode getMarkMode() {

        return markMode;
    }

    public void setMarkMode(MarkMode markMode) {
        this.markMode = markMode;
    }

    public int getPicNum() {
        return picNum;
    }

    public void setPicNum(int picNum) {
        this.picNum = picNum;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
