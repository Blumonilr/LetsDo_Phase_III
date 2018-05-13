package YingYingMonster.LetsDo_Phase_III.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import org.springframework.stereotype.Component;

public interface DataDAO {

	/**
	 * 上传数据集
	 * @param publiserId
	 * @param dataSetId
	 * @param dataSet
	 * @return 解压后的图片数量
	 * @throws IOException 
	 */
	public int uploadDataSet(String publisherId,String dataSetId,int packSize,byte[]dataSet) throws IOException;
	
	/**
	 * 上传者下载工人的作业
	 * @param publisherId
	 * @param dataSetId
	 * @return
	 */
	public byte[] downloadTags(String publisherId,String dataSetId);
	
	/**
	 * 上传者查看项目进度
	 * 感觉项目进度应该是动态获取的，因此我把这个属性从Project对象里删去了
	 * @param publisherId
	 * @param projectId
	 * @return
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * @throws FileNotFoundException 
	 */
	public double viewProjectProgress(String publisherId,String projectId) throws FileNotFoundException, ClassNotFoundException, IOException;
	
	/**
	 * 查看某个项目的提交记录
	 * @param publisherId
	 * @param projectId
	 * @return
	 */
	public List<String[]>viewPushEvents(String publisherId,String projectId);
	
	/**
	 * 查看某个项目的参与者
	 * @param publisherId
	 * @param projectId
	 * @return
	 */
	public List<String> viewWorkers(String publisherId,String projectId);
	
	/**
	 * 
	 * @return
	 */
	public List<String[]> readProjectsDate();
	
	/**
	 * 
	 * @param Projects
	 * @return
	 */
	public boolean modifyDateStatus(List<String[]> Projects);
	
	public boolean addDate(String startTime,String endTime,String publisherId,String projectId) throws ParseException;
	
	public void deleteDir(String publisherId,String projectId);
}
