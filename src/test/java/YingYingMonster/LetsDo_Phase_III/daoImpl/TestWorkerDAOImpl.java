package YingYingMonster.LetsDo_Phase_III.daoImpl;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.test.context.junit4.SpringRunner;

import YingYingMonster.LetsDo_Phase_III.dao.MockDB;
import YingYingMonster.LetsDo_Phase_III.dao.WorkerDAO;
import YingYingMonster.LetsDo_Phase_III.model.Project;
import YingYingMonster.LetsDo_Phase_III.model.Tag;
import YingYingMonster.LetsDo_Phase_III.model.Worker;

@SpringBootTest
@RunWith(SpringRunner.class)
@EnableAspectJAutoProxy
public class TestWorkerDAOImpl {

	@Autowired
	WorkerDAO wkDao;
	
	@Autowired
	MockDB db;
	
	@Test
	public void uploadTag1() throws FileNotFoundException, IOException {
		assertEquals(false,wkDao.uploadTag("wkid", "pbid", "pjid", "tgid", null));
	}
	
	@Test 
	public void project(){
		Project pj1=new Project("pubid", "pjid", 13, 5, 101, null, null, null, null, 0);
		int[]pkgs1=pj1.getPkgs();
		assertEquals(5,pkgs1.length);
		assertEquals(2,pkgs1[0]);
		assertEquals(5,pkgs1[4]);
	}

//	@Test
	public void fork() throws FileNotFoundException, IOException, ClassNotFoundException{
		Project pj1=new Project("pubid", "pjid", 5, 5, 101, null, null, null, null, 0);
		int[]pkgs1=pj1.getPkgs();
		assertEquals(5,pkgs1.length);
		assertEquals(1,pkgs1[0]);
		assertEquals(1,pkgs1[4]);
		db.insert("projects", pj1);
		
		assertEquals(true,wkDao.fork("wk1", "pubid", "pjid"));
		assertEquals(true,wkDao.fork("wk2", "pubid", "pjid"));
		assertEquals(true,wkDao.fork("wk3", "pubid", "pjid"));
		assertEquals(true,wkDao.fork("wk4", "pubid", "pjid"));
		assertEquals(true,wkDao.fork("wk5", "pubid", "pjid"));
		assertEquals(false,wkDao.fork("wk6", "pubid", "pjid"));
		assertEquals(false,wkDao.fork("wk1", "pubid", "pjid"));
		
		db.delete("projects", pj1);
	}
	
//	@Test
	public void viewWorkerProject() throws FileNotFoundException, IOException, ClassNotFoundException{
		Project pj1=new Project("pubid", "pjid", 5, 5, 101, null, null, null, null, 0);
		Project pj2=new Project("pubid1", "pjid", 5, 5, 101, null, null, null, null, 0);
		Project pj3=new Project("pubid", "pjid1", 5, 5, 101, null, null, null, null, 0);
		db.insert("projects", pj1);
		db.insert("projects", pj2);
		db.insert("projects", pj3);
		
		assertEquals(true,wkDao.fork("wk1", "pubid", "pjid"));
		assertEquals(true,wkDao.fork("wk1", "pubid1", "pjid"));
		assertEquals(true,wkDao.fork("wk1", "pubid", "pjid1"));
		
		List<Project>list=wkDao.viewWorkerProject("wk1");
		for(Project p:list)
			System.out.println(p.getKey());
		
		db.delete("projects", pj1);
		db.delete("projects", pj2);
		db.delete("projects", pj3);
	}
	
//	@Test
	public void viewDoneData(){
		List<String>list=wkDao.viewDoneData("wk1", "pubid", "pjid");
		assertEquals(0,list.size());
		for(String str:list)
			System.out.println(str);
	}
	
//	@Test
	public void viewUndoData(){
		List<String>list=wkDao.viewUndoData("wk1", "pubid", "pjid");
		assertEquals(4,list.size());
		for(String str:list)
			System.out.println(str);
	}
	
//	@Test
	public void push() throws FileNotFoundException, ClassNotFoundException, IOException{
		assertEquals(false,wkDao.fork("wk1", "pub", "test"));
		assertEquals(true,wkDao.uploadTag("wk1", "pub", "test", "tag1", new Tag(null, null, null, 0, 0)));
		assertEquals(true,wkDao.uploadTag("wk1", "pub", "test", "tag2", new Tag(null, null, null, 0, 0)));
		assertEquals(true,wkDao.uploadTag("wk1", "pub", "test", "tag3", new Tag(null, null, null, 0, 0)));
		assertEquals(3,wkDao.push("wk1", "pub", "test"));
		Worker wk=(Worker) db.retrieve("users", "wk1");
		assertEquals(0,wk.getUnfinishedPj().size());
		assertEquals("pub_test",wk.getFinishedPj().get(0));
	}
}
