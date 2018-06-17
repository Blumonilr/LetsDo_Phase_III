package YingYingMonster.LetsDo_Phase_III.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import YingYingMonster.LetsDo_Phase_III.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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
	 * 返回进入考试的界面
	 * @return
	 */
	@GetMapping("/entrance")
	public String getExamEntrance() {
		return "exam/exam_entrance";
	}
	
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
			
			//long trueProjectId = testpjservice.getTrueProjectId(testProjectId);
			long publisherId = testpjservice.getProjectPublisherId(testProjectId);
			
			String res = publisherId+"_"+type+"_"+testProjectId;
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
			return "exam/exam_square";
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
    	
    	wkservice.uploadTag(tag);
    	
	}
	
	/*
	 * 返回所有考试图片的id
	 */
	@ResponseBody
	@GetMapping("/getAllExamPic")
	public String getSomeImages(HttpServletRequest request, HttpServletResponse response) {
		String res = "";
	
		String projectId = request.getParameter("testProjectId");
		
    	long testpjid = Long.parseLong(projectId);
    	
    	picture_list = (ArrayList<Image>) wkservice.getAllImages(testpjid);
		int len = picture_list.size();
		for(int i=0;i<len;i++) {
			res += picture_list.get(i).getId();
			if(i != len-1) {
				res += "_";
			}
		}
		
		System.out.println("GET ALL EXAM PIC: "+res);
		return res;//如果没有了，返回""
	}
	
	 /**
   	* 把一张等待完成的图片放到url里
 * @throws IOException 
   	*/
    @RequestMapping("/getNewPicture/{pictureId}")  
    @ResponseBody  
	public void getNewImage(HttpServletRequest request, HttpServletResponse response,
			@PathVariable("pictureId")String pictureId) throws IOException {
    	long picId = Long.parseLong(pictureId);
    	
    	String JPG="image/jpeg;charset=UTF-8";  
    	
    	for(Image image : picture_list) {
    		if(image.getId() == picId) {
    			byte[] data = image.getPicture();
    			OutputStream outputStream = response.getOutputStream();  
    			response.setContentType(JPG);  
                outputStream.write(data);  
                outputStream.flush();   
                outputStream.close();  
                break;
    		}
    	}
	}
    

    /**
     * 返回  width_height
     * @param pictureId
     * @return
     */
    @RequestMapping("/getPictureSize/{pictureId}")  
    @ResponseBody  
    public String getPictureSize(@PathVariable("pictureId")String pictureId) {
    	long picId = Long.parseLong(pictureId);
    	int width = 0;
    	int height = 0;
    	for(Image image : picture_list) {
    		if(image.getId() == picId) {
    			width = image.getWidth();
    			height = image.getHeight();
                break;
    		}
    	}
    	return width+"_"+height;
    }
	
    /**
     * 返回项目要求
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/getRequirement")  
    @ResponseBody 
    public String get_requirement(HttpServletRequest request, HttpServletResponse response) {
    	String tProjectId = request.getParameter("projectId");
    	long trueProjectId = Long.parseLong(tProjectId);
		Project project = pjservice.getAProject(trueProjectId);
		
		String req = project.getTagRequirement();
		return req;
    }
    
    @RequestMapping("/getOptions")  
    @ResponseBody 
    public String get_options(HttpServletRequest request, HttpServletResponse response) {
    	String pjId = request.getParameter("projectId");
    	long projectId = Long.parseLong(pjId);
		Project pj = pjservice.getAProject(projectId);
		String res = "";
		List<TextNode> list = pjservice.getProjectTextNode(projectId);//一textnode对应一个class
		
		int classNum = list.size();
		
		for(int i=0;i<classNum;i++) {
			
			String classi = list.get(i).getName()+":";
			List<String> selections = list.get(i).getAttributions();
			
			for(int j=0;j<selections.size();j++) {
				String sel_j = selections.get(j);
				String[] sel_j_split = sel_j.split(":");
				String selectionj = sel_j_split[0]+"_"+sel_j_split[1];
				classi += selectionj;
				if(j != selections.size()-1) {
					classi += ",";
				}
			}
			
			res += classi;
			if(i != classNum-1) {
				res += "!";
			}
		}
		return res;
    }
    
    /**
     * 返回项目要求
     * @return
     */
    @RequestMapping("/getTestScore")  
    @ResponseBody 
    public String getTestScore(HttpServletRequest request, HttpServletResponse response) {
    	String tProjectId = request.getParameter("projectId");
    	long trueProjectId = Long.parseLong(tProjectId);
    	String uId = request.getParameter("userId");
    	long userId = Long.parseLong(uId);
		
    	wkservice.finishTest(userId, trueProjectId);//告诉后端考试完成
    	double score = wkservice.getTestResult(userId, trueProjectId);//用true ProjectId拿结果
    	
    	return score+"";
    }
    
    
}
