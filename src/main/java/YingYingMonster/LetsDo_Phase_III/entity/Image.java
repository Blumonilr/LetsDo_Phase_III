package YingYingMonster.LetsDo_Phase_III.entity;

import javax.persistence.*;

@Entity
@Table(name="images")
public class Image {
    @Id
    @GeneratedValue
    private long id;

    private long projectId;
    @Column(length=20971520)
    private byte[] picture;
    private int minNum,maxNum;
    private int currentNum;
    private boolean isFinished;
    private boolean isTest;

    public Image(long projectId, byte[] picture, int minNum, int maxNum, int currentNum, boolean isFinished, boolean isTest) {
        this.projectId = projectId;
        this.picture = picture;
        this.minNum = minNum;
        this.maxNum = maxNum;
        this.currentNum = currentNum;
        this.isFinished = isFinished;
        this.isTest = isTest;
    }

    public Image() {
    }

    public long getId() {
        return id;
    }

    public long getProjectId() {
        return projectId;
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public int getMinNum() {
        return minNum;
    }

    public void setMinNum(int minNum) {
        this.minNum = minNum;
    }

    public int getMaxNum() {
        return maxNum;
    }

    public void setMaxNum(int maxNum) {
        this.maxNum = maxNum;
    }

    public int getCurrentNum() {
        return currentNum;
    }

    public void setCurrentNum(int currentNum) {
        this.currentNum = currentNum;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    public boolean isTest() {
        return isTest;
    }

    public void setTest(boolean test) {
        isTest = test;
    }

    public String toString(){ return String.format("Image[id=%d , projectId=%s , isFinished=%s, isTest=%s]", id,projectId,isFinished,isTest); }
}
