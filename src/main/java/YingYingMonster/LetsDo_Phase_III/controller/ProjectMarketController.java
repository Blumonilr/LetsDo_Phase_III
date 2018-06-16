package YingYingMonster.LetsDo_Phase_III.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import YingYingMonster.LetsDo_Phase_III.entity.Project;
import YingYingMonster.LetsDo_Phase_III.model.MarkMode;
import YingYingMonster.LetsDo_Phase_III.service.ProjectService;
import YingYingMonster.LetsDo_Phase_III.service.WorkerService;

@Controller
@RequestMapping("/market")
public class ProjectMarketController {
	@Autowired
	private WorkerService service;
	@Autowired
	private ProjectService pjservice;
	private static int num_of_one_page = 6;//一页展示6个project
	List<Project> projectList;
	/**
	 * @return 返回market的界面
	 */
	@GetMapping("")
	public String getMarket() {
	
		return "market/market";
	}
	
	@GetMapping("/detail")
	public String getMarketDetail() {                                                                                                                                                                                                                                                                                                                                                                                                                                            
	
		return "market/marketprojectdetail";
	}
	
	/**
	 * 进入内测入口界面
	 * @return
	 */
	@GetMapping("/makeanswer")
	public String makeAnswer() {
		return "answers/entrance";
	}
	
	/**
	 * @param userId
	 * @return 获得所有项目列表 pubid_pjid,pubid_pjid
	 */
	@GetMapping("/getList")
	@ResponseBody
	public String getList(HttpServletRequest request, HttpServletResponse response) {
		String res = "";
		String userId = request.getParameter("userId");
		long uid = Long.parseLong(userId);
		projectList = service.discoverProjects(uid);

		int len = projectList.size();
		for(int i=0;i<len;i++) {
			Project pj = projectList.get(i);
			String pj_discription = "";
			long pjId = pj.getId();
			String pjName = pj.getProjectName();
			int minLevel = pj.getWorkerMinLevel();
			long pubId = pj.getPublisherId();
			String endTime = pj.getEndDate();
			String type_disc = "";
			if(pj.getType() == MarkMode.AREA) {
				type_disc = "区域标注";
			}
			else if(pj.getType() == MarkMode.SQUARE) {
				type_disc = "矩形标注";
			}
			pj_discription = pjName+"_"+type_disc+"_"+minLevel+"_"+endTime+"_"+pjId;
			
			res = res+pj_discription;
			if(i != len-1) {
				res = res+",";
			}
		}
		
		System.out.println("PROJECT LIST: "+res);
		return res;
	}
	
	/**
	 * 获得项目的详细信息
	 * @param request
	 * @param response
	 * @return
	 */
	@GetMapping("/getdetail")
	@ResponseBody
	public String getProjectDetail(HttpServletRequest request, HttpServletResponse response) {
		String res = "";
		String pjId = request.getParameter("projectId");
		long projectId = Long.parseLong(pjId);
		
		Project pj = pjservice.getAProject(projectId);
		
		String type = "";
		String type_disc = "区域标注（temp）";
		if(pj.getType() == MarkMode.AREA) {
			type_disc = "区域标注";
			type = "area";
		}
		else if(pj.getType() == MarkMode.SQUARE) {
			type_disc = "矩形标注";
			type = "square";
		}
		String start_time = pj.getStartDate();
		String end_time = pj.getEndDate();
		int lowest_level = pj.getWorkerMinLevel();
		int payment = pj.getMoney();
		String requirement = pj.getTagRequirement();
		int people_in = pj.getCurrWorkerNum();//当前参与人数
		
		res = type_disc+"_"+start_time+"_"+end_time+"_"+lowest_level+"_"+payment+"_"+people_in+"_"+requirement+"_"+type;
		
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
	 * fork一个项目
	 * @param userId
	 * @param publisherId
	 * @param projectId
	 * @return  成功1  失败0
	 */
	@GetMapping("/fork/{userId}/{publisherId}/{projectId}")
	@ResponseBody
	public String fork(@PathVariable("userId")String userId,
			@PathVariable("publisherId")String publisherId,
			@PathVariable("projectId")String projectId) {
	//	int res = service.forkProject(userId, publisherId, projectId);
		String info = "";
//		if(res==0) {
//			info = "恭喜您获取成功，快去工作吧~";
//		}
//		else if(res==-1) {
//			info = "发生数据错误，请稍后重试";
//		}
//		else if(res==-2) {
//			info = "用户不存在";
//		}
//		else if(res==-3) {
//			info = "您已经选取过该项目啦，不能重复选取哦";
//		}
//		else if(res==-4) {
//			info = "项目已取消或不存在";
//		}
//		else if(res==-5) {
//			info = "啊哦，项目人数已满，下次要快一点啊";
//		}
//		else if(res==-6) {
//			info = "啊哦，您的等级不足，快快努力打怪升级吧~";
//		}
		return info;
	}
	
}



