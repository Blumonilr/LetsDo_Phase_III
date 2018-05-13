package YingYingMonster.LetsDo_Phase_III.controller;

import java.util.ArrayList;

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
@RequestMapping("/market")
public class ProjectMarketController {
	@Autowired
	private WorkerService service;
	
	/**
	 * @return 返回market的界面
	 */
	@GetMapping("")
	public String getMarket() {
	
		return "market/market";
	}
	
	/**
	 * @param userId
	 * @return 获得所有项目列表 pubid_pjid,pubid_pjid
	 */
	@GetMapping("/getList/{userId}")
	@ResponseBody
	public String getList(@PathVariable("userId")String userId) {
		String res = "";
		ArrayList<String> list = (ArrayList<String>) service.viewAllProjects();
		int len = list.size();
		for(int i=0 ; i<len ; i++) {//pubid_pjid
			
			//加描述
			String pubid = list.get(i).split("_")[0];
			String pjid = list.get(i).split("_")[1];
			Project pj = service.getAProject(pubid, pjid);
			String startDate = pj.getStartDate();
			String endDate = pj.getEndDate();
			String type = "";
			TagRequirement requirement = pj.getTagRequirement();
			switch(requirement.getMarkMode()) {
	        case ENTIRETY:	
	        	type = "整体描述项目";
	        	break;
	        case TAGS:
   	        	type = "标签描述项目";
	        	break;
	        case AREA:
   	        	type = "区域覆盖标注项目";
	        	break;
	        case RECTANGLE:
  	        	type = "部分圈选项目";
	        	break;
	        }
			
			String workerLevel = pj.getWorkerRequirement().getLevelLimit()+"";
			
			res = res + pubid+"_"+pjid+"_"+startDate+"_"+endDate+"_"+type+"_"+workerLevel;
			
			if(i!=len-1) {
				res = res + ",";
			}
			
		}
		System.out.println(res);
		return res;
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
		int res = service.forkProject(userId, publisherId, projectId);
		String info = "";
		if(res==0) {
			info = "恭喜您获取成功，快去工作吧~";
		}
		else if(res==-1) {
			info = "发生数据错误，请稍后重试";
		}
		else if(res==-2) {
			info = "用户不存在";
		}
		else if(res==-3) {
			info = "您已经选取过该项目啦，不能重复选取哦";
		}
		else if(res==-4) {
			info = "项目已取消或不存在";
		}
		else if(res==-5) {
			info = "啊哦，项目人数已满，下次要快一点啊";
		}
		else if(res==-6) {
			info = "啊哦，您的等级不足，快快努力打怪升级吧~";
		}
		return info;
	}
}
