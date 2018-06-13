package YingYingMonster.LetsDo_Phase_III.model;

import java.util.List;

public class JsonOb {
    private String name;
    private List<JsonOb> children;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<JsonOb> getChildren() {
        return children;
    }

    public void setChildren(List<JsonOb> children) {
        this.children = children;
    }
}
