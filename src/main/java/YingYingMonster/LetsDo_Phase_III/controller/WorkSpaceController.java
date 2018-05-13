package YingYingMonster.LetsDo_Phase_III.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import YingYingMonster.LetsDo_Phase_III.model.Data;
import YingYingMonster.LetsDo_Phase_III.model.Tag;
import YingYingMonster.LetsDo_Phase_III.service.WorkerService;


@Controller
@RequestMapping("/workspace")
public class WorkSpaceController {
	
	@Autowired
	private WorkerService service;
	
	/**
	 * 
	 * @return 返回标注的界面
	 */
	@GetMapping("/{workType}")
	public String getWorkSpace(@PathVariable("workType")String type) {
		if(type.equals("tips")) {
			//填写标签，包括整体标注
			return "workspace/tips";
		}
		else if(type.equals("area")) {
			//按要求覆盖指定区域
			return "workspace/area";
		}
		else if(type.equals("mark")) {
			//按要圈出指定物体
			return "workspace/mark";
		}
		else {
			//default
			return "workspace/total";
		}
	}
	
	/**
	 * 
	 * @param type
	 * @return  返回修改tag的界面
	 */
	@GetMapping("/editPreviousTag/{workType}")
	public String getEditWorkSpace(@PathVariable("workType")String type) {
		if(type.equals("tips")) {
			//填写标签，包括整体标注
			return "workspace/edit/tips";
		}
		else if(type.equals("area")) {
			//按要求覆盖指定区域
			return "workspace/edit/area";
		}
		else if(type.equals("mark")) {
			//按要圈出指定物体
			return "workspace/edit/mark";
		}
		else {
			//default
			return "workspace/edit/total";
		}
	}
	
	/**
	 * 
	 * @param projectId
	 * @param userId
	 * @return  返回查看已经完成的标注的界面
	 */
	@GetMapping("/viewDone/{userId}/{projectId}/{publisherId}")
	public String getViewDone(@PathVariable("projectId")String projectId ,
			@PathVariable("userId")String userId,
			@PathVariable("publisherId")String publisherId) {
		
		//需要根据id进行一些图片的准备工作
		return "workspace/viewDone";
	}
	
	
	/**
	 * 
	 * @param projectId
	 * @param userId
	 * @return 返回已经完成的图片的id  id之间以, 隔开
	 */
	@GetMapping("/viewDone/{userId}/{projectId}/{publisherId}/getPictureIdList")
	@ResponseBody
	public String getDonePictureIdList(@PathVariable("projectId")String projectId ,
			@PathVariable("userId")String userId ,
			@PathVariable("publisherId")String publisherId) {
		String str = "";//从service获得
		ArrayList<String> list = (ArrayList<String>) service.viewDoneData(userId, publisherId, projectId);
		if(list==null) {
			return "";//空串
		}
		
		int len = list.size();
		for(int i=0 ; i<len ; i++) {
			str += list.get(i);
			if(i!=len-1) {
				str+=",";
			}
		}
		System.out.println(str);
		return str;
	}
	
	
	/**
	 * 把一张已完成的图片放到url里
	 */
    @RequestMapping("/viewDone/putPicture/{userId}/{projectId}/{pictureId}")  
    @ResponseBody  
    public void getDoneImage(HttpServletRequest request, HttpServletResponse response,@PathVariable("projectId")String projectId ,
			@PathVariable("userId")String userId,
			@PathVariable("pictureId")String pictureId) throws Exception{  
    	
    	String JPG="image/jpeg;charset=UTF-8";      
        // 获取输出流  
        OutputStream outputStream = response.getOutputStream();  
        // 读数据  
        byte[] data = null; //service.getPicture(pictureId);//从service获得
        // 回写  
        response.setContentType(JPG);  
        outputStream.write(data);  
        outputStream.flush();  
        outputStream.close();  
    }  
    
