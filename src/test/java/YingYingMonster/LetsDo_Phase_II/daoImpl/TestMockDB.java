package YingYingMonster.LetsDo_Phase_II.daoImpl;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import YingYingMonster.LetsDo_Phase_III.daoImpl.MockDBImpl;
import YingYingMonster.LetsDo_Phase_III.daoImpl.MockTable;
import YingYingMonster.LetsDo_Phase_III.model.User;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestMockDB {

	@Autowired
	MockDBImpl db;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

//	@Test
//	public void test() throws IOException {
//		User user=new User();
//		user.name="testName";
//		user.pw="testPw";
//		
//		assertEquals(true,db.insert("users", user));
//	}
	
	@Test
	public void testTables(){
		HashMap<String,MockTable>tables=db.getTables();
		Set<String>keys=tables.keySet();
		for(String str:keys)
			System.out.println(tables.get(str).path);
	}

}
