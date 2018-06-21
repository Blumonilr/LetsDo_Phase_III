package YingYingMonster.LetsDo_Phase_III.serviceImpl;

import YingYingMonster.LetsDo_Phase_III.entity.*;
import YingYingMonster.LetsDo_Phase_III.entity.role.Publisher;
import YingYingMonster.LetsDo_Phase_III.entity.role.User;
import YingYingMonster.LetsDo_Phase_III.entity.role.Worker;
import YingYingMonster.LetsDo_Phase_III.entity.MarkMode;
import YingYingMonster.LetsDo_Phase_III.repository.AbilityRepository;
import YingYingMonster.LetsDo_Phase_III.repository.LabelRepository;
import YingYingMonster.LetsDo_Phase_III.repository.ProjectRepository;
import YingYingMonster.LetsDo_Phase_III.service.ProjectService;
import YingYingMonster.LetsDo_Phase_III.service.UserService;
import YingYingMonster.LetsDo_Phase_III.service.WorkerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertNotNull;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestWorkerServiceImpl {

	@Autowired
	ProjectService projectService;

	@Autowired
	ProjectRepository projectRepository;
	
	@Autowired
	WorkerService wkService;

	@Autowired
	UserService userService;

	@Autowired
	LabelRepository labelRepository;

	@Autowired
	AbilityRepository abilityRepository;

	public void setup(){
		Worker worker = new Worker("name1", "pw", "email", "intro", 0, 0, 0, 0, 0);
		Worker worker1 = new Worker("name2", "pw", "email", "intro", 0, 0, 0, 0, 0);
		Worker worker2 = new Worker("name3", "pw", "email", "intro", 0, 0, 0, 0, 0);
		worker = (Worker) userService.register(worker);
		worker1 = (Worker) userService.register(worker1);
		worker2 = (Worker) userService.register(worker2);

		Label label = new Label("label");
		Label label1 = new Label("label1");
		Label label2 = new Label("label2");
		label = labelRepository.save(label);
		label1 = labelRepository.save(label1);
		label2 = labelRepository.save(label2);
		labelRepository.flush();

		Ability ability = new Ability(worker, label);
		Ability ability1 = new Ability(worker, label1);
		Ability ability2 = new Ability(worker, label2);
		ability.setAccuracy(0.90);ability.setBias(10);
		ability1.setAccuracy(0.90);ability1.setBias(4);
		ability2.setAccuracy(0.99);ability2.setBias(1);
		abilityRepository.save(ability);
		abilityRepository.save(ability1);
		abilityRepository.save(ability2);
		abilityRepository.flush();

		Project project = new Project(MarkMode.AREA, 1, "pj1",
				5, 1, null, "",
				0, 0.60, 0, Arrays.asList("label"));
		Project project1 = new Project(MarkMode.AREA, 1, "pj2",
				5, 1, null, "",
				0, 0.60, 0, Arrays.asList("label1"));
		Project project2 = new Project(MarkMode.AREA, 1, "pj3",
				5, 1, null, "",
				0, 0.60, 0, Arrays.asList("label1","label2"));
		Project project3 = new Project(MarkMode.AREA, 1, "pj4",
				5, 1, null, "",
				0, 0.60, 0, Arrays.asList("label","label1"));
		projectRepository.save(project);
		projectRepository.save(project1);
		projectRepository.save(project2);
		projectRepository.save(project3);
		projectRepository.flush();
	}

	//this method is not safe!!!
//	@Transactional(rollbackOn = Exception.class)
//	public void teardown(){
//		userService.deleteUsersByName(Arrays.asList("name1", "name2", "name3"));
//		labelRepository.deleteAll();
//		abilityRepository.deleteAll();
//		projectRepository.deleteAll();
//	}

	@Test public void test_discover_projects(){
//		teardown();
		setup();
		User user = userService.findUsersByName("name1").get(0);
		List<Project> projects = wkService.discoverProjects(user.getId());
		for (Project project : projects) {
			System.out.println(project.getProjectName());
		}
//		teardown();
	}
	
	@Test public void test_discover_projects1(){
//		Worker worker = new Worker("neo", "pw", "email", "intro", 0, 0, 0, 0, 0);
//		worker = (Worker) userService.register(worker);
		List<Project> projects = wkService.discoverProjects(49);
		assertNotNull(projects);
		System.out.println(projects.size());
	}

	@Test public void batch_register(){

		for (int i = 0; i < 30; i++) {
			User user;
			double rand = Math.random() * 2;
			if (rand < 1) {
				user = new Worker("worker" + i, "111111", "email", "intro",
						0, 0, 0, 0, 0);
				int level = (int) (Math.random() * 10);
				int exp = ((int) (Math.random() * 10))*10;
				((Worker) user).setLevel(level);
				((Worker) user).setExp(exp);
			} else {
				user = new Publisher("publisher" + i, "111111", "email", "intro", (int) (Math.random() * 10000));
			}
			userService.register(user);
		}

	}

}
