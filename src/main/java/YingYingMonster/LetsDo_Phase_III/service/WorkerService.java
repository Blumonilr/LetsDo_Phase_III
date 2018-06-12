package YingYingMonster.LetsDo_Phase_III.service;

import java.util.List;

import YingYingMonster.LetsDo_Phase_III.entity.JoinEvent;
import YingYingMonster.LetsDo_Phase_III.entity.Project;
import YingYingMonster.LetsDo_Phase_III.model.Data;

import YingYingMonster.LetsDo_Phase_III.model.Tag;
import YingYingMonster.LetsDo_Phase_III.model.TagRequirement;

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

	@Deprecated
	public List<String>viewAllProjects();//查看所有项目
	@Deprecated
	public Project getAProject(String publisherId,String projectId);//根据publisherId,projectId获取Project对象
	@Deprecated
	/**
	 * 
	 * @param workerId
	 * @param publisherId
	 * @param projectId
	 * @return 0 fork成功; -1 有参数为null; -2 worker不存在; -3 重复fork; -4 project不存在或者文件损失;
	 * 		   -5 人数已满,-6用户不符合要求
	 */
	public int forkProject(String workerId,String publisherId,String projectId);//fork项目，返回值待定
	@Deprecated
	public List<String>viewMyProjects(String workerId);//查看某个用户参加的项目名称
	@Deprecated
	public int viewProgress(String workerId,String publisherId,String projectId);//查看某个用户某个项目的进度
	@Deprecated
	public List<String>viewUndoData(String workerId,String publisherId,String projectId);
	@Deprecated
	public List<String>viewDoneData(String workerId,String publisherId,String projectId);
	@Deprecated
	public Data getAData(String workerId,String publisherId,String projectId,String dataId);
	@Deprecated
	public Tag getATag(String workerId,String publisherId,String projectId,String tagId);
	@Deprecated
	public boolean uploadTag(String userId,String publisherId,String projectId,String tagId,Tag tag);
	@Deprecated
	public int push(String workerId,String publisherId,String projectId);
	@Deprecated
	/**
	 * String pattern : publisherId_projectId
	 * @param wkId
	 * @return
	 */
	public List<String>viewFinishedPj(String wkId);
	@Deprecated
	public List<String>viewUnfinishedPj(String wkId);
	@Deprecated
	/**
	 * 
	 * @param wkId
	 * @param pjKey pattern : publisherId_projectId
	 * @return
	 */
	public boolean isPjFinished(String wkId,String pjKey);
	@Deprecated
	public TagRequirement getPjTagRequirement(String pubid,String pjid);
	
}
