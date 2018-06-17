package YingYingMonster.LetsDo_Phase_III.entity.json;

import java.util.List;

public class TagTreeNode {
    private String name;
    private List<TagTreeNode> children;
    private String parentTId;
    private String tId;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TagTreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<TagTreeNode> children) {
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
