package YingYingMonster.LetsDo_Phase_III.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import YingYingMonster.LetsDo_Phase_III.model.Data;
import YingYingMonster.LetsDo_Phase_III.model.Project;
import YingYingMonster.LetsDo_Phase_III.model.Tag;

public interface WorkerDAO {

	public List<Project>viewWorkerProject(String workerId) throws FileNotFoundException, ClassNotFoundException, IOException;
	
	public int fork(String workerId,String publisherId,String projectId) throws FileNotFoundException, ClassNotFoundException, IOException;
	
	public boolean uploadTag(String workerId,String publisherId, String projectId, String tagId, Tag tag) throws FileNotFoundException, IOException;
	
	public Tag downloadTag(String workerId,String publisherId, String projectId, String tagId) throws FileNotFoundException, ClassNotFoundException, IOException;
	
	public List<String>viewUndoData(String workerId,String publisherId,String projectId);

	public List<String>viewDoneData(String workerId,String publisherId,String projectId);

	public Data getAData(String workerId,String publisherId,String projectId,String dataId) throws IOException;
	
	/**
	 * 返回被采纳的Tag数量
	 * @param workerId
	 * @param publisherId
	 * @param projectId
	 * @return
	 * @throws FileNotFoundException
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public int push(String workerId,String publisherId,String projectId) throws FileNotFoundException, ClassNotFoundException, IOException;
	
	/**
	 * String pattern : publisherId_projectId
	 * @param wkId
	 * @return
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * @throws FileNotFoundException 
	 */
	public List<String>viewFinishedPj(String wkId) throws FileNotFoundException, ClassNotFoundException, IOException;
	
	public List<String>viewUnfinishedPj(String wkId) throws FileNotFoundException, ClassNotFoundException, IOException;
	
	/**
	 * 
	 * @param wkId
	 * @param pjKey pattern : publisherId_projectId
	 * @return
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * @throws FileNotFoundException 
	 */
	public boolean isPjFinished(String wkId,String pjKey) throws FileNotFoundException, ClassNotFoundException, IOException;
}
