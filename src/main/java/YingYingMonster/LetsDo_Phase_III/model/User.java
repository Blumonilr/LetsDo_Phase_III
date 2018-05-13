package YingYingMonster.LetsDo_Phase_III.model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public abstract class User implements Persistent{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1225535721206014632L;
	private String id,name,pw,email,intro;//账号，昵称，密码，邮箱，简介
	private long money;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * 只能检验密码是否正确，不能查看密码
	 * @param pw
	 * @return
	 */
	public boolean validatePw(String pw){
		MessageDigest md=null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		byte[]bytes=md.digest(pw.getBytes());
		String input=new String(bytes);
		
		
		return input.equals(this.pw);
	}
	public void setPw(String pw) {
		//md5 encode
		try {
			MessageDigest md=MessageDigest.getInstance("MD5");
			byte[]bytes=md.digest(pw.getBytes());
			this.pw=new String(bytes);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	
	
}
