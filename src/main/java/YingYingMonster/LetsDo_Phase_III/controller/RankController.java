package YingYingMonster.LetsDo_Phase_III.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    @GetMapping("/rankByExp/{userId}")
    public String getList(@PathVariable("userId") String userId) {
    	String res = "";
    	
    	int my_rank = rankService.viewMyRankByExp(Long.parseLong(userId));
    	
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
    	
    	return my_rank+"*"+res;
    }
    
    @ResponseBody
    @GetMapping("/rankByAccuracy/{label}/{userId}")
    public String getListByAccuracy(@PathVariable("label") String label,@PathVariable("userId") String userId){
            String res = "";
            int my_rank = rankService.viewMyRankByLabelAccuracy(Long.parseLong(userId), label);

			ArrayList<Worker> list;
			try {
				System.out.println("LABEL: "+label);
				list = (ArrayList<Worker>) rankService.rankByAccuracy(label);
				int len = list.size();
		    	for(int i=0 ; i<len ; i++) {
		    		Worker w = (Worker) list.get(i);
		    		String name = w.getName();
		    		String accu = Double.toString(w.getAbilities().stream()
		    				.filter(x->x.getLabel().getName().equals(label))
		    				.collect(Collectors.toList())
		    				.get(0).getAccuracy());
		    		

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
	    	return my_rank+"*"+res;
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
