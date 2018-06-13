package YingYingMonster.LetsDo_Phase_III.RepositoryTest;

import YingYingMonster.LetsDo_Phase_III.entity.Label;
import YingYingMonster.LetsDo_Phase_III.entity.Project;
import YingYingMonster.LetsDo_Phase_III.repository.LabelRepository;
import YingYingMonster.LetsDo_Phase_III.repository.ProjectRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static YingYingMonster.LetsDo_Phase_III.model.MarkMode.SQUARE;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestProjectRepository {
    @Autowired
    ProjectRepository pr;
    @Autowired
    LabelRepository lr;

    @Test
    public void test1(){
        Label l1=lr.findByName("动物").get(0);
        Label l2=lr.findByName("植物").get(0);
        List<String> list=new ArrayList<>();
        list.add(l1.getName());list.add(l2.getName());
        Project p1=new Project(SQUARE,1,"test1",0,10,"2018-07-07","null",0,0.8,10,list);
        pr.saveAndFlush(p1);
        Project p2=new Project(SQUARE,1,"test1",0,10,"2018-07-08","null",0,0.8,10,list);
        pr.saveAndFlush(p2);
        Project p3=new Project(SQUARE,3,"test1",0,10,"2018-06-09","null",0,0.8,10,list);
        pr.saveAndFlush(p3);
    }

    @Test
    public void test2(){
        System.out.println(pr.findById(18).getId());
//        System.out.println(pr.findByProjectId("test1"));
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
    public void test5(){
        Project p=pr.findById(18);
        List<String> list=p.getLabels();
        for(String pl:list)
            System.out.println(pl);
    }

    @Test
    public void test6(){
        Project p=pr.findById(19);
        Label l1=lr.findById(9);
        p.addLabel(l1);
        pr.saveAndFlush(p);
    }

    @Test
    public void testover(){
        pr.deleteAll();
    }
}
