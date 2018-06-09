package YingYingMonster.LetsDo_Phase_III.repository;

import YingYingMonster.LetsDo_Phase_III.entity.Project;
import YingYingMonster.LetsDo_Phase_III.entity.TestProject;
import YingYingMonster.LetsDo_Phase_III.model.MarkMode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
                10);
        long id = projectRepository.saveAndFlush(project).getId();
        TestProject testProject = new TestProject(MarkMode.AREA);
        testProject = testProjectRepository.saveAndFlush(testProject);
        projectRepository.updateTestProject(id, testProject);

    }
}
