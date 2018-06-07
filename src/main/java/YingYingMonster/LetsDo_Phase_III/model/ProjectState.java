package YingYingMonster.LetsDo_Phase_III.model;

import java.security.PrivateKey;

public enum ProjectState {
    setup("setup"), initialize("initialize"), open("open"), closed("closed");

    private String val;

    ProjectState(String val) {
        this.val = val;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    public boolean equals(ProjectState projectState) {
        return this.val.equals(projectState.val);
    }
}
