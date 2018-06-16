package YingYingMonster.LetsDo_Phase_III.serviceImpl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

import YingYingMonster.LetsDo_Phase_III.entity.Ability;
import YingYingMonster.LetsDo_Phase_III.entity.Image;
import YingYingMonster.LetsDo_Phase_III.entity.Project;
import YingYingMonster.LetsDo_Phase_III.entity.event.JoinEvent;
import YingYingMonster.LetsDo_Phase_III.entity.role.Publisher;
import YingYingMonster.LetsDo_Phase_III.entity.role.User;
import YingYingMonster.LetsDo_Phase_III.entity.role.Worker;
import YingYingMonster.LetsDo_Phase_III.model.WorkerRequirement;
import YingYingMonster.LetsDo_Phase_III.repository.AbilityRepository;
import YingYingMonster.LetsDo_Phase_III.repository.event.JoinEventRepository;
import YingYingMonster.LetsDo_Phase_III.repository.event.PublishEventRepository;
import YingYingMonster.LetsDo_Phase_III.repository.role.UserRepository;
import YingYingMonster.LetsDo_Phase_III.repository.role.WorkerRepository;
import YingYingMonster.LetsDo_Phase_III.service.ImageService;
import YingYingMonster.LetsDo_Phase_III.service.ProjectService;
import YingYingMonster.LetsDo_Phase_III.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import YingYingMonster.LetsDo_Phase_III.service.AdminService;

@Component
public class AdminServiceImpl implements AdminService{


	@Autowired
	JoinEventRepository joinEventRepository;

	@Autowired
	PublishEventRepository publishEventRepository;

	@Autowired
	UserService userService;

	@Autowired
	ProjectService projectService;

	@Autowired
	ImageService imageService;

	@Autowired
	AbilityRepository abilityRepository;


	@Override
	public List<Project> viewAllProjects() {
		return projectService.viewAllProjects();
	}

	@Override
	public List<JoinEvent> viewAllJoinEvents(String workerName, String projectName) {
		List<JoinEvent> events=new ArrayList<>();
		List<Worker> workers=userService.findWorkerByNameLike(workerName);
		for (Worker w:workers) {
			List<Project> projects = projectService.findWorkerProjects(w.getId(),projectName);
			for (Project p:projects){
				JoinEvent joinEvent=joinEventRepository.findByWorkerIdAndProjectId(w.getId(),p.getId());
				events.add(joinEvent);
			}
		}
		return events;
	}

	@Override
	public int viewUserNum(){
		return userService.findUsersByName("").size();
	}


	@Override
	public List<Project> viewDoingProject() {
		List<Project> projects=projectService.viewAllProjects();
		List<Project> re=new ArrayList<>();
		for (Project p:projects){
			if (p.getProjectState().equals("open"))
				re.add(p);
		}
		return re;
	}

	@Override
	public List<Project> viewDoneProject() {
		List<Project> projects=projectService.viewAllProjects();
		List<Project> re=new ArrayList<>();
		for (Project p:projects){
			if (p.getProjectState().equals("close"))
				re.add(p);
		}
		return re;
	}

	@Override
	public List<Worker> viewAllWorkers() {
		return userService.findWorkerByNameLike("");
	}

	@Override
	public Worker finByWorkerId(long workerId) {
		return (Worker) userService.getUser(workerId);
	}

	@Override
	public List<Worker> findByWorkerName(String key) {
		return userService.findWorkerByNameLike(key);
	}

	@Override
	public int workInProjectNum(long workerId) {
		return joinEventRepository.findByWorkerId(workerId).size();
	}

	@Override
	public Ability findWorkerAbilityInOneField(long workerId, String labelName) {
		User wk=userService.getUser(workerId);
		List<Ability> list=abilityRepository.findByUser(wk);
		for (Ability a:list){
			if (a.getLabel().getName().equals(labelName))
				return a;
		}
		return null;
	}

	@Override
	public List<Ability> findWorkerAbility(long workerId) {
		User wk=userService.getUser(workerId);
		List<Ability> list=abilityRepository.findByUser(wk);
		return list;
	}

	@Override
	public int workerActiveTime(long workerId) {
		return userService.getUser(workerId).getLogEvent().getConstantDays();
	}

	@Override
	public List<Publisher> viewAllPublishers() {
		return userService.findPublisherByNameLike("");
	}

	@Override
	public Publisher findByPublisherId(long publisherId) {
		return (Publisher) userService.getUser(publisherId);
	}

	@Override
	public List<Publisher> findByPublisherName(String key) {
		return userService.findPublisherByNameLike(key);
	}

	@Override
	public int publishProjectNum(long publisherId) {
		return publishEventRepository.findByPublisherId(publisherId).size();
	}

	@Override
	public Map<Project, Double> viewAllProjectProgress() {
		Map<Project,Double> map=new HashMap<>();
		List<Project> list=projectService.viewAllProjects();
		for (Project p:list){
			int picNum=p.getPicNum();
			List<Image> images=imageService.findProjectAllImage(p.getId());
			int finishedPicNum=0;
			for (Image i:images){
				if(i.isFinished())
					finishedPicNum++;
			}
			double port=(double)finishedPicNum/picNum;
			map.put(p,port);
		}
		return map;
	}

	@Override
	public List<Worker> workerLabelAccuracyRank(String labelName) {
		List<Worker> rank=findByWorkerName("");
		rank.sort(new Comparator<Worker>() {
			@Override
			public int compare(Worker o1, Worker o2) {
				if(findWorkerAbilityInOneField(o1.getId(),labelName).getAccuracy()>findWorkerAbilityInOneField(o1.getId(),labelName).getAccuracy())
					return 1;
				else if(findWorkerAbilityInOneField(o1.getId(),labelName).getAccuracy()==findWorkerAbilityInOneField(o1.getId(),labelName).getAccuracy())
					return 0;
				else
					return -1;
			}
		});
		return rank;
	}

	@Override
	public List<Worker> workerLabelNumRank(String labelName) {
		List<Worker> rank=findByWorkerName("");
		rank.sort(new Comparator<Worker>() {
			@Override
			public int compare(Worker o1, Worker o2) {
				if(findWorkerAbilityInOneField(o1.getId(),labelName).getLabelHistoryNum()>findWorkerAbilityInOneField(o1.getId(),labelName).getLabelHistoryNum())
					return 1;
				else if(findWorkerAbilityInOneField(o1.getId(),labelName).getLabelHistoryNum()==findWorkerAbilityInOneField(o1.getId(),labelName).getLabelHistoryNum())
					return 0;
				else
					return -1;
			}
		});
		return rank;
	}
}
