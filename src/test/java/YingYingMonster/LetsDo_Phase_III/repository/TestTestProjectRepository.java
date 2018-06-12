package YingYingMonster.LetsDo_Phase_III.repository;

import YingYingMonster.LetsDo_Phase_III.entity.Project;
import YingYingMonster.LetsDo_Phase_III.entity.TestProject;
import YingYingMonster.LetsDo_Phase_III.model.MarkMode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestTestProjectRepository {
    @Autowired
    TestProjectRepository testProjectRepository;
    @Autowired
    ProjectRepository projectRepository;

    @Test public void test_addAndUpdate(){
        Project project = new Project(MarkMode.AREA, 1, "aaah", 10,
                6, "2018-06-24", "", 0, 0.6,
                10,null);
        long id = projectRepository.saveAndFlush(project).getId();
        TestProject testProject = new TestProject(MarkMode.AREA,0);
        testProject = testProjectRepository.saveAndFlush(testProject);
        projectRepository.updateTestProject(id, testProject);

    }

    @Test public void test_update(){
        TestProject testProject = testProjectRepository.findById((long) 2).get();
        assertEquals(null, testProject.getInviteCode());
        testProject.setInviteCode("haha");
        testProjectRepository.updateInviteCode(2,"haha");
        testProject = testProjectRepository.findById((long) 2).get();
        assertEquals("haha", testProject.getInviteCode());
        
    }

    @Test public void test_find(){
        TestProject testProject = testProjectRepository.findByInviteCode("haha");
        assertEquals(2, testProject.getId());
    }
}
