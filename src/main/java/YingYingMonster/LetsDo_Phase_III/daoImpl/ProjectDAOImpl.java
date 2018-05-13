package YingYingMonster.LetsDo_Phase_III.daoImpl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import YingYingMonster.LetsDo_Phase_III.dao.MockDB;
import YingYingMonster.LetsDo_Phase_III.dao.ProjectDAO;
import YingYingMonster.LetsDo_Phase_III.model.Persistent;
import YingYingMonster.LetsDo_Phase_III.model.Project;

@Component
public class ProjectDAOImpl implements ProjectDAO {

	@Autowired
	MockDB database;
	
	@Autowired
	String root;
	
	@Autowired
	CSVHandler handler;
	
	@Override
	public boolean addProject(Project project) throws FileNotFoundException, IOException {
		// TODO 自动生成的方法存根
		return database.insert("projects", project);
	}

	@Override
	public boolean validateProject(String publisherId, String projectId) throws FileNotFoundException, ClassNotFoundException, IOException {
		// TODO 自动生成的方法存根
		List<Persistent> list=database.readTable("projects");
		for(Persistent p:list){
			if(((Project)p).getPublisherId().equals(publisherId)&&((Project)p).getProjectId().equals(projectId))
				return false;
		}
		return true;
	}

	@Override
	public List<Project> publisherViewProjects(String publisherId) throws FileNotFoundException, ClassNotFoundException, IOException {
		// TODO 自动生成的方法存根
		List<Persistent> list=database.readTable("projects");
		List<Project> proList=new ArrayList<>();
		for(Persistent p:list){
			if(((Project)p).getPublisherId().equals(publisherId))
				proList.add((Project)p);
		}
		return proList;
	}

	@Override
	public List<Project> viewAllProjects() throws FileNotFoundException, ClassNotFoundException, IOException {
		// TODO 自动生成的方法存根
		List<Persistent> list=database.readTable("projects");
		List<Project> proList=new ArrayList<>();
		for(Persistent p:list){
			proList.add((Project)p);
		}
		return proList;
	}

	@Override
	public boolean modifyCurrentWorkerNum(String publisherId, String projectId) throws FileNotFoundException, ClassNotFoundException, IOException {
		// TODO 自动生成的方法存根
		List<Persistent> list=database.readTable("projects");
		for(Persistent p:list){
			if(((Project)p).getPublisherId().equals(publisherId)&&((Project)p).getProjectId().equals(projectId)){
				Project newP=(Project)p;
				newP.setCurrWorkerNum(newP.getCurrWorkerNum()+1);
				return database.modify("projects", newP);
			}
		}
		return false;
	}

	@Override
	public boolean deleteProject(String publisherId, String projectId) throws FileNotFoundException, ClassNotFoundException, IOException {
		// TODO 自动生成的方法存根	
		List<Persistent> list=database.readTable("projects");
		for(Persistent p:list){
			if(((Project)p).getPublisherId().equals(publisherId)&&((Project)p).getProjectId().equals(projectId))
				return database.delete("projects", (Project)p);
		}
		return false;
	}

	@Override
	public Project getAProject(String publisherId,String projectId) throws FileNotFoundException, ClassNotFoundException, IOException {
		// TODO Auto-generated method stub
		return (Project) database.retrieve("projects", publisherId+"_"+projectId);
	}

}
