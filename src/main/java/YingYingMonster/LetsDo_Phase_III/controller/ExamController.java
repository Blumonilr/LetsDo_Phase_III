package YingYingMonster.LetsDo_Phase_III.controller;

import java.util.ArrayList;
import java.util.Base64;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import YingYingMonster.LetsDo_Phase_III.entity.Image;
import YingYingMonster.LetsDo_Phase_III.entity.Tag;
import YingYingMonster.LetsDo_Phase_III.entity.TestProject;
import YingYingMonster.LetsDo_Phase_III.model.MarkMode;
import YingYingMonster.LetsDo_Phase_III.service.ProjectService;
import YingYingMonster.LetsDo_Phase_III.service.TestProjectService;
import YingYingMonster.LetsDo_Phase_III.service.WorkerService;

@Controller
@RequestMapping("/exam")
public class ExamController {
ArrayList<Image> picture_list = new ArrayList<Image>();//暂存

	@Autowired
	WorkerService wkservice;

	@Autowired
	TestProjectService testpjservice;
	
	@Autowired
	ProjectService pjservice;
	
	/**
	 * 根据拿到的内测码找project的trueProjectId+"_"+publisherId+"_"+type+"_"+testProjectId
	 * @return id 若没有，返回""
	 */
	@GetMapping("/getExam")
	@ResponseBody
	public String getExamProject(HttpServletRequest request, HttpServletResponse response) {
		String pjid = request.getParameter("projectId");
		long projectId = Long.parseLong(pjid);
		TestProject testProject = wkservice.joinTest(projectId);
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
			
			long trueProjectId = testpjservice.getTrueProjectId(testProjectId);
			long publisherId = testpjservice.getProjectPublisherId(testProjectId);
			
			String res = trueProjectId+"_"+publisherId+"_"+type+"_"+testProjectId;
			return res;
		}
		else {
			return "";
		}		
	}
	
	/**
	 * 返回制作答案的界面
	 * @param type
	 * @return
	 */
	@GetMapping("/make/{workType}")
	public String getWorkSpace(@PathVariable("workType")String type) {
		if(type.equals("area")) {
			//填写标签，包括整体标注
			return "exam/exam_area";
		}
		else if(type.equals("square")) {
			//按要求覆盖指定区域
			return "answers/exam_square";
		}	
		else {
			//default
			return "error";
		}
	}
	
	/**DONE
	 * 上传一个tag
	 * @param request
	 * @param response
	 */
	@PostMapping("/submit")
	public void submitTag(HttpServletRequest request, HttpServletResponse response) {
		
		String imageDataURL = request.getParameter("base64");//tag图片的64位编码
		String xml = request.getParameter("xml");//xml
		System.out.println("XML: "+xml);
		System.out.println("LENGTH: "+xml.length());
		String userId = request.getParameter("userId");
		String pictureId = request.getParameter("pictureId");
		String projectId = request.getParameter("projectId");
		Base64.Decoder mdecoder = Base64.getDecoder();
    	byte[] mb = mdecoder.decode(imageDataURL);
    	
    	long uid = Long.parseLong(userId);
    	long picid = Long.parseLong(pictureId);
    	long pjid = Long.parseLong(projectId);
    	
    	Tag tag = new Tag(uid,picid,pjid,mb,xml,false);
    	
    	//wkservice.uploadAnswer(uid,tag);
    	
	}

}
