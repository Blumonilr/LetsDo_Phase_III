package YingYingMonster.LetsDo_Phase_III.entity.role;

import YingYingMonster.LetsDo_Phase_III.entity.role.User;
import com.google.gson.annotations.Expose;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("WK")
public class Worker extends User {

	@Expose
	private int level,exp;
	@Expose
	private static final int GAP=100;
	@Expose
	private int tagNum,passedTagNum;

	public Worker() {
		super();
	}

	public Worker(String name, String pw, String email, String intro, long money,
				  int level, int exp, int tagNum, int passedTagNum) {

		super(name, pw, email, intro, money);
		this.level = level;
		this.exp = exp;
		this.tagNum = tagNum;
		this.passedTagNum = passedTagNum;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getExp() {
		return exp;
	}

	public void setExp(int exp) {
		this.exp = exp;
	}

	public static int getGAP() {
		return GAP;
	}

	public int getTagNum() {
		return tagNum;
	}

	public void setTagNum(int tagNum) {
		this.tagNum = tagNum;
	}

	public int getPassedTagNum() {
		return passedTagNum;
	}

	public void setPassedTagNum(int passedTagNum) {
		this.passedTagNum = passedTagNum;
	}
}
