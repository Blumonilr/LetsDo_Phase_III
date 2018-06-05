package YingYingMonster.LetsDo_Phase_III.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "projects")
public class Project {
    @Id @GeneratedValue private long pjid;
    private long pubid;
}
