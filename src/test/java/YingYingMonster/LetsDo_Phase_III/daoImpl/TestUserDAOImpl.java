package YingYingMonster.LetsDo_Phase_III.daoImpl;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import YingYingMonster.LetsDo_Phase_III.dao.MockDB;
import YingYingMonster.LetsDo_Phase_III.dao.UserDAO;
import YingYingMonster.LetsDo_Phase_III.model.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestUserDAOImpl {

	@Autowired
	UserDAO userDao;
	
	@Autowired
	MockDB db;
	
	public void setUp() throws Exception {
		tearDown();
		Publisher pub=new Publisher();
		pub.setId("publisher1");
		pub.setPw("pw1");
		pub.setName("name1");
		pub.setMoney(1000);				
		userDao.register(pub);
	}

	public void tearDown() throws Exception {
		List<Persistent>list=db.readTable("users");
		for(Persistent t:list)
			db.delete("users", t);
	}

//	@Test
	public void register() throws Exception {
		setUp();
		
		Publisher pub=new Publisher();
		pub.setId("publisher1");
		pub.setPw("pw1");
		pub.setName("name1");
		pub.setMoney(1000);
		assertEquals(false,userDao.register(pub));

		Worker wor=new Worker();
		wor.setId("worker1");
		wor.setPw("pw2");
		wor.setName("name2");
		wor.setMoney(28);
		assertEquals(true,userDao.register(wor));

//		Administrator ad=new Administrator();
//		ad.setId("admin");
//		ad.setPw("742623624");
//		ad.setName("管理员");
//		assertEquals(true,mockDB.insert("users",ad));

		tearDown();
	}
	
//	@Test
	public void login() throws Exception{
		setUp();
		User user1=userDao.login("publisher1", "pw1");
		assertNotNull(user1);
		assertEquals("name1",user1.getName());
		
		User user2=userDao.login("publisher1", "pw2");
		assertNull(user2);

		tearDown();
	}

//	@Test
	public void modify() throws Exception{
		setUp();
		User user1=userDao.login("publisher1", "pw1");
		assertEquals("name1",user1.getName());
		
		user1.setName("name2");
		
		User user2=userDao.modify(user1);
		assertEquals("name2",user2.getName());
		tearDown();
	}
	
//	@Test
	public void findUsers() throws Exception{
		setUp();
		
		List<User>list=userDao.findUsers(null);
		assertEquals("name1",list.get(0).getName());
		assertEquals(true,list.get(0)instanceof Publisher);
		assertEquals(false,list.get(0)instanceof Worker);
		tearDown();
	}

	@Test
	public void food(){
		Administrator administrator=new Administrator();
		administrator.setId("admin");
		administrator.setPw("admin");
		try {
			userDao.register(administrator);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
