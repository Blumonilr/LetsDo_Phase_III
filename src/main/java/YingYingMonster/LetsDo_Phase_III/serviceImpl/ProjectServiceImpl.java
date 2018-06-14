package YingYingMonster.LetsDo_Phase_III.serviceImpl;

import YingYingMonster.LetsDo_Phase_III.entity.Project;
import YingYingMonster.LetsDo_Phase_III.entity.TextNode;
import YingYingMonster.LetsDo_Phase_III.model.JsonOb;
import YingYingMonster.LetsDo_Phase_III.model.ProjectState;
import YingYingMonster.LetsDo_Phase_III.repository.ImageRepository;
import YingYingMonster.LetsDo_Phase_III.repository.event.JoinEventRepository;
import YingYingMonster.LetsDo_Phase_III.repository.ProjectRepository;
import YingYingMonster.LetsDo_Phase_III.service.ImageService;
import YingYingMonster.LetsDo_Phase_III.service.ProjectService;
import net.coobird.thumbnailator.Thumbnails;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class ProjectServiceImpl implements ProjectService  {

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    ImageService imageService;

    @Autowired
    JoinEventRepository joinEventRepository;

    @Override
    public List<Project> viewAllProjects() {
        return projectRepository.findAll();
    }

    @Override
    public List<Project> viewAllProjects(List<String> list) {
        //list is sorted list of user's attributes...
        List<Project> projects = projectRepository.findAll();
//        Set<Project> res = new HashSet<>();
        List<Project> res = new ArrayList<>();
        for (String s : list) {
            for (Project project : projects) {
                if (project.getLabels().contains(s)) {
                    res.add(project);
                }
            }
        }
        return res.stream().distinct().collect(Collectors.toList());
    }

    @Override
    public List<Project> findWorkerProjects(long workerId, String key) {
        return joinEventRepository.findByWorkerId(workerId).stream()
                .map(x -> projectRepository.findById(x.getProjectId()))
                .filter(x -> (x.getProjectName().contains(key) || x.getTagRequirement().contains(key)))
                .collect(Collectors.toList());
    }

    @Override
    public List<Project> findPublisherProjects(long publisherId, String key) {
        return projectRepository.findByPublisherIdAndStringAttributes(publisherId, key);
    }

    @Override
    public Project getAProject(long projectId) {
        return projectRepository.findById(projectId);
    }

    @Override
    public boolean validateProjectName(long publisherId, String projectName) {
        List<Project> list = projectRepository.findByPublisherId(publisherId);
        for (Project project : list) {
            if (project.getProjectName().equals(projectName)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public Project addProject(Project project, MultipartFile multipartFile) {
        project.setProjectState(ProjectState.setup);
        project.setStartDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        project = projectRepository.saveAndFlush(project);

        int picNum = imageService.saveImages(multipartFile, project.getId(), false);
        project.setPicNum(picNum);

        return projectRepository.saveAndFlush(project);
    }

    @Override
    public Project initializeProject(long projectId) {
        Project project = projectRepository.findById(projectId);
        project.setProjectState(ProjectState.initialize);
        return projectRepository.saveAndFlush(project);
    }

    @Override
    public Project openProject(long projectId) {
        Project prtemp = projectRepository.findById(projectId);
        prtemp.setProjectState(ProjectState.open);
        return projectRepository.saveAndFlush(prtemp);
    }

    @Override
    public Project closeProject(long projectId) {
        Project prtemp = projectRepository.findById(projectId);
        prtemp.setProjectState(ProjectState.closed);
        return projectRepository.saveAndFlush(prtemp);
    }

    @Override
    public byte[] getProjectOverview(long projectId) throws IOException {
        byte[] data = null;
        String path = System.getProperty("user.dir")+"/projectOverview/pj" + projectId + ".jpg";
        File file = new File(path);
        if (file.exists()){
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(Thumbnails.of(file).scale(1.0).asBufferedImage(), "jpg", byteArrayOutputStream);
            data = byteArrayOutputStream.toByteArray();
        }
        return data;
    }

    @Override
    public void setProjectCustomTextNode(long projectId,String tagTree) {
        Project project=projectRepository.findById(projectId);
        project.setTagTree(tagTree);
        projectRepository.saveAndFlush(project);
    }

    @Override
    public List<TextNode> getProjectTextNode(long projectId) {
        Project prj=projectRepository.findById(projectId);
        JSONArray jsonArray = JSONArray.fromObject(prj.getTagTree());
        List<TextNode> nodes=new ArrayList<>();
        for (Object o : jsonArray) {
            JSONObject jsonObject2 = JSONObject.fromObject(o);
            JsonOb ob = (JsonOb) JSONObject.toBean(jsonObject2,JsonOb.class);
            List<JsonOb> children = ob.getChildren();
            if (children != null) {
                JSONArray array = JSONArray.fromObject(children);
                for (Object oc : array) {
                    JSONObject jsonObject3 = JSONObject.fromObject(oc);
                    JsonOb obc = (JsonOb) JSONObject.toBean(jsonObject3, JsonOb.class);
                    List<String> attributions=new ArrayList<>();
                    if(obc.getChildren()!=null){
                        JSONArray attri=JSONArray.fromObject(obc.getChildren());
                        for(Object oa:attri){
                            JSONObject jsonObject4 = JSONObject.fromObject(oa);
                            JsonOb oba = (JsonOb) JSONObject.toBean(jsonObject4, JsonOb.class);
                            attributions.add(oba.getName());
                        }
                    }
                    TextNode tn=new TextNode(obc.getName(),ob.getName(),true,attributions);
                    nodes.add(tn);
                }
            }
        }
        return nodes;
    }


}
