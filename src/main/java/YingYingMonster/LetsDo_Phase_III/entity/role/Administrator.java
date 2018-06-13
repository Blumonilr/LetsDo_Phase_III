package YingYingMonster.LetsDo_Phase_III.entity.role;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("AD")
public class Administrator extends User {
    public Administrator() {
    }

    public Administrator(String name, String pw, String email, String intro, long money) {

        super(name, pw, email, intro, money);
    }
}
