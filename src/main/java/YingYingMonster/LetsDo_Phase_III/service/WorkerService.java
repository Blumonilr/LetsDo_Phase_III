package YingYingMonster.LetsDo_Phase_III.service;

import java.util.List;

import YingYingMonster.LetsDo_Phase_III.entity.Image;
import YingYingMonster.LetsDo_Phase_III.entity.event.JoinEvent;
import YingYingMonster.LetsDo_Phase_III.entity.Project;
import YingYingMonster.LetsDo_Phase_III.entity.Tag;
import YingYingMonster.LetsDo_Phase_III.entity.TestProject;

public interface WorkerService {

	/**
	 * 根据worker的能力、偏好按序返回项目列表
	 * @param workerId
	 * @return
	 */
	public List<Project> discoverProjects(long workerId);

	public Project getAProject(long projectId);

	/**
	 * 查找worker参加的项目
	 * key是筛选条件，null，“”均可，支持模糊查找
	 * @param workerId
	 * @param key
	 * @return
	 */
	public List<Project> viewMyProjects(long workerId, String key);

	public List<Project> viewMyActiveProjects(long workerId, String key);

	/**
	 * 查看worker的join记录
	 * @param workerId
	 * @return
	 */
	public List<JoinEvent> viewMyJoinHistory(long workerId);

	/**
	 *
	 * @param workerId
	 * @param projectId
	 * @return   0 success; -1 requirement not satisfied; -2 no access to the project
	 */
	public int joinProject(long workerId, long projectId);

	/**
	 * 工人停止参加某个项目
	 * @param workerId
	 * @param projectId
	 */
	public void quitProject(long workerId, long projectId);

	public Tag uploadTag(Tag tag);

	public List<Image> getAPageOfImage(long projectId, int pageId);

	public List<Image> getAllImages(long projectId);

	/**
	 * 查看已经做过的Tag
	 * @param workerId
	 * @param projectId
	 * @return
	 */
	public List<Tag> viewTags(long workerId, long projectId);

	/**
	 * 等待考试或考试中返回a	
	 * 正在工作 b
	 * 结束  c
	 * @param workerId
	 * @param projectId
	 */
	public String  getWorkingState(long workerId, long projectId);
	
	/**
	 * 返回TestProject对象
	 * @param projectId
	 * @return
	 */
	public TestProject joinTest(long projectId);

	/**
	 * 告知服务器考试结束，请求计算分数
	 * @param projectId
	 * @param workerId
	 */
	public void finishTest(long workerId,long projectId);

	public int getTestResult(long workerId, long projectId);
}
