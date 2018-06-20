package YingYingMonster.LetsDo_Phase_III.serviceImpl;

import YingYingMonster.LetsDo_Phase_III.entity.role.Worker;
import YingYingMonster.LetsDo_Phase_III.repository.LabelRepository;
import YingYingMonster.LetsDo_Phase_III.service.AdminService;
import YingYingMonster.LetsDo_Phase_III.service.RankService;
import YingYingMonster.LetsDo_Phase_III.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class RankServiceImpl implements RankService {

	@Autowired
	UserService userService;
	@Autowired
	AdminService adminService;
	@Autowired
	LabelRepository labelRepository;
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	public List<Worker> rankByExp() {
		// TODO Auto-generated method stub
		List<Worker> users=userService.findWorkerByNameLike("");
		users.sort(new Comparator<Worker>() {
			@Override
			public int compare(Worker o1, Worker o2) {
				return o1.getLevel()>o2.getLevel()?-1:(o1.getLevel()<o2.getLevel()?1:(o1.getExp()>o2.getExp()?-1:(o1.getExp()<o2.getExp()?1:0)));
			}
		});
		//返回前15名
		return users.stream().limit(15).collect(Collectors.toList());
	}

	@Override
	public List<Worker> rankByAccuracy(String labelName) {
		logger.info("labelName = ", labelName);
		return adminService.workerLabelAccuracyRank(labelName).stream().limit(15).collect(Collectors.toList());
	}

	@Override
	public int viewMyRankByExp(long workerId) {
		return userService.getAllWorkers().stream().sorted((w1, w2) -> {
			return (w1.getLevel() > w2.getLevel() ||
					w1.getLevel() == w2.getLevel() && w1.getExp() > w2.getExp()) ? -1 :
					(w1.getLevel() == w2.getLevel() && w1.getExp() == w2.getExp()) ? 0 : 1;
		}).collect(Collectors.toList()).indexOf(userService.getUser(workerId)) + 1;
	}

	@Override
	public int viewMyRankByLabelAccuracy(long workerId, String labelName) {
		return adminService.workerLabelAccuracyRank(labelName).indexOf(userService.getUser(workerId))+1;
	}

	@Override
	public List<String> getLabels() {
		return labelRepository.findAll().stream().map(x -> x.getName()).collect(Collectors.toList());
	}

}
