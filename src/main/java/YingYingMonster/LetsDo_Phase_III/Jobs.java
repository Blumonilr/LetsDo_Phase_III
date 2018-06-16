package YingYingMonster.LetsDo_Phase_III;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import YingYingMonster.LetsDo_Phase_III.entity.Project;
import YingYingMonster.LetsDo_Phase_III.service.AdminService;
import YingYingMonster.LetsDo_Phase_III.service.ProjectService;
import YingYingMonster.LetsDo_Phase_III.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
@Component
public class Jobs {
	@Autowired
	AdminService adminService;
	@Autowired
	ProjectService projectService;

	SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
	
	@Scheduled(cron="10 0 00 * * ?")
	//每日0点0分10秒时更新
	public void cronJob() throws ParseException, ClassNotFoundException, IOException{
		Calendar today = Calendar.getInstance();
		today.set(today.get(Calendar.YEAR),today.get(Calendar.MONTH),today.get(Calendar.DATE),0,0,0);
		List<Project> projects=adminService.viewDoingProject();
		for (Project p:projects){
			Date d=format.parse(p.getEndDate());
			if (d.before(today.getTime()))
				projectService.closeProject(p.getId());
		}
	}
	
}
