package YingYingMonster.LetsDo_Phase_III.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import YingYingMonster.LetsDo_Phase_III.entity.TestProject;
import YingYingMonster.LetsDo_Phase_III.model.MarkMode;
import YingYingMonster.LetsDo_Phase_III.service.TestProjectService;

@Controller
@RequestMapping("/answer")
public class AnswerController {
	
	@Autowired
	TestProjectService service;
	
	/**
	 * 根据拿到的内测码找project的trueProjectId+"_"+publisherId+"_"+type+"_"+testProjectId
	 * @return id 若没有，返回""
	 */
	@GetMapping("/getProject")
	@ResponseBody
	public String getProject(HttpServletRequest request, HttpServletResponse response) {
		String inviteCode = request.getParameter("numId");
		TestProject testProject = service.getTestProjectByInviteCode(inviteCode);
		if(testProject != null) {
			long testProjectId = testProject.getId();
			MarkMode mode = testProject.getMarkMode();
			String type = "";
			if(mode == MarkMode.AREA) {
				type = "area";
			}
			else {
				type = "square";
			}
			
			long trueProjectId = service.getTrueProjectId(testProjectId);
			long publisherId = service.getProjectPublisherId(testProjectId);
			
			String res = trueProjectId+"_"+publisherId+"_"+type+"_"+testProjectId;
			return res;
		}
		else {
			return "";
		}		
	}
	
	/**
	 * 返回制作答案对界面
	 * @param type
	 * @return
	 */
	@GetMapping("/{workType}")
	public String getWorkSpace(@PathVariable("workType")String type) {
		if(type.equals("area")) {
			//填写标签，包括整体标注
			return "answers/answer_area";
		}
		else if(type.equals("square")) {
			//按要求覆盖指定区域
			return "answers/answer_square";
		}	
		else {
			//default
			return "error";
		}
	}
	

	
	
}
