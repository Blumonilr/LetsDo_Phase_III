package YingYingMonster.LetsDo_Phase_III.serviceImpl;

import YingYingMonster.LetsDo_Phase_III.entity.JoinEvent;
import YingYingMonster.LetsDo_Phase_III.entity.Project;
import YingYingMonster.LetsDo_Phase_III.entity.User;
import YingYingMonster.LetsDo_Phase_III.entity.Worker;
import YingYingMonster.LetsDo_Phase_III.model.Data;
import YingYingMonster.LetsDo_Phase_III.model.Tag;
import YingYingMonster.LetsDo_Phase_III.model.TagRequirement;

import YingYingMonster.LetsDo_Phase_III.repository.JoinEventRepository;
import YingYingMonster.LetsDo_Phase_III.service.ProjectService;
import YingYingMonster.LetsDo_Phase_III.service.UserService;
import YingYingMonster.LetsDo_Phase_III.service.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class WorkerServiceImpl implements WorkerService {

	@Autowired
	ProjectService projectService;

	@Autowired
	UserService userService;

	@Autowired
	JoinEventRepository joinEventRepository;


	@Override
	public List<Project> discoverProjects(long workerId) {
		//获得worker的能力、偏好等数据

		return null;
	}

	@Override
	public Project getAProject(long projectId) {
		return projectService.getAProject(projectId);
	}

	@Override
	public List<Project> viewMyProjects(long workerId, String key) {
		String k = key == null ? "" : key;
//		return projectService.findWorkerProjects(workerId, k);
		return joinEventRepository.findByWorkerId(workerId).stream()
				.map(x -> projectService.getAProject(x.getProjectId()))
				.filter(x->(x.getProjectName().contains(k)||x.getTagRequirement().contains(k)))
				.collect(Collectors.toList());
	}

	@Override
	public List<Project> viewMyActiveProjects(long workerId, String key) {
		String k = key == null ? "" : key;
		return joinEventRepository.findByWorkerIdAndActiveTrue(workerId).stream()
				.map(x -> projectService.getAProject(x.getProjectId()))
				.filter(x->(x.getProjectName().contains(k)||x.getTagRequirement().contains(k)))
				.collect(Collectors.toList());
	}

	@Override
	public List<JoinEvent> viewMyJoinHistory(long workerId) {
		return joinEventRepository.findByWorkerId(workerId);
	}


	@Override
	public int joinProject(long workerId, long projectId) {
		Project project = projectService.getAProject(projectId);
		Worker worker = (Worker) userService.getUser(workerId);

		if (worker.getLevel() < project.getWorkerMinLevel()) {  //level limit not satisfied
			return -1;
		}
		if (false) {  //no access to the project
			return -2;
		}

		JoinEvent joinEvent = joinEventRepository.findByWorkerIdAndProjectId(workerId, projectId);
		if (joinEvent == null) {
			joinEvent = new JoinEvent(workerId, projectId, new Date(), true);
			joinEventRepository.saveAndFlush(joinEvent);
		} else {
			if (!joinEvent.isActive()) {
				joinEvent.setActive(true);
				joinEventRepository.saveAndFlush(joinEvent);
			}
		}
		return 0;
	}

	@Override
	public void quitProject(long workerId, long projectId) {
		JoinEvent joinEvent = joinEventRepository.findByWorkerIdAndProjectId(workerId, projectId);
		joinEvent.setActive(false);
		joinEventRepository.saveAndFlush(joinEvent);
	}

	@Override
	public List<String> viewAllProjects() {
		return null;
	}

	@Override
	public Project getAProject(String publisherId, String projectId) {
		return null;
	}

	@Override
	public int forkProject(String workerId, String publisherId, String projectId) {
		return 0;
	}

	@Override
	public List<String> viewMyProjects(String workerId) {
		return null;
	}

	@Override
	public int viewProgress(String workerId, String publisherId, String projectId) {
		return 0;
	}

	@Override
	public List<String> viewUndoData(String workerId, String publisherId, String projectId) {
		return null;
	}

	@Override
	public List<String> viewDoneData(String workerId, String publisherId, String projectId) {
		return null;
	}

	@Override
	public Data getAData(String workerId, String publisherId, String projectId, String dataId) {
		return null;
	}

	@Override
	public Tag getATag(String workerId, String publisherId, String projectId, String tagId) {
		return null;
	}

	@Override
	public boolean uploadTag(String userId, String publisherId, String projectId, String tagId, Tag tag) {
		return false;
	}

	@Override
	public int push(String workerId, String publisherId, String projectId) {
		return 0;
	}

	@Override
	public List<String> viewFinishedPj(String wkId) {
		return null;
	}

	@Override
	public List<String> viewUnfinishedPj(String wkId) {
		return null;
	}

	@Override
	public boolean isPjFinished(String wkId, String pjKey) {
		return false;
	}

	@Override
	public TagRequirement getPjTagRequirement(String pubid, String pjid) {
		return null;
	}
}
