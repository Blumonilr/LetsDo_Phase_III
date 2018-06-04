package YingYingMonster.LetsDo_Phase_III.service;

import java.util.List;

import YingYingMonster.LetsDo_Phase_III.model.Data;
import YingYingMonster.LetsDo_Phase_III.model.Project;
import YingYingMonster.LetsDo_Phase_III.model.Tag;
import YingYingMonster.LetsDo_Phase_III.model.TagRequirement;

public interface WorkerService {

	public List<String>viewAllProjects();//查看所有项目

	public Project getAProject(String publisherId,String projectId);//根据publisherId,projectId获取Project对象

	/**
	 * 
	 * @param workerId
	 * @param publisherId
	 * @param projectId
	 * @return 0 fork成功; -1 有参数为null; -2 worker不存在; -3 重复fork; -4 project不存在或者文件损失;
	 * 		   -5 人数已满,-6用户不符合要求
	 */
	public int forkProject(String workerId,String publisherId,String projectId);//fork项目，返回值待定

	public List<String>viewMyProjects(String workerId);//查看某个用户参加的项目名称

	public int viewProgress(String workerId,String publisherId,String projectId);//查看某个用户某个项目的进度

	public List<String>viewUndoData(String workerId,String publisherId,String projectId);

	public List<String>viewDoneData(String workerId,String publisherId,String projectId);

	public Data getAData(String workerId,String publisherId,String projectId,String dataId);

	public Tag getATag(String workerId,String publisherId,String projectId,String tagId);

	public boolean uploadTag(String userId,String publisherId,String projectId,String tagId,Tag tag);

	public int push(String workerId,String publisherId,String projectId);
	
	/**
	 * String pattern : publisherId_projectId
	 * @param wkId
	 * @return
	 */
	public List<String>viewFinishedPj(String wkId);
	
	public List<String>viewUnfinishedPj(String wkId);
	
	/**
	 * 
	 * @param wkId
	 * @param pjKey pattern : publisherId_projectId
	 * @return
	 */
	public boolean isPjFinished(String wkId,String pjKey);
	
	public TagRequirement getPjTagRequirement(String pubid,String pjid);
	
}
