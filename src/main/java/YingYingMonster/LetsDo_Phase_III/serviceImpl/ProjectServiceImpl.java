package YingYingMonster.LetsDo_Phase_III.serviceImpl;

import YingYingMonster.LetsDo_Phase_III.entity.Image;
import YingYingMonster.LetsDo_Phase_III.entity.Project;
import YingYingMonster.LetsDo_Phase_III.entity.Tag;
import YingYingMonster.LetsDo_Phase_III.entity.TextNode;
import YingYingMonster.LetsDo_Phase_III.entity.json.TagTreeNode;
import YingYingMonster.LetsDo_Phase_III.model.ProjectState;
import YingYingMonster.LetsDo_Phase_III.repository.ImageRepository;
import YingYingMonster.LetsDo_Phase_III.repository.TextNodeRepository;
import YingYingMonster.LetsDo_Phase_III.repository.event.JoinEventRepository;
import YingYingMonster.LetsDo_Phase_III.repository.ProjectRepository;
import YingYingMonster.LetsDo_Phase_III.service.ImageService;
import YingYingMonster.LetsDo_Phase_III.service.ProjectService;
import net.coobird.thumbnailator.Thumbnails;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
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

    @Autowired
    TextNodeRepository textNodeRepository;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public List<Project> viewAllProjects() {
        return projectRepository.findAll();
    }

    @Override
    public List<Project> viewAllOpenedProjects(List<String> list) {
        //list is sorted list of user's attributes...
        List<Project> projects = projectRepository.findAll().stream()
                .filter(x -> x.getProjectState() == ProjectState.open)
                .collect(Collectors.toList());

        List<Project> res = new ArrayList<>();
        for (String s : list) {
            for (Project project : projects) {
                if (project.getLabels().contains(s)) {
                    res.add(project);
                }
            }
        }
        for (Project project : projects) {
            if (!res.contains(project)) {
                res.add(project);
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
        logger.info("project service 开始");
        project.setProjectState(ProjectState.setup);
        project.setStartDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        project = projectRepository.saveAndFlush(project);

        int picNum = imageService.saveImages(multipartFile, project.getId(), false);
        project.setPicNum(picNum);
        logger.info("(project service) project ={}", project);
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
        if (prtemp.getProjectState() != ProjectState.setup
                && prtemp.getProjectState() != ProjectState.ready) {
            throw new IllegalStateException("project state not satisfied for publishment :"
                    + prtemp.getProjectState());
        }
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
    public void setProjectCustomTextNode(long projectId,String xmlFile) {
        Project project=projectRepository.findById(projectId);
        project.setTagTree(xmlFile);
        projectRepository.saveAndFlush(project);
    }

    @Override
    public List<TextNode> getProjectTextNode(long projectId) {
        Project prj=projectRepository.findById(projectId);
        JSONArray jsonArray = JSONArray.fromObject(prj.getTagTree());
        List<TextNode> nodes=new ArrayList<>();
        for (Object o : jsonArray) {
            JSONObject jsonObject2 = JSONObject.fromObject(o);
            TagTreeNode ob = (TagTreeNode) JSONObject.toBean(jsonObject2,TagTreeNode.class);
            List<TagTreeNode> children = ob.getChildren();
            if (children != null) {
                JSONArray array = JSONArray.fromObject(children);
                for (Object oc : array) {
                    JSONObject jsonObject3 = JSONObject.fromObject(oc);
                    TagTreeNode obc = (TagTreeNode) JSONObject.toBean(jsonObject3, TagTreeNode.class);
                    List<String> attributions=new ArrayList<>();
                    if(obc.getChildren()!=null){
                        JSONArray attri=JSONArray.fromObject(obc.getChildren());
                        for(Object oa:attri){
                            JSONObject jsonObject4 = JSONObject.fromObject(oa);
                            TagTreeNode oba = (TagTreeNode) JSONObject.toBean(jsonObject4, TagTreeNode.class);
                            StringBuilder builder=new StringBuilder();
                            builder.append(oba.getName()+":");
                            if(oba.getChildren()!=null){
                                JSONArray choices=JSONArray.fromObject(oba.getChildren());
                                for(int i=0;i<choices.size();i++){
                                    Object ch=choices.get(i);
                                    JSONObject jsonObject5=JSONObject.fromObject(ch);
                                    TagTreeNode obch=(TagTreeNode)JSONObject.toBean(jsonObject5,TagTreeNode.class);
                                    builder.append(obch.getName());
                                    if (i!=choices.size()-1){
                                        builder.append("_");
                                    }
                                }
                            }
                            attributions.add(builder.toString());
                        }
                    }
                    TextNode tn=new TextNode(obc.getName(),ob.getName(),true,attributions);
                    nodes.add(tn);
                }
            }
        }
        return nodes;
    }

    @Override
    public String getInitialTextNodeTree() {
        StringBuilder builder=new StringBuilder();
        builder.append("[");
        List<TextNode> fathers=textNodeRepository.findFathers();
        for (int k=0;k<fathers.size();k++){
            TextNode t=fathers.get(k);
            builder.append("{\"name\":\""+t.getName()+"\",");
            if (textNodeRepository.findByFather(t.getName())!=null){
                builder.append("\"children\":[");
                for (int i=0;i<textNodeRepository.findByFather(t.getName()).size();i++){
                    TextNode tc=textNodeRepository.findByFather(t.getName()).get(i);
                    builder.append("{\"name\":\""+tc.getName()+"\",\"children\":[");
                    for (int j=0;j<tc.getAttributions().size();j++){
                        String s=tc.getAttributions().get(j);
                        builder.append("{\"name\":\""+s.substring(0,s.indexOf(":"))+"\",\"children\":[");
                        String tmp=s.substring(s.indexOf(":")+1);
                        String[] choices=tmp.split("_");
                        for (int n=0;n<choices.length;n++){
                            String str=choices[n];
                            builder.append("{\"name\":\""+str+"\"}");
                            if (n!=choices.length-1)
                                builder.append(",");
                        }
                        builder.append("]}");
                        if (j!=tc.getAttributions().size()-1)
                            builder.append(",");
                    }
                    builder.append("]}");
                    if (i!=textNodeRepository.findByFather(t.getName()).size()-1)
                        builder.append(",");
                }
            }
            builder.append("]}");
            if (k!=fathers.size()-1)
                builder.append(",");
        }
        builder.append("]");
        return builder.toString();
    }

}
