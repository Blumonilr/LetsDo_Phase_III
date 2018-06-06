package YingYingMonster.LetsDo_Phase_III.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("USRL")
public class UserLabel extends Label{

    long userId;
    double accuracy;
    double efficiency;
    int labelHistory;

    public UserLabel(String name, long userId, double accuracy, double efficiency, int labelHistory) {
        super(name);
        this.userId = userId;
        this.accuracy = accuracy;
        this.efficiency = efficiency;
        this.labelHistory = labelHistory;
    }

    public UserLabel() {
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public double getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(double accuracy) {
        this.accuracy = accuracy;
    }

    public double getEfficiency() {
        return efficiency;
    }

    public void setEfficiency(double efficiency) {
        this.efficiency = efficiency;
    }

    public int getLabelHistory() {
        return labelHistory;
    }

    public void setLabelHistory(int labelHistory) {
        this.labelHistory = labelHistory;
    }
}
