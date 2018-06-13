package YingYingMonster.LetsDo_Phase_III.entity;

import javax.persistence.*;

@Entity
@Table(name="labels")
//@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public class Label {
    @Id @GeneratedValue
    private long id;

    private String name;

    public Label(String name) {
        this.name = name;
    }

    public Label() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }
}
