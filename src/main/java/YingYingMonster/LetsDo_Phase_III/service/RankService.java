package YingYingMonster.LetsDo_Phase_III.service;

import YingYingMonster.LetsDo_Phase_III.entity.role.User;
import YingYingMonster.LetsDo_Phase_III.entity.role.Worker;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;


public interface RankService {

	/**
	 * 根据经验值给Worker排序
	 * 前15
	 */
	List<Worker>rankByExp();
	
	/**
	 * 根据准确率给Worker排序
	 */
	List<Worker> rankByAccuracy(String labelName);

	/**
	 * 供worker查看自己的经验值排名
	 * @param workerId
	 * @return
	 */
	public int viewMyRankByExp(long workerId);

	public int viewMyRankByLabelAccuracy(long workerId, String labelName);

	public List<String> getLabels();
}
