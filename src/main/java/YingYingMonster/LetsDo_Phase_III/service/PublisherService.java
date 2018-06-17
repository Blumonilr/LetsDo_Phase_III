package YingYingMonster.LetsDo_Phase_III.service;

import java.io.FileNotFoundException;
import java.util.List;

import YingYingMonster.LetsDo_Phase_III.entity.TestProject;
import org.springframework.web.multipart.MultipartFile;

import YingYingMonster.LetsDo_Phase_III.entity.Project;

public interface PublisherService {

    /**
     * 创建project，设置为setup状态
     * @param project
     * @param dataSet
     * @return
     */
	public Project createProject(Project project, MultipartFile dataSet);

    public boolean validateProjectName(long publisherId, String projectName);//新建项目时检查项目名是否合法

    /**
     * 把相应project设置为initialize状态
     * @param id
     * @return 相应project对象
     */
    public Project initializeProject(long id);

    //上传测试集
    public TestProject addTestProject(long publisherId, MultipartFile multipartFile);

    /**
     * 把相应project设置为open状态
     * @param id
     * @return
     */
    public Project openProject(long id);

    /**
     * 把相应project设置为closed状态
     * @param id
     * @return
     */
    public Project closeProject(long id);

	public Project getAProject(long projectId);

    public List<Project> findProjectByPublisherId(long publisherId);

    /**
     * project string类型属性有projectName , tagrequirement
     *
     * @param publisherId
     * @param keyword
     * @return
     */
    public List<Project> searchProjects(long publisherId, String keyword);//根据keyword查找已有的项目，支持模糊查询

	@Deprecated
	public List<String[]> viewPushEvents(String publisherId,String projectId);//查看某个项目的提交记录

	public byte[] downloadTags(long projectId) throws Exception;//下载所有标注

    @Deprecated
	public double viewProjectProgress(String publisherId,String projectId);

    @Deprecated
	public List<String> viewWorkers(String publisherId,String projectId);
}
