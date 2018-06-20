package YingYingMonster.LetsDo_Phase_III.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import YingYingMonster.LetsDo_Phase_III.entity.role.Worker;
import YingYingMonster.LetsDo_Phase_III.service.RankService;

@Controller
@RequestMapping("/rank")
public class RankController {

	@Autowired
    private RankService rankService;
	private final Logger logger = LoggerFactory.getLogger(getClass());
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
	    	for(int i=0 ; i<len ; i++) {//name level money exp
	    		Worker w = list.get(i);
	    		String name = w.getName();
	    		String exp = w.getExp()+"";
	    		String money = w.getMoney()+"";
	    		String level = w.getLevel()+"";
	    		
	    		String tip = name+"_"+level+"_"+money+"_"+exp;
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
    @GetMapping("/rankByAccuracy/{label}")
    public String getListByAccuracy(@PathVariable("label") String label){
            String res = "";
		logger.info("label = ",label);
			ArrayList<Worker> list;
			try {
				list = (ArrayList<Worker>) rankService.rankByAccuracy(label);
				int len = list.size();
		    	for(int i=0 ; i<len ; i++) {
		    		Worker w = (Worker) list.get(i);
		    		String name = w.getName();
		    		String accu = w.getAccuracy()+"";

		    		String tip = name+"_"+accu;
		    		res = res + tip;

		    		if(i != len-1) {
		    			res = res + ",";
		    		}
		    	}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
	    	
			System.out.println("ACCU: "+res);
	    	return res;
    }
    
    
    /**
     * 
     * @return所有label
     */
    @ResponseBody
    @GetMapping("/getLabels")
    public String getLabels() {
    	String res = "";
    	List<String> list = rankService.getLabels();
    	for(int i=0;i<list.size();i++) {
    		res = res + list.get(i);
    		if(i != list.size()-1) {
    			res = res + "_";
    		}
    	}
    	System.out.println("LABELS: "+res);
    	return res;
    }
    
}
