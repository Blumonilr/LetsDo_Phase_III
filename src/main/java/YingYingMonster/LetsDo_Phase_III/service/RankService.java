package YingYingMonster.LetsDo_Phase_III.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import YingYingMonster.LetsDo_Phase_III.model.User;

public interface RankService {

	/**
	 * 根据经验值给Worker排序
	 * @return
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * @throws FileNotFoundException 
	 */
	List<User>rankByExp() throws FileNotFoundException, ClassNotFoundException, IOException;
	
	/**
	 * 根据准确率给Worker排序
	 * @return
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * @throws FileNotFoundException 
	 */
	List<User>rankByAccuracy() throws FileNotFoundException, ClassNotFoundException, IOException;
}
