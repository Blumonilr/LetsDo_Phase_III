package YingYingMonster.LetsDo_Phase_III.service;

import YingYingMonster.LetsDo_Phase_III.entity.Project;
import YingYingMonster.LetsDo_Phase_III.entity.TestProject;
import YingYingMonster.LetsDo_Phase_III.entity.TextNode;
import YingYingMonster.LetsDo_Phase_III.entity.role.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProjectService {

    public List<Project> viewAllProjects();

    public List<Project> viewAllProjects(List<String> list);

    public List<Project> findWorkerProjects(long workerId, String key);

    public List<Project> findPublisherProjects(long publisherId, String key);

    public Project getAProject(long projectId);

    public boolean validateProjectName(long publisherId, String projectName);

    public Project addProject(Project project, MultipartFile multipartFile);

    public Project initializeProject(long projectId);

    public Project openProject(long projectId);
    
    public Project closeProject(long projectId);

    public byte[] getProjectOverview(long projectId) throws IOException;

    public void setProjectCustomTextNode(long projectId,String xmlFile);

    public List<TextNode> getProjectTextNode(long projectId);
}
