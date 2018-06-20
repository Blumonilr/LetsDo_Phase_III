package YingYingMonster.LetsDo_Phase_III.serviceImpl;

import YingYingMonster.LetsDo_Phase_III.entity.*;
import YingYingMonster.LetsDo_Phase_III.entity.event.CommitEvent;
import YingYingMonster.LetsDo_Phase_III.entity.event.JoinEvent;
import YingYingMonster.LetsDo_Phase_III.entity.role.User;
import YingYingMonster.LetsDo_Phase_III.entity.role.Worker;
import YingYingMonster.LetsDo_Phase_III.model.ProjectState;
import YingYingMonster.LetsDo_Phase_III.repository.AbilityRepository;
import YingYingMonster.LetsDo_Phase_III.repository.ImageRepository;
import YingYingMonster.LetsDo_Phase_III.repository.event.CommitEventRepository;
import YingYingMonster.LetsDo_Phase_III.repository.event.JoinEventRepository;
import YingYingMonster.LetsDo_Phase_III.repository.TagRepository;
import YingYingMonster.LetsDo_Phase_III.service.ProjectService;
import YingYingMonster.LetsDo_Phase_III.service.UserService;
import YingYingMonster.LetsDo_Phase_III.service.WorkerService;
import com.sun.imageio.plugins.common.I18N;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.text.NumberFormat;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class WorkerServiceImpl implements WorkerService {

	@Autowired
	ProjectService projectService;

	@Autowired
	UserService userService;

	@Autowired
	JoinEventRepository joinEventRepository;

	@Autowired
	TagRepository tagRepository;

	@Autowired
	ImageRepository imageRepository;

	@Autowired
	AbilityRepository abilityRepository;
	@Autowired
	CommitEventRepository commitEventRepository;

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public List<Project> discoverProjects(long workerId) {
		//获得worker的能力、偏好等数据
		User user = userService.getUser(workerId);

		List<Ability> abilities = abilityRepository.findByUser(user);
		logger.info("user's abilities = {}", abilities);
		logger.info("size = "+abilities.size());

		List<String>labelNames=abilities.stream().sorted((x, y) -> {
			if (x.getAccuracy() > y.getAccuracy() ||
					x.getAccuracy() == y.getAccuracy() && x.getBias() > y.getBias()) {
				return -1;
			} else {
				if (x.getAccuracy() == y.getAccuracy() && x.getBias() == y.getBias()) {
					return 0;
				} else {
					return 1;
				}
			}
		}).map(x -> x.getLabel().getName()).collect(Collectors.toList());

//		for (String s : labelNames) {
//			System.out.println(s);
//		}
//		System.out.println("label size : "+labelNames.size());
		return projectService.viewAllOpenedProjects(labelNames);
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
		return joinEventRepository.findByWorkerIdAndWorkState(workerId,JoinEvent.WORKING)
				.stream().map(x -> projectService.getAProject(x.getProjectId()))
				.filter(x->(x.getProjectName().contains(k)||x.getTagRequirement().contains(k)))
				.collect(Collectors.toList());
	}

	@Override
	public List<Project> viewMyWorkingProject(long workerId) {
		return joinEventRepository.findByWorkerIdAndWorkState(workerId, JoinEvent.WORKING)
				.stream().map(x -> projectService.getAProject(x.getProjectId()))
				.collect(Collectors.toList());
	}

	@Override
	public List<Project> viewMyWorkFinishedProject(long workerId) {
		return joinEventRepository.findByWorkerIdAndWorkState(workerId, JoinEvent.WORK_Finished)
				.stream().map(x -> projectService.getAProject(x.getProjectId()))
				.collect(Collectors.toList());
	}

	@Override
	public List<Project> viewMyNotStartedProject(long workerId) {

		List<JoinEvent> list = joinEventRepository.findByWorkerIdAndWorkState(workerId, JoinEvent.TEST_NOT_FINISHED);
		list.addAll(joinEventRepository.findByWorkerIdAndWorkState(workerId, JoinEvent.TEST_FINISHED));
		list.addAll(joinEventRepository.findByWorkerIdAndWorkState(workerId, JoinEvent.TEST_NOT_PASSED));

		return list.stream().map(x -> projectService.getAProject(x.getProjectId()))
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
			System.out.println("从未参加过这个项目");
			joinEvent = new JoinEvent(workerId, projectId, new Date());
			if (project.getTestProject() == null) {
				joinEvent.setWorkState(JoinEvent.WORKING);
			} else {
				joinEvent.setWorkState(JoinEvent.TEST_NOT_FINISHED);
			}
			joinEventRepository.saveAndFlush(joinEvent);
		} else{
			System.out.println("以前参加过这个项目");
			if (!joinEvent.getWorkState().equals(JoinEvent.WORKING)) {

				if (project.getTestProject() == null) {
					joinEvent.setWorkState(JoinEvent.WORKING);
					joinEvent.setDate(new Date());
				} else {
					joinEvent.setWorkState(JoinEvent.TEST_NOT_FINISHED);
					joinEvent.setDate(new Date());
				}
				joinEventRepository.saveAndFlush(joinEvent);
			}
		}

		return 0;
	}

	@Override
	public void quitProject(long workerId, long projectId) {
		JoinEvent joinEvent = joinEventRepository.findByWorkerIdAndProjectId(workerId, projectId);
		joinEvent.setWorkState(JoinEvent.WORK_Finished);
		joinEventRepository.saveAndFlush(joinEvent);
	}

	@Override
	public Tag uploadTag(Tag tag) {

		Tag tag1 = tagRepository.findByWorkerIdAndImageId(tag.getWorkerId(), tag.getImageId());

		if (tag1 != null) {
			tag1.setData(tag.getData());
			tag1.setXmlFile(tag.getXmlFile());
			tag1=tagRepository.saveAndFlush(tag1);
		} else {
			tag1=tagRepository.saveAndFlush(tag);
		}
		return tag1;
	}

	@Override
	/**
	 * 获得一页图片，优先分配人数最少的图片
	 */
	public List<Image> getAPageOfImage(long projectId, int pageId) {
		return imageRepository.findByProjectId(projectId,new Sort(Sort.Direction.ASC, "currentNum"))
				.stream().filter(x->!x.isFinished()).limit(5).collect(Collectors.toList());
//		return imageRepository
//				.findByProjectIdAndIsFinishedFalseAndIsTestFalse(projectId, PageRequest.of(pageId, 5))
//				.stream().sorted((i1,i2)->{
//					return i1.getCurrentNum() > i2.getCurrentNum() ? 1 :
//							i1.getCurrentNum() == i2.getCurrentNum() ? 0 : -1;
//				}).collect(Collectors.toList());

	}

	@Override
	public List<Image> getAllImages(long projectId) {
		return imageRepository.findByProjectId(projectId);
	}

	@Override
	public List<Tag> viewTags(long workerId, long projectId) {
		return tagRepository.findByWorkerIdAndProjectId(workerId, projectId);
	}

	@Override
	public String getWorkingState(long workerId, long projectId) {
		return joinEventRepository.findByWorkerIdAndProjectId(workerId, projectId).getWorkState();
	}

	@Override
	public TestProject joinTest(long projectId) {
		return projectService.getAProject(projectId).getTestProject();
	}

	@Override
	public void finishTest(long workerId, long projectId) {
		joinEventRepository.setWorkState(workerId, projectId, JoinEvent.TEST_FINISHED);
		//后台开始计算分数...并设置相应的状态
		double score = Math.random() * 20+80;

		//read accuracy from commit event
//		logger.info("read accuracy from commit event");
//		List<CommitEvent> comits = commitEventRepository.findByWorkeridAndProjectid(workerId, projectId);


		joinEventRepository.setTestScore(workerId, projectId, score);
		if (score >= projectService.getAProject(projectId).getTestAccuracy()) {
			joinEventRepository.setWorkState(workerId, projectId, JoinEvent.WORKING);
		}else{
			joinEventRepository.setWorkState(workerId, projectId, JoinEvent.TEST_NOT_PASSED);
		}
	}

	@Override
	public double getTestResult(long workerId, long projectId) {
		return joinEventRepository.findByWorkerIdAndProjectId(workerId, projectId).getTestScore();
	}

	@Override
	public List<Project> viewWorkerMonthJoinProject(long workerId,Calendar date) {
		List<JoinEvent> joins=joinEventRepository.findByWorkerId(workerId);
		List<Project> pjs=new ArrayList<>();
		for (JoinEvent j:joins) {
			Calendar month = Calendar.getInstance();
			month.setTime(j.getDate());
			if (month.get(Calendar.YEAR)==date.get(Calendar.YEAR)&&month.get(Calendar.MONTH)==date.get(Calendar.MONTH)){
				pjs.add(projectService.getAProject(j.getProjectId()));
			}
		}
		return pjs;
	}

	@Override
	public List<Project> viewWorkerMonthFinishProject(long workerId, Calendar calendar) {
		List<JoinEvent> joins=joinEventRepository.findByWorkerId(workerId);
		List<Project> pjs=new ArrayList<>();
		for (JoinEvent j:joins) {
			if (j.getWorkState().equals(JoinEvent.WORK_Finished)) {
				Calendar month = Calendar.getInstance();
				month.setTime(j.getDate());
				if (month.get(Calendar.YEAR)==calendar.get(Calendar.YEAR)
						&&month.get(Calendar.MONTH)==calendar.get(Calendar.MONTH)){
					pjs.add(projectService.getAProject(j.getProjectId()));
				}
			}
		}
		return pjs;
	}

	@Override
	public Map<String, Integer> viewWorkerMonthLabel(long workerId, Calendar date) {
		Map<String,Integer> labels=new HashMap<>();
		List<JoinEvent> joins=joinEventRepository.findByWorkerId(workerId);
		for (JoinEvent j:joins) {
			Calendar month = Calendar.getInstance();
			month.setTime(j.getDate());
			if (month.get(Calendar.YEAR)==date.get(Calendar.YEAR)&&month.get(Calendar.MONTH)==date.get(Calendar.MONTH)){
				List<String> label=projectService.getAProject(j.getProjectId()).getLabels();
				for (String l:label){
					if (labels.containsKey(l))
						labels.put(l,labels.get(l).intValue()+1);
					else
						labels.put(l,1);
				}
			}
		}
		return labels;
	}

	@Override
	public List<String> getWorkerAbilitiesInString(long workerId) {
		return userService.getUserAbilities(workerId).stream()
				.map(x -> x.getLabel().getName() + "_" + Double.toString(x.getAccuracy())
						+ "_" + Integer.toString(x.getLabelHistoryNum())
						+ "_" + Integer.toString(x.getBias())).collect(Collectors.toList());
	}


}
