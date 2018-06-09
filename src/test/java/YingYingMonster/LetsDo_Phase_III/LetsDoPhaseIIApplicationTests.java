package YingYingMonster.LetsDo_Phase_III;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LetsDoPhaseIIApplicationTests {

	@Test
	public void contextLoads() {
		Initializer ini=new Initializer();
		ini.initTextNodeTree(new File("TextNodeTree"));
	}

}
