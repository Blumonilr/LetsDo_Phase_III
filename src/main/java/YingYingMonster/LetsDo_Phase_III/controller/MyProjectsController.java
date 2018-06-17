package YingYingMonster.LetsDo_Phase_III.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import YingYingMonster.LetsDo_Phase_III.entity.event.JoinEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import YingYingMonster.LetsDo_Phase_III.entity.Project;
import YingYingMonster.LetsDo_Phase_III.entity.MarkMode;
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
    @GetMapping("/getList")
    @ResponseBody
    public String getList(HttpServletRequest request, HttpServletResponse response) {
    	String uid = request.getParameter("userId");
    	long userId = Long.parseLong(uid);
    	String key = request.getParameter("key");//模糊查找
    	
    	String res = "";
    	List<Project> list = service.viewMyActiveProjects(userId,key);
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
    		res = res + tip+"_"+description;
    		
    		if(i != len-1) {
    			res += ",";
    		}
    	}
    	return res;
    }

    /**
	 * 获得project的缩略图
	 * @param request
	 * @param response
	 * @param prtId
	 * @throws IOException
	 */
	 @RequestMapping("/getProjectOverview/{projectId}")  
	 @ResponseBody  
	 public void setProjectOverviewPicture(HttpServletRequest request, HttpServletResponse response,
			@PathVariable("projectId")String prtId) throws IOException {
		long projectId = Long.parseLong(prtId);
		byte[] data ;
		try {
			data = pjservice.getProjectOverview(projectId);
			String JPG="image/jpeg;charset=UTF-8"; 
			OutputStream outputStream = response.getOutputStream();  
			response.setContentType(JPG);  
	        outputStream.write(data);  
	        outputStream.flush();   
	        outputStream.close();  
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
    
    /**
     * 这个方法是projectdetail界面使返回详细信息
     * @param publisherId
     * @param projectId
     * @return
     */
    @GetMapping("/getProjectDetail")
    @ResponseBody
    public String getProjectDetail(HttpServletRequest request, HttpServletResponse response) {
    	String uid = request.getParameter("userId");
    	long userId = Long.parseLong(uid);
    	String pjid = request.getParameter("projectId");
    	long projectId = Long.parseLong(pjid);
    	
    	Project pj = service.getAProject(projectId);
    	
    	String condition = "";
    	
    	String workingState = service.getWorkingState(userId, projectId);
    	System.out.println("WORKING STATE: "+workingState);
    	if(workingState.equals(JoinEvent.WORKING)) {//工作中
    		condition = "b";
    	}
    	else if(workingState.equals(JoinEvent.WORK_Finished)) {//工作结束
    		condition = "c";
    	}
    	else if(workingState.equals(JoinEvent.TEST_FINISHED)) {//等待评判
    		condition = "ax";
    	}
    	else {//还没考试或者考试没通过
    		condition = "a";
    	}
    	
    	condition = "a";//暂时！！！！！
    	
    	String type = "";
    	String type_disc = "";
    	if(pj.getType() == MarkMode.AREA) {
    		type = "area";
    		type_disc = "区域标注";
    	}
    	else {
    		type = "square";
    		type_disc = "矩形标注";
    	}
    	
    	String requirement = pj.getTagRequirement();
    	double examScore = service.getTestResult(userId, projectId);
    	
    	String res = condition+"_"+type+"_"+requirement+"_"+type_disc+"_"+examScore;
    	System.out.println("DETAIL: "+res);
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
}
