package YingYingMonster.LetsDo_Phase_III;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import YingYingMonster.LetsDo_Phase_III.dao.DataDAO;
import YingYingMonster.LetsDo_Phase_III.dao.MockDB;
import YingYingMonster.LetsDo_Phase_III.dao.ProjectDAO;
import YingYingMonster.LetsDo_Phase_III.daoImpl.CSVHandler;
import YingYingMonster.LetsDo_Phase_III.model.Project;
import YingYingMonster.LetsDo_Phase_III.model.Worker;

@Component
public class Jobs {
	
	@Autowired
	DataDAO dataDAO;
	@Autowired
	ProjectDAO projectDAO;
	@Autowired
	CSVHandler handler;
	@Autowired
	MockDB db;
	@Autowired
	String ROOT;
	
	SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
	
	@Scheduled(cron="10 0 00 * * ?")
	//每日0点0分10秒时更新
	public void cronJob() throws ParseException, ClassNotFoundException, IOException{
		Calendar now=Calendar.getInstance();
		List<String[]> pros=dataDAO.readProjectsDate();
		List<String[]> newpros=new ArrayList<String[]>();
		newpros.add(pros.get(0));
		for(int i=1;i<pros.size();i++){
			String[] str=pros.get(i);
			if(format.parse(str[2]).after(now.getTime())){
				str[4]="false";
				newpros.add(str);
				continue;
			}
			if(format.parse(str[2]).before(now.getTime())&&format.parse(str[3]).after(now.getTime())){
				str[4]="true";
				System.out.println(str[4]);
				newpros.add(str);
				continue;
			}
			if(format.parse(str[3]).before(now.getTime())){
				str[4]="null";
				Calendar yearago=Calendar.getInstance();
				yearago.set(now.get(Calendar.YEAR)-1, now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
				Calendar dayago=Calendar.getInstance();
				dayago.set(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH)-1);
				if(format.parse(str[3]).before(yearago.getTime())){
					continue;
				}
				else if(format.parse(str[3]).after(yearago.getTime())&&!str[3].equals(format.format(dayago.getTime()))){
					newpros.add(str);
					continue;
				}
				else if(str[3].equals(format.format(dayago.getTime()))){
					//结算设定
					Project project=projectDAO.getAProject(str[0], str[1]);
					List<String[]> workers=handler.readCSV(ROOT+"/publishers/"+str[0]+"/"+str[1]+"/fork.csv");
					
					//更新worker
					for(int j=1;j<workers.size();j++){
						String[] worker=workers.get(j);
						Worker wk=(Worker) db.retrieve("users", worker[0]);
						//获得赏金
						System.out.println(wk.getId());
						wk.setMoney(wk.getMoney()+project.getMoney()/(project.getCurrWorkerNum()));
						db.modify("users", wk);
					}
					
					newpros.add(str);
					continue;
				}
				else
					continue;
			}
		}
		dataDAO.modifyDateStatus(newpros);
	}
	
}
