package YingYingMonster.LetsDo_Phase_III.service;

import java.util.List;

import YingYingMonster.LetsDo_Phase_III.model.User;

public interface UserService {
	
	public boolean userExist(String id);

	public boolean register(User user);
	
	public User login(String id,String pw);
	
	public User modify(User user);
	
	public User getUser(String id);
	
	/**
	 * 根据用户昵称搜索用户，支持模糊查询
	 * name=null,返回所有用户
	 * @param name
	 * @return
	 */
	public List<User>findUsers(String name);
	
	/**
	 * 专门处理金融事务，如充值，付款，提款
	 * 返回修改后的用户对象
	 * @param userId
	 * @param money(signed long)
	 * @return
	 */
	public User financeTransaction(String userId,long money);
}
