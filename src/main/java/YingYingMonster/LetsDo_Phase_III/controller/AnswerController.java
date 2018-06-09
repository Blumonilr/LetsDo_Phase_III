package YingYingMonster.LetsDo_Phase_III.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/answer")
public class AnswerController {
	
	/**
	 * 根据拿到的内测码找project的id_publisherId_type
	 * @return id 若没有，返回""
	 */
	@GetMapping("/getProject")
	@ResponseBody
	public String getProject(HttpServletRequest request, HttpServletResponse response) {
		String numId = request.getParameter("numId");
		System.out.println(numId);
		String pjid = "aaa";//调用service方法
		String pubid = "publisher";
		String type = "area";
		
		return pjid+"_"+pubid+"_"+type;
	}
	
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
