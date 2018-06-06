package YingYingMonster.LetsDo_Phase_III.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import YingYingMonster.LetsDo_Phase_III.model.User;

public interface UserDAO {

public boolean register(User user) throws FileNotFoundException, IOException;
	
	public User login(String id,String pw) throws FileNotFoundException, ClassNotFoundException, IOException;
	
	public User modify(User user) throws FileNotFoundException, IOException;
	
	public User getUser(String id) throws FileNotFoundException, ClassNotFoundException, IOException;
	
	/**
	 * 根据用户昵称搜索用户，支持模糊查询
	 * @param name
	 * @return
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * @throws FileNotFoundException 
	 */
	public List<User>findUsers(String name) throws FileNotFoundException, ClassNotFoundException, IOException;
	
	/**
	 * 专门处理金融事务，如充值，付款，提款
	 * 返回修改后的用户对象
	 * @param userId
	 * @param money(signed long)
	 * @return
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * @throws FileNotFoundException 
	 */
	public User financeTransaction(String userId,long money) throws FileNotFoundException, 
		ClassNotFoundException, IOException;

	public int getActiveUserNum();
}
