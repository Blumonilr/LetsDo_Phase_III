package YingYingMonster.LetsDo_Phase_III.entity;

import javax.persistence.*;
import java.util.List;

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
	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name="userId")
	List<UserLabel> labels;

	public User(String name, String pw, String email, String intro, long money,List<UserLabel> labels) {
		super();
		this.name = name;
		this.pw = pw;
		this.email = email;
		this.intro = intro;
		this.money = money;
		this.labels=labels;
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


	public List<UserLabel> getLabels() {
		return labels;
	}

	public void setLabels(List<UserLabel> labels) {
		this.labels = labels;
	}
}
