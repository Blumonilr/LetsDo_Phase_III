package YingYingMonster.LetsDo_Phase_III.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
/**
 * worker，publisher，administrater存在同一张表里
 * @author 17678
 *
 */
@Table(name="users")
public abstract class User {

	@Id @GeneratedValue private long id;
	
	private String name,pw,email,intro;
	private long money;
	public User(String name, String pw, String email, String intro, long money) {
		super();
		this.name = name;
		this.pw = pw;
		this.email = email;
		this.intro = intro;
		this.money = money;
	}
	public User() {
		super();
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getIntro() {
		return intro;
	}
	public void setIntro(String intro) {
		this.intro = intro;
	}
	public long getMoney() {
		return money;
	}
	public void setMoney(long money) {
		this.money = money;
	}
	public long getId() {
		return id;
	}
	
	
}
