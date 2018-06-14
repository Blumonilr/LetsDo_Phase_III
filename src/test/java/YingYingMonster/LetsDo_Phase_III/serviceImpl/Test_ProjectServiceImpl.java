package YingYingMonster.LetsDo_Phase_III.serviceImpl;

import YingYingMonster.LetsDo_Phase_III.entity.Image;
import YingYingMonster.LetsDo_Phase_III.repository.ImageRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
public class Test_ProjectServiceImpl {
    @Autowired
    ProjectServiceImpl projectService;
    @Autowired
    ImageRepository imageRepository;
    @Autowired
    PublisherServiceImpl publisherService;

    @Test public void test_addOverview(){
//        System.out.println(System.getProperty("user.dir"));

        Image image = imageRepository.findAll().get(0);
//        publisherService.saveProjectOverview(90, image);
        String path = "src/main/resources/static/images/projectOverview/pj" + 90 + ".jpg";
        File file = new File(path);
        assertEquals(true, file.exists());
    }

    @Test
    public void testInitialTree(){
        System.out.println(projectService.getInitialTextNodeTree());
    }


}
