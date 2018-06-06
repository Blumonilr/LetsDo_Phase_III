package YingYingMonster.LetsDo_Phase_III.daoImpl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import YingYingMonster.LetsDo_Phase_III.dao.MockDB;
import YingYingMonster.LetsDo_Phase_III.dao.UserDAO;
import YingYingMonster.LetsDo_Phase_III.model.Persistent;
import YingYingMonster.LetsDo_Phase_III.model.Publisher;
import YingYingMonster.LetsDo_Phase_III.model.User;
import YingYingMonster.LetsDo_Phase_III.model.Worker;

@Component
public class UserDAOImpl implements UserDAO {

	@Autowired
	MockDB db;
	
	@Autowired String root;
	
	@Override
	public boolean register(User user) throws FileNotFoundException, IOException {
		// TODO Auto-generated method stub
		if(user instanceof Worker){
			File file=new File(root+"/workers/"+user.getId());
			file.mkdirs();
		}
		if(user instanceof Publisher){
			File file=new File(root+"/publishers/"+user.getId());
			file.mkdirs();
		}
		return db.insert("users", user);
	}

	@Override
	public User login(String id, String pw) throws FileNotFoundException, ClassNotFoundException, IOException {
		// TODO Auto-generated method stub
		User user=(User) db.retrieve("users", id);
		if(user!=null&&user.validatePw(pw)){
			return user;
		}else
			return null;
	}

	@Override
	public User modify(User user) throws FileNotFoundException, IOException {
		// TODO Auto-generated method stub
		return db.modify("users", user)?user:null;
	}

	@Override
	public User getUser(String id) throws FileNotFoundException, ClassNotFoundException, IOException {
		// TODO Auto-generated method stub
		return (User) db.retrieve("users", id);
	}

	@Override
	public List<User> findUsers(String name) throws FileNotFoundException, ClassNotFoundException, IOException {
		// TODO Auto-generated method stub
		List<Persistent>list=db.readTable("users");
		if(list==null)
			return null;
		
		List<User>res=new ArrayList<>();
		for(Persistent p:list){
			if(name==null||((User)p).getName().contains(name))
				res.add((User) p);
		}
		return res;
	}

	@Override
	public User financeTransaction(String userId, long money) throws FileNotFoundException, 
		IOException, ClassNotFoundException{
		// TODO Auto-generated method stub
		User user=(User) db.retrieve("users", userId);
		if(user==null)
			return null;
		
		user.setMoney(user.getMoney()+money);
		db.modify("users", user);
		return user;
	}

	@Override
	public int getActiveUserNum() {
		return 10;
	}

}
