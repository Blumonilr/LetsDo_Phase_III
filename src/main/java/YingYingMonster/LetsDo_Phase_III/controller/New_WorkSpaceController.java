package YingYingMonster.LetsDo_Phase_III.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import YingYingMonster.LetsDo_Phase_III.entity.Project;
import YingYingMonster.LetsDo_Phase_III.entity.Tag;
import YingYingMonster.LetsDo_Phase_III.service.WorkerService;


@Controller
@RequestMapping("/workspace")
public class New_WorkSpaceController {
	
	@Autowired
	private WorkerService service;
	
	ArrayList<Image> picture_list = new ArrayList<Image>();//暂存
	
	/**
	 * 
	 * @return 返回标注的界面
	 */
	@GetMapping("/make/{workType}")
	public String getWorkSpace(@PathVariable("workType")String type) {
		if(type.equals("area")) {
			//填写标签，包括整体标注
			return "workspace/newarea";
		}
		else if(type.equals("square")) {
			//按要求覆盖指定区域
			return "workspace/newsquare";
		}	
		else {
			//default
			return "error";
		}
	}
	
	/*
	 * 返回一些图片的id，下划线分割
	 */
	@ResponseBody
	@GetMapping("/getsomeimages")
	public String getSomeImages(HttpServletRequest request, HttpServletResponse response) {
		String res = "";
	
		String projectId = request.getParameter("projectId");
		
    	long pjid = Long.parseLong(projectId);
    	
    	picture_list = (ArrayList<Image>) service.getAPageOfImage(pjid,0);//pageNum参数不给出，给0
		int len = picture_list.size();
		for(int i=0;i<len;i++) {
			res += picture_list.get(i).getId();
			if(i != len-1) {
				res += "_";
			}
		}
		
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
    	
    	service.uploadTag(tag);
    	
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
    	String pjId = request.getParameter("projectId");
    	long projectId = Long.parseLong(pjId);
		Project project = service.getAProject(projectId);
		
		String req = project.getTagRequirement();
		return req;
    }
	
}

