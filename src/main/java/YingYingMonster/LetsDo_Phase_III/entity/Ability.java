package YingYingMonster.LetsDo_Phase_III.entity;

import YingYingMonster.LetsDo_Phase_III.entity.role.User;

import javax.persistence.*;

@Entity
@Table(name = "abilities")
public class Ability {
    @Id
    @GeneratedValue
    private long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "label_id")
    private Label label;

    private double accuracy;

    private int bias;//偏好程度，以参加项目的次数计算

    public long getId() {
        return id;
    }

    public User getUser() {

        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    public double getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(double accuracy) {
        this.accuracy = accuracy;
    }

    public int getBias() {
        return bias;
    }

    public void setBias(int bias) {
        this.bias = bias;
    }

    public Ability() {
        bias = 1;
    }

    public Ability(User user, Label label) {
        bias = 1;
        this.user = user;
        this.label = label;
    }
}
