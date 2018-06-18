package YingYingMonster.LetsDo_Phase_III.serviceImpl;

import YingYingMonster.LetsDo_Phase_III.entity.Image;
import YingYingMonster.LetsDo_Phase_III.entity.TestProject;
import YingYingMonster.LetsDo_Phase_III.repository.TestProjectRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestTestProjectServiceImpl {
    @Autowired
    TestProjectServiceImpl testProjectService;
    @Autowired
    TestProjectRepository testProjectRepository;

    @Test public void test_getAPage(){
        TestProject testProject = testProjectRepository.findById((long) 2);
        //       List<Image> list = testProjectService.getAPageOfImages(testProject.getProject().getId());
//        assertEquals(3, list.size());
//        for (Image image : list) {
 //           System.out.println(image.getId());
      //  }
    }


}
