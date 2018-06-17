package YingYingMonster.LetsDo_Phase_III.serviceImpl;

import YingYingMonster.LetsDo_Phase_III.entity.role.User;
import YingYingMonster.LetsDo_Phase_III.entity.role.Worker;
import YingYingMonster.LetsDo_Phase_III.service.AdminService;
import YingYingMonster.LetsDo_Phase_III.service.RankService;
import YingYingMonster.LetsDo_Phase_III.service.UserService;
import com.fasterxml.classmate.types.ResolvedRecursiveType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class RankServiceImpl implements RankService {

	@Autowired
	UserService userService;
	@Autowired
	AdminService adminService;
	
	@Override
	public List<Worker> rankByExp() {
		// TODO Auto-generated method stub
		List<Worker> users=userService.findWorkerByNameLike("");
		users.sort(new Comparator<Worker>() {
			@Override
			public int compare(Worker o1, Worker o2) {
				return o1.getLevel()>o2.getLevel()?1:(o1.getLevel()<o2.getLevel()?-1:(o1.getExp()>o2.getExp()?1:(o1.getExp()<o2.getExp()?-1:0)));
			}
		});
		return users;
	}

	@Override
	public List<Worker> rankByAccuracy(String labelName) {
		return adminService.workerLabelAccuracyRank(labelName);
	}

}
