package YingYingMonster.LetsDo_Phase_III.entity.role;

import YingYingMonster.LetsDo_Phase_III.entity.Ability;
import YingYingMonster.LetsDo_Phase_III.entity.event.LogEvent;
import com.google.gson.annotations.Expose;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

	@Id @GeneratedValue @Expose
	private long id;

	@Expose
	private String name,pw,email,intro;
	@Expose
	private long money;

	@Expose
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "user", cascade = CascadeType.ALL)
	private List<Ability> abilities;

	@OneToOne(fetch = FetchType.EAGER,mappedBy = "user",cascade = CascadeType.ALL)
	private LogEvent logEvent;

	@Expose
	private Calendar registerDate;

	public List<Ability> getAbilities() {
		return abilities;
	}

	public String getStringAbilities(){
		ArrayList list=new ArrayList();
		for (Ability a : abilities) {
			list.add(a.getLabel());
		}
		String result=String.join(",",list);
		return result;
	}

	/**\
	 * 返回总准确率
	 * @return
	 */
	public double getAccuracy(){
		double sum=0;
		double accuracy=0;
		for (Ability a : abilities) {
			sum+=a.getLabelHistoryNum();
		}
		for (Ability a : abilities) {
			accuracy+=a.getAccuracy()*a.getLabelHistoryNum()/sum;
		}
		return accuracy;
	}

	public void setAbilities(List<Ability> abilities) {
		this.abilities = abilities;
	}

	public User(String name, String pw, String email, String intro, long money) {

		this.name = name;
		this.pw = pw;
		this.email = email;
		this.intro = intro;
		this.money = money;
		abilities = new ArrayList<>();
		registerDate=Calendar.getInstance();
	}

	public User() {
		super();
		abilities = new ArrayList<>();
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
	public LogEvent getLogEvent() {
		return logEvent;
	}
	public void setLogEvent(LogEvent logEvent) {
		this.logEvent = logEvent;
	}
	public void checkLogin(){
		this.getLogEvent().checkLogin();
	}

	public Calendar getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(Calendar registerDate) {
		this.registerDate = registerDate;
	}
}
