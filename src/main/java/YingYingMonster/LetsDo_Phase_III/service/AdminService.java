package YingYingMonster.LetsDo_Phase_III.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import YingYingMonster.LetsDo_Phase_III.entity.JoinEvent;
import YingYingMonster.LetsDo_Phase_III.model.Project;
import YingYingMonster.LetsDo_Phase_III.model.User;

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

	@Deprecated
	public int viewUserNum() throws FileNotFoundException, ClassNotFoundException, IOException;
	@Deprecated
	public int viewProjectNum() throws FileNotFoundException, ClassNotFoundException, IOException;
	@Deprecated
	public List<User> viewUsers() throws FileNotFoundException, ClassNotFoundException, IOException;
	@Deprecated
	public List<Project> viewProjectOnDuty() throws FileNotFoundException, ClassNotFoundException, IOException;
	@Deprecated
	public List<Project> viewProjectDone() throws FileNotFoundException, ClassNotFoundException, IOException;
}
