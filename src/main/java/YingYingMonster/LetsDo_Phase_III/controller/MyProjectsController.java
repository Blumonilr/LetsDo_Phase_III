package YingYingMonster.LetsDo_Phase_III.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import YingYingMonster.LetsDo_Phase_III.model.Project;
import YingYingMonster.LetsDo_Phase_III.model.TagRequirement;
import YingYingMonster.LetsDo_Phase_III.service.WorkerService;

@Controller
@RequestMapping("/myProjects")
public class MyProjectsController {

	@Autowired
	private WorkerService service;
	
	/**
	 * 
	 * @return 返回展示界面
	 */
    @GetMapping("/projects")
    public String getPage() {
    	return "myProjects/projectDisplay";
    }
    
    /**
     * 
     * @param userId
     * @return 返回项目列表
     */
    @GetMapping("/getList/{userId}")
    @ResponseBody
    public String getList(@PathVariable("userId")String userId) {
    	
    	String res = "";
    	List<String> list = service.viewMyProjects(userId);
    	//pubid_projectid,
    	int len = list.size();
    	
    	for(int i=0 ; i<len ; i++) {
    		String publisherId = list.get(i).split("_")[0];
    		String projectId = list.get(i).split("_")[1];
    		if(!service.isPjFinished(userId, publisherId+"_"+projectId)) {
    			//获取描述
        		res += list.get(i);
        		int progress = service.viewProgress(userId, publisherId, projectId);
        	    Project project = service.getAProject(publisherId, projectId);
        	    TagRequirement requirement = project.getTagRequirement();
        	    String description = "";
        	    switch(requirement.getMarkMode()) {
        	        case ENTIRETY:	
        	        	description = "整体描述项目"+'\n';
        	        	break;
        	        case TAGS:
           	        	description = "标签描述项目"+'\n';
        	        	break;
        	        case AREA:
           	        	description = "区域覆盖标注项目"+'\n';
        	        	break;
        	        case RECTANGLE:
          	        	description = "部分圈选项目"+'\n';
        	        	break;
        	        }
        		res = res+"_"+ description+"_"+progress;
        		if(i != len-1) {
        			res = res + ",";
        		}
    		}
    			
    	}
//    	System.out.println("getList "+res);
    	return res;
    }
    
    @GetMapping("/getProject/{publisherId}/{projectId}")
    @ResponseBody
    public String getProject(@PathVariable("publisherId")String publisherId,
    		@PathVariable("projectId")String projectId) {
    	
    	String res = "";
    	String description = "";
        Project project = service.getAProject(publisherId, projectId);
        TagRequirement requirement = project.getTagRequirement();
        String type = "";
        switch(requirement.getMarkMode()) {
        case ENTIRETY:
        	type="total";
        	description = "整体描述"+'\n';
        	break;
        case TAGS:
        	type="tips";
        	description = "标签描述"+'\n';
        	break;
        case AREA:
        	type="area";
        	description = "区域覆盖标注"+'\n';
        	break;
        case RECTANGLE:
        	type="mark";
        	description = "部分圈选"+'\n';
        	break;
        }
        
        String req = requirement.getRequirement(); /*markMode是tags的时候，requirement为tag列表，tag之间以逗号隔开，其他模式都为具体要求*/
        System.out.println("requirement: "+req);
    	return type+":"+req ;
    }
    
    @ResponseBody
    @RequestMapping("/pushProject/{userId}/{publisherId}/{projectId}")
    public void pushProject(@PathVariable("userId")String userId,
    		@PathVariable("publisherId")String publisherId,
    		@PathVariable("projectId")String projectId) {
    	service.push(userId, publisherId, projectId);
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
    	
        Project project = service.getAProject(publisherId, projectId);
        TagRequirement requirement = project.getTagRequirement();
        
        String req = requirement.getRequirement(); /*markMode是tags的时候，requirement为tag列表，tag之间以逗号隔开，其他模式都为具体要求*/
     
    	return req ;
    }
    
}
