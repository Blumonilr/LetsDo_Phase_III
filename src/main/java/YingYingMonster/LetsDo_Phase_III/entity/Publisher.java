package YingYingMonster.LetsDo_Phase_III.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("PUB")
public class Publisher extends User {
    public Publisher() {
    }

    public Publisher(String name, String pw, String email, String intro, long money) {

        super(name, pw, email, intro, money);
    }
}
