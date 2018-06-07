package YingYingMonster.LetsDo_Phase_III.serviceImpl;

import YingYingMonster.LetsDo_Phase_III.entity.Project;
import YingYingMonster.LetsDo_Phase_III.repository.ProjectRepository;
import YingYingMonster.LetsDo_Phase_III.service.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Component
public class PublisherServiceImpl implements PublisherService {

	@Autowired
	ProjectRepository pjrepository;


	@Override
	public Project getAProject(long projectId) {
		return null;
	}

	@Override
	public boolean createProject(Project project, MultipartFile dataSet) {
		return false;
	}

	@Override
	public boolean validateProject(String publisherId, String projectId) {
		return false;
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