    /**
   	 * 把一张已经完成的图片放到url里
   	 */
     @RequestMapping("/getOldPicture/{userId}/{projectId}/{publisherId}/{pictureId}")  
     @ResponseBody  
     public void getOldImage(HttpServletRequest request, HttpServletResponse response,@PathVariable("projectId")String projectId ,
   			@PathVariable("userId")String userId,
   			@PathVariable("publisherId")String publisherId,
   			@PathVariable("pictureId")String pictureId) throws Exception{  
       	
       	String JPG="image/jpeg;charset=UTF-8";      
           // 获取输出流  
           OutputStream outputStream = response.getOutputStream();  
           // 读数据  
           Data dataPac = service.getAData(userId, publisherId, projectId, pictureId);
           System.out.println(userId+" "+publisherId+" "+projectId+" "+pictureId);
           byte[] data = dataPac.getData();
           int height = dataPac.getHeight();
           int width = dataPac.getWidth();
           String size = width+","+height;
           // 回写  
           response.setContentType(JPG);  
           outputStream.write(data);  
           outputStream.flush();  
           outputStream.close();  
           
       }  
    
     
    
    /**
     * 随机获得一个待完成的图片的id
     * @return 图片id
     */
    @RequestMapping("/getNewPictureId/{userId}/{projectId}/{publisherId}")  
    @ResponseBody  
    public String getNewPictureId(@PathVariable("projectId")String projectId ,
   			@PathVariable("userId")String userId,
   			@PathVariable("publisherId")String publisherId) {
    	System.out.println(userId+" "+ publisherId+" "+ projectId);
    	List<String> list = service.viewUndoData(userId, publisherId, projectId);
    	
    	if(list==null||list.size()==0) {
    		return "";//没有内容
    	}
    	
    	String id = list.get(0) ; //需要检查是否有内容！！
    	
    	return id;
    }
    
    
    /**
   	 * 把一张等待完成的图片放到url里
   	 */
     @RequestMapping("/getNewPicture/{userId}/{projectId}/{publisherId}/{pictureId}")  
     @ResponseBody  
     public void getNewImage(HttpServletRequest request, HttpServletResponse response,@PathVariable("projectId")String projectId ,
   			@PathVariable("userId")String userId,
   			@PathVariable("publisherId")String publisherId,
   			@PathVariable("pictureId")String pictureId) throws Exception{  
       	
       	String JPG="image/jpeg;charset=UTF-8";      
           // 获取输出流  
           OutputStream outputStream = response.getOutputStream();  
           // 读数据  
           Data dataPac = service.getAData(userId, publisherId, projectId, pictureId);
           System.out.println(userId+" "+publisherId+" "+projectId+" "+pictureId);
           byte[] data = dataPac.getData();
           int height = dataPac.getHeight();
           int width = dataPac.getWidth();
           String size = width+","+height;
           // 回写  
           response.setContentType(JPG);  
           outputStream.write(data);  
           outputStream.flush();  
           outputStream.close();  
           
       }  
     
     
     /**
    	 * 获得图片的size
    	 */
      @RequestMapping("/getNewPictureSize/{userId}/{projectId}/{publisherId}/{pictureId}")  
      @ResponseBody  
      public String getNewImageSize(HttpServletRequest request, HttpServletResponse response,@PathVariable("projectId")String projectId ,
    			@PathVariable("userId")String userId,
    			@PathVariable("publisherId")String publisherId,
    			@PathVariable("pictureId")String pictureId) throws Exception{  
        	
            Data dataPac = service.getAData(userId, publisherId, projectId, pictureId);
         
            int height = dataPac.getHeight();
            int width = dataPac.getWidth();
           
            String size = width+","+height;
            return size;
        } 
      
      
      /**
       * 
       * @param request
       * @param response
       * @param projectId
       * @param userId
       * @param publisherId
       * @return 获得当前项目进度
       * @throws Exception
       */
      @RequestMapping("/getProgress/{userId}/{projectId}/{publisherId}")  
      @ResponseBody  
      public String getProjectProgress(HttpServletRequest request, HttpServletResponse response,@PathVariable("projectId")String projectId ,
    			@PathVariable("userId")String userId,
    			@PathVariable("publisherId")String publisherId) throws Exception{  
        	
            String prg = "";
           
            prg = service.viewProgress(userId, publisherId, projectId)+"";
            System.out.println("get progress "+prg);
            return prg;
        }  
     
     
     /**
      * 提交标记
      * @param request
      * @param response
      * @param type
      * @param userId
      * @param projectId
      * @param pictureId
      * @param remark  格式为
      * name1:content1,name2:content2(结尾无分号,没有tag内容的 remark为空串)
      */
     @PostMapping("/submit/{type}/{userId}/{projectId}/{publisherId}/{pictureId}")
     public void submitTag(HttpServletRequest request, HttpServletResponse response,
     		@PathVariable("type")String type,
     		@PathVariable("userId")String userId,
     		@PathVariable("projectId")String projectId,
     		@PathVariable("pictureId")String pictureId,
     		@PathVariable("publisherId")String publisherId) {
    	 
    	String imageDataURL = request.getParameter("base64");//tag图片的64位编码
     	String remark = request.getParameter("remark");  
     	String w = request.getParameter("width");
     	String h = request.getParameter("height");
     	int width = Integer.parseInt(w);
     	int height = Integer.parseInt(h);//标记的长宽
     	
     	Tag tag = new Tag();
     	tag.setWidth(width);
     	tag.setHeight(height);
     	tag.setType(type);
    
     	
     	switch(type) {
     	case  "area":
     		Base64.Decoder adecoder = Base64.getDecoder();
        	byte[] ab = adecoder.decode(imageDataURL);//这个用来生成图片
        	tag.setData(ab);
        	
     		break;
     	case  "tips":
     		tag.setData(null);
     		Map<String,String> pmap = new HashMap<String,String>();
     		String[] plist = remark.split(",");
     		for(String ps : plist) {
     			String[] pss = ps.split(":");
     			pmap.put(pss[0], pss[1]);
     		}
     		tag.setAttributes(pmap);
     		break;
     		
     	case  "mark":
     		Base64.Decoder mdecoder = Base64.getDecoder();
        	byte[] mb = mdecoder.decode(imageDataURL);//这个用来生成图片
        	tag.setData(mb);
  
     		break;
     	case "total":
     		tag.setData(null);
     		Map<String,String> tmap = new HashMap<String,String>();
            tmap.put("total", remark);
     		tag.setAttributes(tmap);
     		break;
     	}
     	
     	service.uploadTag(userId, publisherId, projectId, pictureId, tag);
     }
     
     
     /**
      * 
      * @param type
      * @param userId
      * @param projectId
      * @param pictureId
      * @param publisherId
      * @return  文字性标签的内容
      */
     @ResponseBody
     @GetMapping("/getStringTag/{type}/{userId}/{projectId}/{publisherId}/{pictureId}")
     public String loadPrevoisTag_String(@PathVariable("type")String type,//tips,total
      		@PathVariable("userId")String userId,
      		@PathVariable("projectId")String projectId,
      		@PathVariable("pictureId")String pictureId,
      		@PathVariable("publisherId")String publisherId) {
    	 String res = "";
    	 Tag tag = service.getATag(userId, publisherId, projectId, pictureId);
    	 Map<String, String> attr = tag.getAttributes();
    	 
    	 if(type.equals("tips")) {
    		 for(Map.Entry<String,String > entry:attr.entrySet()){  
       	         res = res+entry.getKey()+":"+entry.getValue()+",";
       	  	 }  
       	     res = res.substring(0, res.length()-1);//去掉一个逗号
    	 }
    	 else {
    		 //total
    		 for(Map.Entry<String,String > entry:attr.entrySet()){  
       	         res = entry.getValue();
       	         break;//只要第一个的value
       	  	 }  
    	 }
    	 
    	
    	 return res;
     }
     
     
     /**
      * 
      * @param type
      * @param userId
      * @param projectId
      * @param pictureId
      * @param publisherId
      */
     @ResponseBody
     @RequestMapping("/getPictureTag/{type}/{userId}/{projectId}/{publisherId}/{pictureId}")
     public void loadPreviosTag_Picture(HttpServletRequest request, HttpServletResponse response,
    		@PathVariable("type")String type,
       		@PathVariable("userId")String userId,
       		@PathVariable("projectId")String projectId,
       		@PathVariable("pictureId")String pictureId,
       		@PathVariable("publisherId")String publisherId) throws Exception{
    	 
    	 Tag tag = service.getATag(userId, publisherId, projectId, pictureId);
    	 byte[] data = tag.getData();
    	 String JPG="image/jpeg;charset=UTF-8";
    	 System.out.println("DATA: "+data);
    	 response.setContentType(JPG);  
         OutputStream outputStream = response.getOutputStream();  
 	     outputStream.write(data);
 		 outputStream.flush();  
 		 outputStream.close();  
 
     }
}

