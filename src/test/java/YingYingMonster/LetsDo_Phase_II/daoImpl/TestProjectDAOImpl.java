package YingYingMonster.LetsDo_Phase_II.daoImpl;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import YingYingMonster.LetsDo_Phase_III.dao.ProjectDAO;
import YingYingMonster.LetsDo_Phase_III.model.Project;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestProjectDAOImpl {

	@Autowired
	ProjectDAO pjDao;
	
	public void setUp() throws FileNotFoundException, IOException{
		Project p1=new Project("pubid","pjid",100,2,6,"2018-04-14","2018-04-15",null,null,100);
		Project p2=new Project("publisher2","project2",100,2,6,"2018-04-14","2018-04-15",null,null,100);
		pjDao.addProject(p1);
		pjDao.addProject(p2);

	}
	
	public void tearDown() throws FileNotFoundException, ClassNotFoundException, IOException {
		pjDao.viewAllProjects().stream().forEach(p->{
			String pubid=p.getPublisherId();
			String pid=p.getProjectId();
			try {
				pjDao.deleteProject(pubid, pid);
			} catch (ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}
	
	@Test
	public void test1() throws FileNotFoundException, IOException{
		setUp();
	}
	
//	@Test
	public void addProject() throws FileNotFoundException, IOException, ClassNotFoundException {
		setUp();
		pjDao.deleteProject("111", "test1");
		Project p1=new Project("111","test1",100,10,100,"2018-04-11","2018-04-20",null,null,100);
		assertEquals(true,pjDao.addProject(p1));
		
		pjDao.deleteProject("113", "test3");
		Project p2=new Project("113","test3",100,10,100,"2018-05-10","2018-05-20",null,null,100);
		assertEquals(true,pjDao.addProject(p2));
		assertEquals(false,pjDao.addProject(p2));
		pjDao.deleteProject("112", "test2");
		Project p3=new Project("112","test2",100,10,100,"2018-04-11","2018-04-30",null,null,100);
		pjDao.addProject(p3);
		//tearDown();
	}
	
//	@Test
	public void validateProject() throws FileNotFoundException, ClassNotFoundException, IOException{
		setUp();
		assertEquals(false,pjDao.validateProject("pubid", "pid"));
		assertEquals(true,pjDao.validateProject("pubid", "pid1"));
		assertEquals(true,pjDao.validateProject("pubid1", "pid"));
		tearDown();
	}

//	@Test
	public void publisherViewProjects() throws FileNotFoundException, ClassNotFoundException, IOException{
		setUp();
		List<Project>list=pjDao.publisherViewProjects("pubid");
		assertEquals("pid",list.get(0).getProjectId());
		
		List<Project>list2=pjDao.publisherViewProjects("kate");
		assertEquals(0,list2.size());
		tearDown();
	}
	
//	@Test
	public void getAProject() throws FileNotFoundException, ClassNotFoundException, IOException{
		setUp();
		Project pj1=pjDao.getAProject("pubid","pid");
		Project pj2=pjDao.getAProject("pubid","pid2");
		
		assertEquals(100,pj1.getMaxWorkerNum());
		assertNull(pj2);
		tearDown();
	}
	
//	@Test
	public void modifyCurrentWorkerNum() throws FileNotFoundException, ClassNotFoundException, IOException{
		setUp();
		Project pj1=pjDao.getAProject("pubid","pid");
		assertEquals(0,pj1.getCurrWorkerNum());
		
		assertEquals(true,pjDao.modifyCurrentWorkerNum("pubid", "pid"));
		Project pj2=pjDao.getAProject("pubid","pid");
		assertEquals(1,pj2.getCurrWorkerNum());
		
		assertEquals(false,pjDao.modifyCurrentWorkerNum("pubid1", "pid"));
		
		tearDown();
	}
}
