package YingYingMonster.LetsDo_Phase_III.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import YingYingMonster.LetsDo_Phase_III.model.Project;

public interface ProjectDAO {

	/**
	 * 添加project
	 * @param project
	 * @return
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public boolean addProject(Project project) throws FileNotFoundException, IOException;
	
	/**
	 * 验证projectId是否合法
	 * @param publisherId
	 * @param projectId
	 * @return
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * @throws FileNotFoundException 
	 */
	public boolean validateProject(String publisherId,String projectId) throws FileNotFoundException, ClassNotFoundException, IOException;
	
	/**
	 * 供发起者查看自己的项目
	 * @param publisherId
	 * @return
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * @throws FileNotFoundException 
	 */
	public List<Project>publisherViewProjects(String publisherId) throws FileNotFoundException, ClassNotFoundException, IOException;
	
	/**
	 * 供所有人查看所有上传的项目
	 * @return
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * @throws FileNotFoundException 
	 */
	public List<Project>viewAllProjects() throws FileNotFoundException, ClassNotFoundException, IOException;
	
//	/**
//	 * 供工人查看自己的项目
//	 * @param workerId
//	 * @return
//	 */
//	public List<Project>workerViewProjects(String workerId);
	
	/**
	 * 修改当前工人数
	 * @param projectId
	 * @return
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * @throws FileNotFoundException 
	 */
	public boolean modifyCurrentWorkerNum(String publisherId,String projectId) throws FileNotFoundException, ClassNotFoundException, IOException;
	
	/**
	 * 删除未开始的项目
	 * @param publisherId
	 * @param projectId
	 * @return
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * @throws FileNotFoundException 
	 */
	public boolean deleteProject(String publisherId,String projectId) throws FileNotFoundException, ClassNotFoundException, IOException;

	public Project getAProject(String publisherId,String projectId) throws FileNotFoundException, ClassNotFoundException, IOException;
	
}
