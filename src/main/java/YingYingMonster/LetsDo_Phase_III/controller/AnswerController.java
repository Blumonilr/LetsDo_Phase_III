package YingYingMonster.LetsDo_Phase_III.controller;

import java.io.IOException;
import java.io.OutputStream;
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

import YingYingMonster.LetsDo_Phase_III.entity.TestProject;
import YingYingMonster.LetsDo_Phase_III.model.MarkMode;
import YingYingMonster.LetsDo_Phase_III.entity.Image;
import YingYingMonster.LetsDo_Phase_III.entity.Tag;
import YingYingMonster.LetsDo_Phase_III.service.TestProjectService;

@Controller
@RequestMapping("/answer")
public class AnswerController {
	
	ArrayList<Image> picture_list = new ArrayList<Image>();//暂存
	
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
	@GetMapping("/make/{workType}")
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
	

	/**DONE
	 * 上传一个tag
	 * @param request
	 * @param response
	 */
	@PostMapping("/submit")
	public void submitTag(HttpServletRequest request, HttpServletResponse response) {
		
		String imageDataURL = request.getParameter("base64");//tag图片的64位编码
		String xml = request.getParameter("xml");//xml
		String userId = request.getParameter("userId");
		String pictureId = request.getParameter("pictureId");
		String projectId = request.getParameter("projectId");
		Base64.Decoder mdecoder = Base64.getDecoder();
    	byte[] mb = mdecoder.decode(imageDataURL);
    	
    	long uid = Long.parseLong(userId);
    	long picid = Long.parseLong(pictureId);
    	long pjid = Long.parseLong(projectId);
    	
    	Tag tag = new Tag(uid,picid,pjid,mb,xml);
    	
    	service.uploadAnswer(uid,tag);
    	
	}
	
	/*
	 * 返回一些图片的id，下划线分割
	 */
	@ResponseBody
	@GetMapping("/getsomeimages")
	public String getSomeImages(HttpServletRequest request, HttpServletResponse response) {
		String res = "";
	
		String projectId = request.getParameter("projectId");
		System.out.println("GET SOME IMGS: "+projectId);
		
    	long pjid = Long.parseLong(projectId);
    	
    	picture_list = (ArrayList<Image>) service.getAPageOfImages(0, pjid);//pageNum参数不给出，给0
		int len = picture_list.size();
		for(int i=0;i<len;i++) {
			res += picture_list.get(i).getId();
			if(i != len-1) {
				res += "_";
			}
		}
		
		return res;
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
     * 返回项目要求
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/getRequirement")  
    @ResponseBody 
    public String get_requirement(HttpServletRequest request, HttpServletResponse response) {
    	String inviteCode = request.getParameter("inviteCode");
		TestProject testProject = service.getTestProjectByInviteCode(inviteCode);
		
		String req = testProject.getProject().getTagRequirement();
		return req;
    }
	
}
