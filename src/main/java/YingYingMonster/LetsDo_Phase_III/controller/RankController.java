package YingYingMonster.LetsDo_Phase_III.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import YingYingMonster.LetsDo_Phase_III.entity.role.Worker;
import YingYingMonster.LetsDo_Phase_III.service.RankService;

@Controller
@RequestMapping("/rank")
public class RankController {

	@Autowired
    private RankService rankService;
	
	/**
	 * @return 返回rank界面
	 */
    @GetMapping("")
    public String getPage() {
    	return "rank/rank";
    }
    
    
    /**
     * @return  
     * @throws IOException 
     * @throws ClassNotFoundException 
     * @throws FileNotFoundException 
     */
    @ResponseBody
    @GetMapping("/rankByExp")
    public String getList() {
    	String res = "";
    	
		try {
			ArrayList<Worker> list = (ArrayList<Worker>) rankService.rankByExp();
			int len = list.size();
	    	for(int i=0 ; i<len ; i++) {
	    		Worker w = list.get(i);
	    		String name = w.getName();
	    		String exp = w.getExp()+"";
	    		String money = w.getMoney()+"";
	    		String level = w.getLevel()+"";
	    		String info = w.getIntro();
	    		String accu = w.getAccuracy()+"";

	    		String tip = name+"_"+level+"_"+money+"_"+exp+"_"+info+"_"+accu;
	    		res = res + tip;

	    		if(i != len-1) {
	    			res = res + ",";
	    		}
	    	}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
    	
  
    	return res;
    }
    
    @ResponseBody
    @GetMapping("/rankByAccuracy")
    public String getListByAccuracy(){
            String res = "";

			ArrayList<Worker> list;
			try {
				list = (ArrayList<Worker>) rankService.rankByAccuracy("");
				int len = list.size();
		    	for(int i=0 ; i<len ; i++) {
		    		Worker w = (Worker) list.get(i);
		    		String name = w.getName();
		    		String exp = w.getExp()+"";
		    		String money = w.getMoney()+"";
		    		String level = w.getLevel()+"";
		    		String info = w.getIntro();
		    		String accu = w.getAccuracy()+"";

		    		String tip = name+"_"+level+"_"+money+"_"+exp+"_"+info+"_"+accu;
		    		res = res + tip;

		    		if(i != len-1) {
		    			res = res + ",";
		    		}
		    	}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
	    	
	    	return res;
    }
}
