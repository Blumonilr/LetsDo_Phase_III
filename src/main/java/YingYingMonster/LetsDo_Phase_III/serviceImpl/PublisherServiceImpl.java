package YingYingMonster.LetsDo_Phase_III.serviceImpl;

import YingYingMonster.LetsDo_Phase_III.entity.Image;
import YingYingMonster.LetsDo_Phase_III.entity.Project;
import YingYingMonster.LetsDo_Phase_III.entity.Tag;
import YingYingMonster.LetsDo_Phase_III.entity.TestProject;
import YingYingMonster.LetsDo_Phase_III.entity.event.JoinEvent;
import YingYingMonster.LetsDo_Phase_III.entity.event.PublishEvent;
import YingYingMonster.LetsDo_Phase_III.entity.role.Administrator;
import YingYingMonster.LetsDo_Phase_III.entity.role.Worker;
import YingYingMonster.LetsDo_Phase_III.model.ProjectState;
import YingYingMonster.LetsDo_Phase_III.model.ZipCompressor;
import YingYingMonster.LetsDo_Phase_III.repository.ImageRepository;
import YingYingMonster.LetsDo_Phase_III.repository.ProjectRepository;
import YingYingMonster.LetsDo_Phase_III.repository.TestProjectRepository;
import YingYingMonster.LetsDo_Phase_III.repository.event.JoinEventRepository;
import YingYingMonster.LetsDo_Phase_III.repository.event.PublishEventRepository;
import YingYingMonster.LetsDo_Phase_III.service.*;
import de.innosystec.unrar.Archive;
import de.innosystec.unrar.exception.RarException;
import de.innosystec.unrar.rarfile.FileHeader;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.tools.ant.taskdefs.Zip;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

@Component
public class PublisherServiceImpl implements PublisherService {

	@Autowired
	ProjectService projectService;

	@Autowired
	TestProjectService testProjectService;

	@Autowired
	TagService tagService;

	@Autowired
	ImageService imageService;

	@Autowired
	PublishEventRepository publishEventRepository;
	@Autowired
	UserService userService;
	@Autowired
	JoinEventRepository joinEventRepository;

	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public Project createProject(Project project, MultipartFile dataSet) {
		Project project1 = projectService.addProject(project, dataSet);
		return project1;
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
		TestProject testProject = testProjectService.addTestProject(projectId, multipartFile);
		return testProject;
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
	public byte[] downloadTags(long projectId) throws Exception {
		Project pj=projectService.getAProject(projectId);
		File f=new File(System.getProperty("user.dir")+"/"+pj.getProjectName());
		System.out.println(System.getProperty("user.dir")+"/"+pj.getProjectName());
		System.out.println(pj.getProjectName());
		f.mkdirs();
		List<Image> images=imageService.findProjectAllImage(projectId);
		for (Image i:images) {
			List<Tag> tags = tagService.getImageTags(i.getId());
			for (Tag t:tags){
				if (t.isResult()) {
					saveTag(pj.getProjectName(), i.getId(), t);
					break;
				}
			}
		}
		File re=new File(System.getProperty("user.dir")+"\\"+pj.getProjectName()+".zip");
		new ZipCompressor(System.getProperty("user.dir")+"/"+pj.getProjectName()).compress(System.getProperty("user.dir")+"\\"+pj.getProjectName()+".zip");
		FileInputStream fis=new FileInputStream(re);
		ByteArrayOutputStream bos=new ByteArrayOutputStream(1024);
		byte[] b = new byte[1024*1024];
		int len = -1;
		while((len = fis.read(b)) != -1) {
			bos.write(b, 0, len);
		}
		byte[] results=bos.toByteArray();
		bos.close();
		return results;
	}

	@Override
	public double viewProjectProgress(long projectId) {
		List<Image> images = imageService.findProjectAllImage(projectId);
		int total = images.size();
		int finished = (int) images.stream().filter(x -> x.isFinished()).count();
		double progress = ((double) finished) / total*100;
		progress = ((double) Math.round(progress * 100))/100;  //保留两位小数
		return progress;
	}

	@Override
	public List<Worker> viewWorkers(long projectId) {
		return joinEventRepository.findByProjectId(projectId).stream()
				.map(x -> ((Worker) userService.getUser(x.getWorkerId())))
				.distinct().collect(Collectors.toList());
	}

	@Override
	public List<JoinEvent> viewJoinEvents(long projectId) {
		return joinEventRepository.findByProjectId(projectId);
	}

	@Override
	public List<String> getPublisherBiasInString(long publisherId) {
		return userService.getUserAbilities(publisherId).stream().map(x -> x.getLabel().getName()
				+ "_" + Integer.toString(x.getBias())).collect(Collectors.toList());
	}

	private void saveTag(String projectName,long imageId, Tag tag) throws IOException, DocumentException {
		String path1 = System.getProperty("user.dir") + "\\" + projectName + "\\im" + imageId + ".xml";
		File f1=new File(path1);
		f1.createNewFile();
		SAXReader reader=new SAXReader();
		Document document=reader.read(new ByteArrayInputStream(tag.getXmlFile().getBytes("UTF-8")));
		OutputFormat format=OutputFormat.createPrettyPrint();
		XMLWriter writer=new XMLWriter(new FileWriter(f1),format);
		writer.write(document);
		writer.close();

		String path2 = System.getProperty("user.dir") + "\\" + projectName + "\\im" + imageId + ".jpg";
        File f2=new File(path2);
		BufferedOutputStream bos = null;
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(f2);
			bos = new BufferedOutputStream(fos);
			bos.write(tag.getData());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bos != null) {
				try {
					bos.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

}
