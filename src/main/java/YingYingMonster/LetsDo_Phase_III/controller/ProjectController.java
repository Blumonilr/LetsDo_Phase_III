package YingYingMonster.LetsDo_Phase_III.controller;

import YingYingMonster.LetsDo_Phase_III.service.ProjectService;
import YingYingMonster.LetsDo_Phase_III.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import YingYingMonster.LetsDo_Phase_III.entity.Project;
import YingYingMonster.LetsDo_Phase_III.service.AdminService;
import YingYingMonster.LetsDo_Phase_III.service.PublisherService;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/project")
public class ProjectController {
    @Autowired
    PublisherService publisherService;
    @Autowired
    AdminService adminService;
    @Autowired
    UserService userService;
    @Autowired
    ProjectService projectService;

    @GetMapping("/publisherProjects")
    public String projects() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        String userId = (String) session.getAttribute("userId");
        if (userId != null) {
            //已登录
            return "projects/publisherProjects";
        } else {
            //如果未登录返回登录页面
            return "redirect:/user/login";
        }
    }
    /**
    * 查找当前发布的项目列表
    * @param keyword 关键字，可以为空
    */
    @PostMapping("/publisherProjects")
    @ResponseBody
    public String queryProjects(@RequestParam("keyword") String keyword) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        String userId = (String) session.getAttribute("userId");
        List<Project> temp = publisherService.searchProjects(Long.parseLong(userId), keyword);
        String result = "";
        for (int i = 0; i < temp.size(); i++) {
            try {
                if (i == temp.size() - 1) {
                    result += temp.get(i).getProjectName() + "_" + userService.getUser(temp.get(i).getPublisherId()).getName() + "_" + temp.get(i).getTagRequirement() + "_" + temp.get(i).getId() + "_" + projectService.getProjectOverview(temp.get(i).getId());
                } else
                    result += temp.get(i).getProjectName() + "_" + userService.getUser(temp.get(i).getPublisherId()).getName() + "_" + temp.get(i).getTagRequirement() + "_" + temp.get(i).getId() + "_" + projectService.getProjectOverview(temp.get(i).getId()) + "+";
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 正在进行的项目列表，用于管理员系统
     */
    @PostMapping("/onGoingProjects")
    @ResponseBody
    public String onGoingProjects(){
//        List<Project> temp= null;
//        try {
//            temp = adminService.viewProjectOnDuty();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        String result="";
//        for(int i=0;i<temp.size();i++){
//            if(i==temp.size()-1)
//                result+=temp.get(i).getProjectId()+"_"+temp.get(i).getPublisherId()+"_"+temp.get(i).getTagRequirement();
//            else
//                result+=temp.get(i).getProjectId()+"_"+temp.get(i).getPublisherId()+"_"+temp.get(i).getTagRequirement()+"/";
//        }
//        return result;
        return "";
    }

    /**
     * 未开始的项目列表，用于管理员系统
     */
    @PostMapping("/doneProjects")
    @ResponseBody
    public String doneProjects(){
//        List<Project> temp= null;
//        try {
//            temp = adminService.viewProjectDone();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        String result="";
//        for(int i=0;i<temp.size();i++){
//            if(i==temp.size()-1)
//                result+=temp.get(i).getProjectId()+"_"+temp.get(i).getPublisherId()+"_"+temp.get(i).getTagRequirement();
//            else
//                result+=temp.get(i).getProjectId()+"_"+temp.get(i).getPublisherId()+"_"+temp.get(i).getTagRequirement()+"/";
//        }
//        return result;
        return "";
    }

    //请求项目详情页面
    @GetMapping("/publisher/projectDetail")
    public String projectDetailPage(){
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        String userId=(String)session.getAttribute("userId");
        if(userId!=null){
            //已登录
            return "projects/publisherProjectDetail";
        } else {
            //如果未登录返回登录页面
            return "redirect:/user/login";
        }
    }

    //管理员请求项目详情页面
    @GetMapping("/publisher/adminDetail")
    public String adminDetailPage(){
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        String userId=(String)session.getAttribute("userId");
        if(userId!=null){
            //已登录
            return "projects/adminProjectDetail";
        } else {
            //如果未登录返回登录页面
            return "redirect:/user/login";
        }
    }

    //异步获取项目详情
    @PostMapping("/publisher/{projectId}")
    @ResponseBody
    public String projectDetail(@PathVariable("projectId") String projectId){
        System.out.println("进入了查看项目详情方法");
        String result="{";
        Project project=publisherService.getAProject(Long.parseLong(projectId));
        result+="\"projectName\":\""+project.getProjectName()+"\",";
        result+="\"publisherName\":\""+userService.getUser(project.getPublisherId()).getName()+"\",";
        result+="\"markMode\":\""+project.getType().toString()+"\",";
        result+="\"maxNumPerPic\":\""+project.getMaxNumPerPic()+"\",";
        result+="\"minNumPerPic\":\""+project.getMinNumPerPic()+"\",";
        result+="\"startDate\":\""+project.getStartDate()+"\",";
        result+="\"endDate\":\""+project.getEndDate()+"\",";
        result+="\"levelLimit\":\""+project.getWorkerMinLevel()+"\",";
        result+="\"testAccuracy\":\""+project.getTestAccuracy()+"\",";
        result+="\"money\":\""+project.getMoney()+"\"";
        result+="}*";
        result+=project.getTagRequirement();
        return result;
    }

}
