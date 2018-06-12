package YingYingMonster.LetsDo_Phase_III.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;

import YingYingMonster.LetsDo_Phase_III.entity.Project;
import YingYingMonster.LetsDo_Phase_III.model.MarkMode;
import YingYingMonster.LetsDo_Phase_III.model.TagRequirement;
import YingYingMonster.LetsDo_Phase_III.service.ProjectService;
import YingYingMonster.LetsDo_Phase_III.service.WorkerService;

@Controller
@RequestMapping("/myProjects")
public class MyProjectsController {

	@Autowired
	private WorkerService service;
	@Autowired
	private ProjectService pjservice;
	
	/**
	 * 
	 * @return 返回展示界面
	 */
    @GetMapping("/projects")
    public String getPage() {
    	return "myProjects/projectDisplay";
    }
    
    
    @GetMapping("/projects/detail")
    public String getProjectDetailPage() {
    	
    	
    	
    	return "myProjects/projectdetail";
    	
    }
    
    /**  DONE
     * @param userId
     * @return 返回已参加项目列表
     */
    @GetMapping("/getList/{userId}")
    @ResponseBody
    public String getList(@PathVariable("userId")String uId,
    		HttpServletRequest request, HttpServletResponse response) {
    	long userId = Long.parseLong(uId);
    	String key = request.getParameter("key");//模糊查找
    	
    	String res = "";
    	List<Project> list = service.viewMyProjects(userId,key);
//    	//pjid_pjname_pubid_type
    	int len = list.size();
    	for(int i=0;i<len;i++) {
    		Project project = list.get(i);
    		String tip = project.getId()+"_"+project.getProjectName()+"_"+project.getPublisherId();
    		String description = "";
    		if(project.getType() == MarkMode.AREA) {
    			description = "区域覆盖项目";
    		}
    		else if(project.getType() == MarkMode.SQUARE) {
    			description = "方框框选项目";
    		}
    		res += tip;
    		if(i != len-1) {
    			res += ",";
    		}
    	}
    	return res;
    }
    
    @GetMapping("/getProject/{publisherId}/{projectId}")
    @ResponseBody//这个方法是projectdisplay界面在用，返回的是比较简单的信息
    public String getProject(@PathVariable("publisherId")String publisherId,
    		@PathVariable("projectId")String projectId) {
    	
//    	String description = "";
//        Project project = service.getAProject(publisherId, projectId);
//        TagRequirement requirement = project.getTagRequirement();
//        String type = "";
//        switch(requirement.getMarkMode()) {
//			case SQUARE:
//        	type="square";
//        	description = "框选"+'\n';
//        	break;
//        case AREA:
//        	type="area";
//        	description = "区域覆盖标注"+'\n';
//        	break;
//        }
//        
//        String req = requirement.getRequirement(); /*markMode是tags的时候，requirement为tag列表，tag之间以逗号隔开，其他模式都为具体要求*/
//        System.out.println("requirement: "+req);
//    	return type+":"+req ;
    	return "";
    }
    
    
    /**
     * 这个方法是projectdetail界面使返回详细信息
     * @param publisherId
     * @param projectId
     * @return
     */
    @GetMapping("/getProjectDetail/{publisherId}/{projectId}")
    @ResponseBody
    public String getProjectDetail(@PathVariable("publisherId")String publisherId,
    		@PathVariable("projectId")String projectId) {
    	String condition = "b";//调用方法
    	String type = "area";
    	String progress = "60";
    	String requirement = "这里是项目要求";
    	/**
    	 * condition_type_progress_requirement
    	 */
    	String res = condition+"_"+type+"_"+progress+"_"+requirement;
    	
    	return res;
    }
    
    
    /**
     * 结束一个项目
     * @param request
     * @param response
     */
    @ResponseBody
    @RequestMapping("/terminateProject")
    public void terminateProject(HttpServletRequest request, HttpServletResponse response) {
    	String uid = request.getParameter("userId");
    	String pjid = request.getParameter("projectId");
    	
    	long userId = Long.parseLong(uid);
    	long projectId = Long.parseLong(pjid);
    	
    	service.quitProject(userId, projectId);
    }
    
    
    /**
     * 为文字性要求的标记要求服务  area mark
     * @param publisherId
     * @param projectId
     * @return
     */
    @GetMapping("/getProjectRequirement/{publisherId}/{projectId}")
    @ResponseBody
    public String getProjectRequirement(@PathVariable("publisherId")String publisherId,
    		@PathVariable("projectId")String projectId) {
    	
//        Project project = service.getAProject(publisherId, projectId);
//        TagRequirement requirement = project.getTagRequirement();
//        
//        String req = requirement.getRequirement(); /*markMode是tags的时候，requirement为tag列表，tag之间以逗号隔开，其他模式都为具体要求*/
//     
//    	return req ;
    	return "";
    }
    
    
    @GetMapping("/getProjectOverview/{projectId}")
    @ResponseBody
    public String getProjectOverviewPicture(@PathVariable("projectId")String projectId) {
//    	long pjId = Long.parseLong(projectId);
//    	String url;
//		try {
//			url = pjservice.getProjectOverview(pjId);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			return "";
//		}
//    	return url;
    	return "";
    }
    
}
