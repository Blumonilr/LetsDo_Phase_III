package YingYingMonster.LetsDo_Phase_III.entity;

import javax.persistence.*;
import java.util.List;
import java.util.Map;

@Entity
@Table(name="textnodes")
public class TextNode {

    @Id
    private String name;
    private String father;
    private boolean isLeaf;

    @ElementCollection(targetClass = String.class,fetch = FetchType.EAGER)
    private List<String> attributions;//format:name:value1_value2_value3...

    public TextNode(String name, String father, boolean isLeaf,List<String> attributions) {
        this.name = name;
        this.father = father;
        this.isLeaf = isLeaf;
        this.attributions = attributions;
    }


    public TextNode() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFather() {
        return father;
    }

    public void setFather(String father) {
        this.father = father;
    }

    public boolean isLeaf() {
        return isLeaf;
    }

    public void setLeaf(boolean leaf) {
        isLeaf = leaf;
    }

    public List<String> getAttributions() {
        return attributions;
    }

    public void setAttributions(List<String> attributions) {
        this.attributions = attributions;
    }

    public void addAttribution(String attributionName,String attribution){
        attributions.add(attributionName+":"+attribution);
    }

    public String toString(){
        return String.format("TextNode[name=%s , father=%s , attributions=%s]",name,father,attributions.toString());
    }
}
