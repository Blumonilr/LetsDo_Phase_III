package YingYingMonster.LetsDo_Phase_III.model;

import java.util.ArrayList;
import java.util.List;

public class Worker extends User {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2468174652439727470L;
	private int level,exp;//等级，经验值，	
	private final int gap=100;//升级所需经验
	private int tagNum,passedTagNum;//完成的tag总数，合格的tag总数
	private List<String>finishedPj,unfinishedPj;
	
	
	public List<String> getFinishedPj() {
		return finishedPj;
	}
	public void setFinishedPj(List<String> finishedPj) {
		this.finishedPj = finishedPj;
	}
	public List<String> getUnfinishedPj() {
		return unfinishedPj;
	}
	public void setUnfinishedPj(List<String> unfinishedPj) {
		this.unfinishedPj = unfinishedPj;
	}
	public Worker() {
		super();
		finishedPj=new ArrayList<String>();
		unfinishedPj=new ArrayList<String>();
	}
	public Worker(String id,String name,String pw,String email,String intro,long money,int level, int exp, int tagNum, int passedTagNum) {
		super();
		this.setId(id);
		this.setName(name);
		this.setPw(pw);
		this.setEmail(email);
		this.setIntro(intro);
		this.setMoney(money);
		this.level = level;
		this.exp = exp;
		this.tagNum = tagNum;
		this.passedTagNum = passedTagNum;
		finishedPj=new ArrayList<String>();
		unfinishedPj=new ArrayList<String>();
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
	public int getGap() {
		return gap;
	}
	@Override
	public String getKey() {
		// TODO Auto-generated method stub
		return getId();
	}

	public double getAccuracy(){
		return tagNum==0?0.0:(passedTagNum+0.0)/tagNum;
	}
	
}
