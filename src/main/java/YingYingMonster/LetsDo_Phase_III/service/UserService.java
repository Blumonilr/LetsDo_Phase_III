package YingYingMonster.LetsDo_Phase_III.service;

import java.util.List;

import YingYingMonster.LetsDo_Phase_III.entity.Ability;
import YingYingMonster.LetsDo_Phase_III.entity.Label;
import YingYingMonster.LetsDo_Phase_III.entity.User;

import javax.transaction.Transactional;

public interface UserService {

	@Deprecated
	public boolean userExist(String id);

	public User register(User user);
	
	public User login(long id,String pw);
	
	public User modify(User user);
	
	public User getUser(long id);
	
	/**
	 * 根据用户昵称搜索用户，支持模糊查询
	 * name=null,返回所有用户
	 * @param name
	 * @return
	 */
	public List<User>findUsersByName(String name);
	
	/**
	 * 专门处理金融事务，如充值，付款，提款
	 * 返回修改后的用户对象
	 * @param userId
	 * @param money(signed long)
	 * @return
	 */
	public User financeTransaction(long userId,long money);

	/**
	 * 根据user对象的string类型属性进行模糊查找:name email intro
	 * @param attr
	 * @return
	 */
	public List<User> findByStringAttr(String attr);

	public List<Label> getUserLabels(long userId);

	public List<Ability> getUserAbilities(long userId);

	@Transactional(rollbackOn = Exception.class)
	public void deleteUserById(long id);
	@Transactional(rollbackOn = Exception.class)
	public void deleteUsersById(List<Long> ids);
	@Transactional(rollbackOn = Exception.class)
	public void deleteUserByName(String name);
	@Transactional(rollbackOn = Exception.class)
	public void deleteUsersByName(List<String> names);
	@Transactional(rollbackOn = Exception.class)
	public List<User> addUsersBatch(List<User> users);
}
