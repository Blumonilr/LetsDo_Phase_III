package YingYingMonster.LetsDo_Phase_III.entity;

import YingYingMonster.LetsDo_Phase_III.model.MarkMode;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tags")
public class Tag {
    @Id @GeneratedValue
    private long id;

    private long imageId;

    private long projectId;

    private byte[] data;//图片

    private String xmlFile;//xml文档

    public Tag(long imageId, long projectId, byte[] data, String xmlFile) {
        this.imageId = imageId;
        this.projectId = projectId;
        this.data = data;
        this.xmlFile = xmlFile;
    }

    public long getProjectId() {

        return projectId;
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    public long getId() {
        return id;
    }

    public long getImageId() {

        return imageId;
    }

    public void setImageId(long imageId) {
        this.imageId = imageId;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public String getXmlFile() {
        return xmlFile;
    }

    public void setXmlFile(String xmlFile) {
        this.xmlFile = xmlFile;
    }

    public Tag() {

    }

    //    MarkMode type;

}
