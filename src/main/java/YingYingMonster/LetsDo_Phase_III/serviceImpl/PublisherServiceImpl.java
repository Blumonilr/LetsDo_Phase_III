package YingYingMonster.LetsDo_Phase_III.serviceImpl;

import YingYingMonster.LetsDo_Phase_III.entity.Image;
import YingYingMonster.LetsDo_Phase_III.entity.Project;
import YingYingMonster.LetsDo_Phase_III.entity.Tag;
import YingYingMonster.LetsDo_Phase_III.entity.TestProject;
import YingYingMonster.LetsDo_Phase_III.model.ProjectState;
import YingYingMonster.LetsDo_Phase_III.repository.ImageRepository;
import YingYingMonster.LetsDo_Phase_III.repository.ProjectRepository;
import YingYingMonster.LetsDo_Phase_III.repository.TestProjectRepository;
import YingYingMonster.LetsDo_Phase_III.service.*;
import de.innosystec.unrar.Archive;
import de.innosystec.unrar.exception.RarException;
import de.innosystec.unrar.rarfile.FileHeader;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
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
		FileOutputStream fos2 = new FileOutputStream(re);
		compress(f,new ZipOutputStream(fos2),f.getName());
		FileInputStream fis=new FileInputStream(re);
		ByteArrayOutputStream bos=new ByteArrayOutputStream(1024);
		byte[] b = new byte[1024*1024];
		int len = -1;
		while((len = fis.read(b)) != -1) {
			bos.write(b, 0, len);
		}
		byte[] results=bos.toByteArray();

		return results;
	}

	@Override
	public double viewProjectProgress(String publisherId, String projectId) {
		return 0;
	}

	@Override
	public List<String> viewWorkers(String publisherId, String projectId) {
		return null;
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

	private void compress(File sourceFile,ZipOutputStream zos,String name)throws Exception{
		byte[] buf = new byte[2*1024];
		if(sourceFile.isFile()){
			// 向zip输出流中添加一个zip实体，构造器中name为zip实体的文件的名字
			zos.putNextEntry(new ZipEntry(name));
			// copy文件到zip输出流中
			int len;
			FileInputStream in = new FileInputStream(sourceFile);
			while ((len = in.read(buf)) != -1){
				zos.write(buf, 0, len);
			}
			// Complete the entry
			zos.closeEntry();
			in.close();
		} else {
			File[] listFiles = sourceFile.listFiles();
			if(listFiles == null || listFiles.length == 0){
				// 需要保留原来的文件结构时,需要对空文件夹进行处理
				// 空文件夹的处理
				zos.putNextEntry(new ZipEntry(name + "\\"));
				// 没有文件，不需要文件的copy
				zos.closeEntry();
		}else {
				for (File file : listFiles) {
					// 注意：file.getName()前面需要带上父文件夹的名字加一斜杠,
					// 不然最后压缩包中就不能保留原来的文件结构,即：所有文件都跑到压缩包根目录下了
					compress(file, zos, name + "\\" + file.getName());
				}
				}
			}
		}

}
