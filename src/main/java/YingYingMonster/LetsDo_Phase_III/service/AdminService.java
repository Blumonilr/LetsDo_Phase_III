package YingYingMonster.LetsDo_Phase_III.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import YingYingMonster.LetsDo_Phase_III.entity.Ability;
import YingYingMonster.LetsDo_Phase_III.entity.Project;
import YingYingMonster.LetsDo_Phase_III.entity.event.JoinEvent;
import YingYingMonster.LetsDo_Phase_III.entity.role.Publisher;
import YingYingMonster.LetsDo_Phase_III.entity.role.Worker;


public interface AdminService {

	public List<Project> viewAllProjects();

	/**
	 * 根据工人名字和项目名字查找join记录
	 * 支持模糊查询
	 * 参数为null表示不关心这个条件
	 * @param workerName
	 * @param projectName
	 * @return
	 */
	public List<JoinEvent> viewAllJoinEvents(String workerName, String projectName);

	public int viewUserNum();

	public List<Project> viewDoingProject();

	public List<Project> viewDoneProject();

	public List<Worker> viewAllWorkers();

	public Worker finByWorkerId(long workerId);

	public List<Worker> findByWorkerName(String key);

	public int workInProjectNum(long workerId);

	public Ability findWorkerAbilityInOneField(long workerId,String labelName);

	public List<Ability> findWorkerAbility(long workerId);

	public int workerActiveTime(long workerId);

	public List<Publisher> viewAllPublishers();

	public Publisher findByPublisherId(long publisherId);

	public List<Publisher> findByPublisherName(String key);

	public int publishProjectNum(long publisherId);

	public Map<Project,Double> viewAllProjectProgress();

	public List<Worker> workerLabelAccuracyRank(String labelName);

	public List<Worker> workerLabelNumRank(String labelName);

	public List<Worker> workerAccuracyRank();

	public int registerNum(Calendar date);

	public List<Project> projectStart(Calendar date) throws ParseException;

	public List<Project> projectDone(Calendar date) throws ParseException;

	public Map<Integer,Integer> commitTime();
}
