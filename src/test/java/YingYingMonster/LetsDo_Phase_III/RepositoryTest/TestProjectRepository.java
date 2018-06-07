package YingYingMonster.LetsDo_Phase_III.RepositoryTest;

import YingYingMonster.LetsDo_Phase_III.entity.Project;
import YingYingMonster.LetsDo_Phase_III.model.MarkMode;
import YingYingMonster.LetsDo_Phase_III.repository.ProjectRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static YingYingMonster.LetsDo_Phase_III.model.MarkMode.SQUARE;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestProjectRepository {
    @Autowired
    ProjectRepository pr;

    @Test
    public void test1(){
        Project p1=new Project(SQUARE,1,"test1",0,10,"2018-06-04",null,0,0,10);
        pr.save(p1);
        Project p2=new Project(SQUARE,1,"test1",0,10,"2018-06-04",null,0,0,10);
        pr.save(p2);
        Project p3=new Project(SQUARE,1,"test1",0,10,"2018-06-04",null,0,0,10);
        pr.save(p3);
    }

    @Test
    public void test2(){
        System.out.println(pr.findById(18).getId());
        System.out.println(pr.findByProjectName("test1"));
        System.out.println(pr.findByPublisherId(1));
        System.out.println(pr.findByType(SQUARE));
    }

    @Test
    public void test3(){
        System.out.println(pr.findProjectsProcessing("2018-06-05"));
        System.out.println(pr.findProjectsEnded("2018-06-05"));
    }

    @Test
    public void test4(){
        pr.updateCurrWorkerNum(18);
        pr.updateMoney(19,200);
        pr.updatePicNum(19,200);
        System.out.println(pr.findById(18));
        System.out.println(pr.findById(19));
    }

    @Test
    public void testover(){
        pr.deleteAll();
    }
}
