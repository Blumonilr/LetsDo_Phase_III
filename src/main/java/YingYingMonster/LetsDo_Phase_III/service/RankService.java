package YingYingMonster.LetsDo_Phase_III.service;

import YingYingMonster.LetsDo_Phase_III.entity.role.User;
import YingYingMonster.LetsDo_Phase_III.entity.role.Worker;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;


public interface RankService {

	/**
	 * 根据经验值给Worker排序
	 * @return
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * @throws FileNotFoundException 
	 */
	List<Worker>rankByExp();
	
	/**
	 * 根据准确率给Worker排序
	 * @return
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * @throws FileNotFoundException 
	 */
	List<Worker> rankByAccuracy(String labelName);
}
