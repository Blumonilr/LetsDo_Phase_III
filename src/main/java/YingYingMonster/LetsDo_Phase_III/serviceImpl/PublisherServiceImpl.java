package YingYingMonster.LetsDo_Phase_III.serviceImpl;

import YingYingMonster.LetsDo_Phase_III.entity.Image;
import YingYingMonster.LetsDo_Phase_III.entity.Project;
import YingYingMonster.LetsDo_Phase_III.entity.TestProject;
import YingYingMonster.LetsDo_Phase_III.model.ProjectState;
import YingYingMonster.LetsDo_Phase_III.repository.ImageRepository;
import YingYingMonster.LetsDo_Phase_III.repository.ProjectRepository;
import YingYingMonster.LetsDo_Phase_III.repository.TestProjectRepository;
import YingYingMonster.LetsDo_Phase_III.service.ImageService;
import YingYingMonster.LetsDo_Phase_III.service.ProjectService;
import YingYingMonster.LetsDo_Phase_III.service.PublisherService;
import YingYingMonster.LetsDo_Phase_III.service.TestProjectService;
import de.innosystec.unrar.Archive;
import de.innosystec.unrar.exception.RarException;
import de.innosystec.unrar.rarfile.FileHeader;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Component
public class PublisherServiceImpl implements PublisherService {

	@Autowired
	ProjectService projectService;

	@Autowired
	TestProjectService testProjectService;


	@Override
	public Project createProject(Project project, MultipartFile dataSet) {
		return projectService.addProject(project, dataSet);
	}

    @Override
    public boolean validateProjectName(long publisherId, String projectName) {
		return projectService.validateProjectName(publisherId, projectName);
    }

    @Override
	public Project initializeProject(long id) {
		return projectService.initializeProject(id);
	}

    @Override
    public TestProject addTestProject(long projectId,  MultipartFile multipartFile) {
		return testProjectService.addTestProject(projectId, multipartFile);
    }

	@Override
	public Project openProject(long id) {
		return projectService.openProject(id);
	}

	@Override
	public Project closeProject(long id) {
		return projectService.closeProject(id);
	}

	@Override
	public Project getAProject(long projectId) {
		return projectService.getAProject(projectId);
	}

	@Override
	public List<Project> findProjectByPublisherId(long publisherId) {

		return projectService.findPublisherProjects(publisherId, "");
	}

	@Override
	public List<Project> searchProjects(long publisherId, String keyword) {
		return projectService.findPublisherProjects(publisherId, keyword);
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
