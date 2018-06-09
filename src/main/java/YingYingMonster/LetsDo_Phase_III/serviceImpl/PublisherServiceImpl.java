package YingYingMonster.LetsDo_Phase_III.serviceImpl;

import YingYingMonster.LetsDo_Phase_III.entity.Image;
import YingYingMonster.LetsDo_Phase_III.entity.Project;
import YingYingMonster.LetsDo_Phase_III.entity.TestProject;
import YingYingMonster.LetsDo_Phase_III.model.ProjectState;
import YingYingMonster.LetsDo_Phase_III.repository.ImageRepository;
import YingYingMonster.LetsDo_Phase_III.repository.ProjectRepository;
import YingYingMonster.LetsDo_Phase_III.repository.TestProjectRepository;
import YingYingMonster.LetsDo_Phase_III.service.PublisherService;
import de.innosystec.unrar.Archive;
import de.innosystec.unrar.exception.RarException;
import de.innosystec.unrar.rarfile.FileHeader;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Component
public class PublisherServiceImpl implements PublisherService {

	@Autowired
	ProjectRepository pjrepository;
	@Autowired
	ImageRepository imrepository;
	@Autowired
	TestProjectRepository tsrepository;


	@Override
	public Project createProject(Project project, MultipartFile dataSet) {
        project.setProjectState(ProjectState.setup);
        project.setStartDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        project = pjrepository.saveAndFlush(project);

		int picNum = unzipFile(dataSet, project.getId(), false);
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
    public TestProject addTestProject(long projectId,  MultipartFile multipartFile) {

		TestProject testProject = new TestProject();
		Project project = pjrepository.findById(projectId);
		testProject.setMarkMode(project.getType());

        int picNum = unzipFile(multipartFile, projectId,true);
        testProject.setPicNum(picNum);
		testProject.setInviteCode(generateUUID());
		testProject = tsrepository.saveAndFlush(testProject);
        pjrepository.updateTestProject(projectId, testProject);

        return testProject;

    }

    public String generateUUID(){
		String uuid = UUID.randomUUID().toString().replaceAll("-", "");
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 8; i++) {
			int index = (int) (Math.random() * 4);
			sb.append(uuid.substring(i, i + 4).charAt(index));
		}

		return sb.toString();
	}

	@Override
	public Project openProject(long id) {
		Project prtemp=pjrepository.findById(id);
		prtemp.setProjectState(ProjectState.open);
		return  pjrepository.saveAndFlush(prtemp);
	}

	@Override
	public Project closeProject(long id) {
		return null;
	}

	@Override
	public Project getAProject(long projectId) {

		return pjrepository.findById(projectId);
	}

	@Override
	public List<Project> findProjectByPublisherId(long publisherId) {

		return pjrepository.findByPublisherId(publisherId);
	}

	@Override
	public List<Project> searchProjects(long publisherId, String keyword) {
		return pjrepository.findByPublisherIdAndStringAttributes(publisherId, keyword);
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

	private int unzipFile(MultipartFile multipartFile,long projectId,boolean isTest){
		String packageName = multipartFile.getOriginalFilename();                    //上传的包名
		int picNum=0;

		if(packageName.matches(".*\\.zip")){                //是zip压缩文件
			try{
				ZipInputStream zs = new ZipInputStream(multipartFile.getInputStream());
				BufferedInputStream bs = new BufferedInputStream(zs);
				ZipEntry ze;
				byte[] picture = null;
				while((ze = zs.getNextEntry()) != null){                    //获取zip包中的每一个zip file entry
					String fileName = ze.getName();                            //pictureName
					if(!(fileName.endsWith(".jpg")||fileName.endsWith(".png")||fileName.endsWith(".JPG")||fileName.endsWith(".PNG")))
						continue;
					picture = new byte[(int) ze.getSize()];                    //读一个文件大小
					bs.read(picture, 0, (int) ze.getSize());
					Image image = new Image(projectId, picture, 1, 1, 0, false, isTest); //保存image

					BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(picture));
					image.setWidth(bufferedImage.getWidth());
					image.setHeight(bufferedImage.getHeight());

					imrepository.saveAndFlush(image);
					picNum++;
				}
				bs.close();
				zs.close();
			}catch(IOException e){
				e.printStackTrace();
			}
		}else if(packageName.matches(".*\\.rar")){                    //是rar压缩文件
			try {
				//MultipartFile file 转化为File 有临时文件产生：
				CommonsMultipartFile cf= (CommonsMultipartFile) multipartFile;
				DiskFileItem fi = (DiskFileItem)cf.getFileItem();
				File fs = fi.getStoreLocation();
				Archive archive = new Archive(fs);
				ByteArrayOutputStream bos = null;
				byte[] picture = null;
				FileHeader fh = archive.nextFileHeader();
				while(fh!=null){
					String fileName = fh.getFileNameString();
					if(!(fileName.endsWith(".jpg")||fileName.endsWith(".png")||fileName.endsWith(".JPG")||fileName.endsWith(".PNG")))
						continue;
					bos = new ByteArrayOutputStream();
					archive.extractFile(fh, bos);
					picture = bos.toByteArray();
					Image image = new Image(projectId, picture, 1, 1, 0, false, isTest); //保存image，非缩略图

					BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(picture));
					image.setWidth(bufferedImage.getWidth());
					image.setHeight(bufferedImage.getHeight());

					imrepository.saveAndFlush(image);
					picNum++;
					fh = archive.nextFileHeader();
				}

				bos.close();
				archive.close();
			} catch (RarException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		return picNum;
	}
}
