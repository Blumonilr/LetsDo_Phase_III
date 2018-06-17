package YingYingMonster.LetsDo_Phase_III.entity;

import com.google.gson.annotations.Expose;

import javax.persistence.*;

@Entity
@Table(name="labels")
//@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public class Label {
    @Id
    @Expose
    private String name;

    public Label(String name) {
        this.name = name;
    }

    public Label() {
    }

    public String getName() {
        return name;
    }


}
