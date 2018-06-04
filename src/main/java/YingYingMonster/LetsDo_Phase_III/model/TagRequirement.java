package YingYingMonster.LetsDo_Phase_III.model;

import java.io.Serializable;

public class TagRequirement implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -1742883425516176928L;
	private MarkMode markMode;
    private String requirement;
    private int gradesLimit;

    public TagRequirement(MarkMode markMode, String requirement, int gradesLimit) {
        this.markMode = markMode;
        this.requirement = requirement;
        this.gradesLimit = gradesLimit;
    }

    public MarkMode getMarkMode() {
        return markMode;
    }

    public void setMarkMode(MarkMode markMode) {
        this.markMode = markMode;
    }

    public String getRequirement() {
        return requirement;
    }

    public void setRequirement(String requirement) {
        this.requirement = requirement;
    }

    public int getGradesLimit() {
        return gradesLimit;
    }

    public void setGradesLimit(int gradesLimit) {
        this.gradesLimit = gradesLimit;
    }
}
