package YingYingMonster.LetsDo_Phase_III.entity.json;

import YingYingMonster.LetsDo_Phase_III.entity.MarkMode;
import YingYingMonster.LetsDo_Phase_III.entity.Project;
import YingYingMonster.LetsDo_Phase_III.service.AdminService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class extraProjectInfo {
    private AdminService adminService;
    private List<Project> projectList;

    private Map<String,Integer> projectType;
    private Map<String,Integer> labelProp;

    public extraProjectInfo(AdminService adminService) {
        this.adminService = adminService;
        this.projectList=adminService.viewAllProjects();
        projectType=new HashMap<>();
    }

    private void getProjectType(){
        int area=0;
        int square=0;
        for (Project p : projectList) {
            if (p.getType() == MarkMode.AREA){
                area++;
            }else {
                square++;
            }
        }
        projectType.put("区域标注",area);
        projectType.put("框选标注",square);
    }

    private void getLabelProp(){

    }
}
