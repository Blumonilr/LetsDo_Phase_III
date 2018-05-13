package YingYingMonster.LetsDo_Phase_II.serviceImpl;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import YingYingMonster.LetsDo_Phase_III.dao.MockDB;
import YingYingMonster.LetsDo_Phase_III.dao.UserDAO;
import YingYingMonster.LetsDo_Phase_III.daoImpl.UserDAOImpl;
import YingYingMonster.LetsDo_Phase_III.model.Persistent;
import YingYingMonster.LetsDo_Phase_III.model.User;
import YingYingMonster.LetsDo_Phase_III.model.Worker;
import YingYingMonster.LetsDo_Phase_III.service.RankService;
import YingYingMonster.LetsDo_Phase_III.service.UserService;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestRankServiceImpl {

	@Autowired
	UserService usrsv;
	
	@Autowired
	MockDB db;
	
	@Autowired
	RankService rksv;
	
	@Before
	public void setUp() throws FileNotFoundException, ClassNotFoundException, IOException{
		tearDown();
		Worker w1=new Worker("id1", "name1", "pw1", null, null, 0, 1, 100, 0, 0);
		Worker w2=new Worker("id2", "name2", "pw2", null, null, 0, 1, 123, 0, 0);
		Worker w3=new Worker("id3", "name3", "pw3", null, null, 0, 0, 100, 0, 0);
		
		usrsv.register(w1);
		usrsv.register(w2);
		usrsv.register(w3);
		System.out.println("set up");
	}
	
	@After
	public void tearDown() throws FileNotFoundException, ClassNotFoundException, IOException{
		List<Persistent>list=db.readTable("users");
		for(Persistent t:list)
			db.delete("users", t);
		
		System.out.println("tear down");
	}
	
	@Test
	public void rankByExp() throws FileNotFoundException, ClassNotFoundException, IOException {
		rksv.rankByExp().stream().forEach(x->System.out.println(x.getId()));
		
		List<User>list=rksv.rankByExp();
		for(User u:list)
			System.out.println(u.getId());
	}

}
