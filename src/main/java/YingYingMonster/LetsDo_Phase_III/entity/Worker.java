package YingYingMonster.LetsDo_Phase_III.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("WK")
public class Worker extends User {

	private int level,exp;
	private static final int GAP=100;
	private int tagNum,passedTagNum;
	
}
