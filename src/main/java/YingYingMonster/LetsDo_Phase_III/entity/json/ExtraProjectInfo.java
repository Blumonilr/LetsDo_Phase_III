package YingYingMonster.LetsDo_Phase_III.entity.json;

import YingYingMonster.LetsDo_Phase_III.entity.MarkMode;
import YingYingMonster.LetsDo_Phase_III.entity.Project;
import YingYingMonster.LetsDo_Phase_III.service.AdminService;
import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExtraProjectInfo {
    private AdminService adminService;
    @Expose
    private List<Project> projectList;
    @Expose
    private MapEntry[] projectType;
    @Expose
    private List<MapEntry> labelProp;
    private Map<String,Integer> labelPropMap;

    public ExtraProjectInfo(AdminService adminService) {
        this.adminService = adminService;
        this.projectList=adminService.viewAllProjects();
        getProjectType();
        getLabelProp();
    }

    private void getProjectType(){
        projectType=new MapEntry[2];
        int area=0;
        int square=0;
        for (Project p : projectList) {
            if (p.getType() == MarkMode.AREA){
                area++;
            }else {
                square++;
            }
        }
        projectType[0]=new MapEntry("区域标注",area);
        projectType[1]=new MapEntry("框选标注",square);
    }

    private void getLabelProp(){
        labelProp=new ArrayList<>();
        labelPropMap =new HashMap<>();
        List<String> labelList;
        for (Project p : projectList) {
            labelList=p.getLabels();
            for (String label : labelList) {
                if (labelPropMap.containsKey(label)){
                    labelPropMap.put(label, labelPropMap.get(label)+1);
                }else {
                    labelPropMap.put(label,1);
                }
            }
        }
        for (Map.Entry<String, Integer> entry : labelPropMap.entrySet()) {
            labelProp.add(new MapEntry(entry));
        }
    }

    public AdminService getAdminService() {
        return adminService;
    }

    public void setAdminService(AdminService adminService) {
        this.adminService = adminService;
    }

    public List<Project> getProjectList() {
        return projectList;
    }

    public void setProjectList(List<Project> projectList) {
        this.projectList = projectList;
    }

    public void setProjectType(MapEntry[] projectType) {
        this.projectType = projectType;
    }

    public void setLabelProp(List<MapEntry> labelProp) {
        this.labelProp = labelProp;
    }

    public Map<String, Integer> getLabelPropMap() {
        return labelPropMap;
    }

    public void setLabelPropMap(Map<String, Integer> labelPropMap) {
        this.labelPropMap = labelPropMap;
    }
}
