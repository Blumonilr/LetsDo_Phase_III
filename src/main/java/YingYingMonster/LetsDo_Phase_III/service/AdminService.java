package YingYingMonster.LetsDo_Phase_III.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import YingYingMonster.LetsDo_Phase_III.model.Project;
import YingYingMonster.LetsDo_Phase_III.model.User;

public interface AdminService {

	public int viewUserNum() throws FileNotFoundException, ClassNotFoundException, IOException;
	public int viewProjectNum() throws FileNotFoundException, ClassNotFoundException, IOException;
	public List<User> viewUsers() throws FileNotFoundException, ClassNotFoundException, IOException;
	public List<Project> viewProjectOnDuty() throws FileNotFoundException, ClassNotFoundException, IOException;
	public List<Project> viewProjectDone() throws FileNotFoundException, ClassNotFoundException, IOException;
}
