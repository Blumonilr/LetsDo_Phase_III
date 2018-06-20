package YingYingMonster.LetsDo_Phase_III.entity.json;

import YingYingMonster.LetsDo_Phase_III.entity.MarkMode;
import YingYingMonster.LetsDo_Phase_III.entity.Project;
import YingYingMonster.LetsDo_Phase_III.service.AdminService;
import com.google.gson.annotations.Expose;

import java.text.ParseException;
import java.util.*;

public class ExtraProjectInfo {
    private AdminService adminService;
    @Expose
    private List<Project> projectList;
    @Expose
    private MapEntry[] projectType;
    @Expose
    private List<MapEntry> labelProp;
    private Map<String,Integer> labelPropMap;
    @Expose
    private ArrayList<ArrayList<Integer>> payment;
    @Expose
    private String[] month;

    public ExtraProjectInfo(AdminService adminService) throws ParseException {
        this.adminService = adminService;
        this.projectList=adminService.viewAllProjects();
        getProjectType();
        getLabelProp();
        setPayment();
    }

    private void setPayment() throws ParseException {
        payment=new ArrayList<>();
        for(int i=6;i>0;i--) {
            payment.add(new ArrayList<>());
        }
        month=new String[6];
        Calendar temp=Calendar.getInstance();
        temp.set(Calendar.DAY_OF_MONTH, 1);  //设置日期
        List<Project> projects;
        for(int i=6;i>0;i--) {
            month[i-1]=i+"月";
            projects=adminService.projectStart(temp);
            for (Project p :
                    projects) {
                payment.get(i - 1).add(p.getMoney());
            }
            temp.add(Calendar.MONTH, -1);
        }
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
