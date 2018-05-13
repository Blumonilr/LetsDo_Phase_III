package YingYingMonster.LetsDo_Phase_III.serviceImpl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import YingYingMonster.LetsDo_Phase_III.dao.DataDAO;
import YingYingMonster.LetsDo_Phase_III.dao.ProjectDAO;
import YingYingMonster.LetsDo_Phase_III.dao.UserDAO;
import YingYingMonster.LetsDo_Phase_III.model.Project;
import YingYingMonster.LetsDo_Phase_III.model.User;
import YingYingMonster.LetsDo_Phase_III.service.AdminService;

@Component
public class AdminServiceImpl implements AdminService{

	@Autowired 
	UserDAO userDAO;
	@Autowired
	ProjectDAO projectDAO;
	@Autowired
	DataDAO dataDAO;
	
	@Override
	public int viewUserNum() throws FileNotFoundException, ClassNotFoundException, IOException {
		// TODO 自动生成的方法存根
		List<User> users=userDAO.findUsers(null);
		return users.size();
	}

	@Override
	public int viewProjectNum() throws FileNotFoundException, ClassNotFoundException, IOException {
		// TODO 自动生成的方法存根
		List<Project> pros=projectDAO.viewAllProjects();
		return pros.size();
	}

	@Override
	public List<Project> viewProjectOnDuty() throws FileNotFoundException, ClassNotFoundException, IOException {
		// TODO 自动生成的方法存根
		List<String[]> dates=dataDAO.readProjectsDate();
		List<Project> pros=new ArrayList<>();
		for(int i=1;i<dates.size();i++){
			String[] str=dates.get(i);
			if(str[4].equals("true")){
				pros.add(projectDAO.getAProject(str[0], str[1]));
			}
		}
		return pros;
	}

	@Override
	public List<Project> viewProjectDone() throws FileNotFoundException, ClassNotFoundException, IOException {
		// TODO 自动生成的方法存根
		List<String[]> dates=dataDAO.readProjectsDate();
		List<Project> pros=new ArrayList<>();
		for(int i=1;i<dates.size();i++){
			String[] str=dates.get(i);
			System.out.println(str[4]);
			if(str[4].equals("null")){
				System.out.println(str[0]+" "+str[1]);
				pros.add(projectDAO.getAProject(str[0], str[1]));
			}
		}
		
		
		return pros;
	}

	@Override
	public List<User> viewUsers() throws FileNotFoundException, ClassNotFoundException, IOException {
		// TODO 自动生成的方法存根
		return userDAO.findUsers(null);
	}

}
