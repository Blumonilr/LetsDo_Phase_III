package YingYingMonster.LetsDo_Phase_III.service;

import java.util.List;

import YingYingMonster.LetsDo_Phase_III.entity.TestProject;
import org.springframework.web.multipart.MultipartFile;

import YingYingMonster.LetsDo_Phase_III.entity.Project;

public interface PublisherService {

	public Project getAProject(long projectId);//根据publisherId,projectId获取Project对象
	
	public boolean createProject(Project project, MultipartFile dataSet);

	//上传测试集
	public TestProject addTestProject(TestProject testProject, MultipartFile multipartFile);

	@Deprecated
	public boolean validateProject(String publisherId,String projectId);//新建项目时检查项目名是否合法

	public List<Project> searchProjects(String keyword);//根据keyword查找已有的项目，支持模糊查询

	@Deprecated
	public List<String[]> viewPushEvents(String publisherId,String projectId);//查看某个项目的提交记录

	public byte[] downloadTags(String publisherId,String projectId);//下载所有标注

	@Deprecated
	public double viewProjectProgress(String publisherId,String projectId);
	
	public List<String> viewWorkers(String publisherId,String projectId);
}
