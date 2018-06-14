package YingYingMonster.LetsDo_Phase_III.model;

import java.util.List;

public class JsonOb {
    private String name;
    private List<JsonOb> children;
    private String parentTId;
    private String tId;


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

    public String getParentTId() {
        return parentTId;
    }

    public void setParentTId(String parentTId) {
        this.parentTId = parentTId;
    }

    public String gettId() {
        return tId;
    }

    public void settId(String tId) {
        this.tId = tId;
    }
}
