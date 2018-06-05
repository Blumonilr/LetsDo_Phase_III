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

    public Tag(long imageId, byte[] data, String xmlFile) {

        this.imageId = imageId;
        this.data = data;
        this.xmlFile = xmlFile;
    }

    //    MarkMode type;
    private byte[] data;//图片
    private String xmlFile;//xml文档

}
