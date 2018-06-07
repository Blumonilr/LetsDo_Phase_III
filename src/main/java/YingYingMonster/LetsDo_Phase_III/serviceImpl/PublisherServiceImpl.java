package YingYingMonster.LetsDo_Phase_III.serviceImpl;

import YingYingMonster.LetsDo_Phase_III.entity.Project;
import YingYingMonster.LetsDo_Phase_III.entity.TestProject;
import YingYingMonster.LetsDo_Phase_III.model.ProjectState;
import YingYingMonster.LetsDo_Phase_III.repository.ProjectRepository;
import YingYingMonster.LetsDo_Phase_III.service.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
public class PublisherServiceImpl implements PublisherService {

	@Autowired
	ProjectRepository pjrepository;


	@Override
	public Project createProject(Project project, MultipartFile dataSet) {
        project.setProjectState(ProjectState.setup);
        project.setStartDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));

        int picNum=0;

        project.setPicNum(picNum);
        return pjrepository.saveAndFlush(project);
	}

    @Override
    public boolean validateProjectName(long publisherId, String projectName) {
        List<Project> list = pjrepository.findByPublisherId(publisherId);
        for (Project project : list) {
            if (project.getProjectName().equals(projectName)) {
                return false;
            }
        }

        return true;
    }

    @Override
	public Project initializeProject(long id) {
        Project project = pjrepository.findById(id);
        project.setProjectState(ProjectState.initialize);
        return pjrepository.saveAndFlush(project);
	}

	@Override
	public TestProject addTestProject(TestProject testProject, MultipartFile multipartFile) {
		return null;
	}

	@Override
	public Project openProject(long id) {
		return null;
	}

	@Override
	public Project closeProject(long id) {
		return null;
	}

	@Override
	public Project getAProject(long projectId) {
		return null;
	}

	@Override
	public List<Project> findProjectByPublisherId(long publisherId) {
		return null;
	}

	@Override
	public List<Project> searchProjects(String keyword) {
		return null;
	}

	@Override
	public List<String[]> viewPushEvents(String publisherId, String projectId) {
		return null;
	}

	@Override
	public byte[] downloadTags(String publisherId, String projectId) {
		return new byte[0];
	}

	@Override
	public double viewProjectProgress(String publisherId, String projectId) {
		return 0;
	}

	@Override
	public List<String> viewWorkers(String publisherId, String projectId) {
		return null;
	}
}
