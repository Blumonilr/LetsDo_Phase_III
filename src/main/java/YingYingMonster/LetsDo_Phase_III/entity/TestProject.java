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

    public TestProject() {
    }

    public TestProject(MarkMode markMode, int picNum) {

        this.markMode = markMode;
        this.picNum = picNum;
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
