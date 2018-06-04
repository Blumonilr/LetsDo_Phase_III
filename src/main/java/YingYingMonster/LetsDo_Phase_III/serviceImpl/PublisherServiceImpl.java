package YingYingMonster.LetsDo_Phase_III.serviceImpl;

import java.io.IOException;
import java.text.ParseException;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import YingYingMonster.LetsDo_Phase_III.dao.DataDAO;
import YingYingMonster.LetsDo_Phase_III.dao.ProjectDAO;
import YingYingMonster.LetsDo_Phase_III.exception.TargetNotFoundException;
import YingYingMonster.LetsDo_Phase_III.model.Project;
import YingYingMonster.LetsDo_Phase_III.service.PublisherService;

@Component
public class PublisherServiceImpl implements PublisherService {

	@Autowired
	ProjectDAO pjDao;
	
	@Autowired
	DataDAO dtDao;
	
	@Override
	public boolean createProject(Project project, MultipartFile dataSet) {
		// TODO Autproject.go-generated method stub
		byte[] bytes = null;
		try {
			bytes = dataSet.getBytes();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		int[]res = null;
		try {
			res=dtDao.uploadDataSet(project.getPublisherId(), project.getProjectId(),
					project.getPackageNum(), bytes);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		System.out.println("picNum = "+res[0]+" packNum = "+res[1]);
		
		if(res[0]<=0){
			dtDao.deleteDir(project.getPublisherId(), project.getProjectId());
			return false;
		}
		project.setPicNum(res[0]);
		if(res[0]<project.getPackageNum())
			project.setPackageNum(res[0]);
		else
			project.setPackageNum(res[1]);
		project.setPkgs();
		
		try {
			pjDao.addProject(project);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			if(!dtDao.addDate(project.getStartDate(), project.getEndDate(), project.getPublisherId(), project.getProjectId())){
				return false;
			}
		} catch (ParseException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public boolean validateProject(String publisherId, String projectId) {
		// TODO Auto-generated method stub
		boolean flag=false;
		try {
			flag=pjDao.validateProject(publisherId, projectId);
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public List<Project> searchProjects(String publisherId, String keyword) {
		// TODO Auto-generated method stub
		List<Project>list=null;
		try {
			list=pjDao.publisherViewProjects(publisherId);
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Iterator<Project>it=list.iterator();
		while(it.hasNext()){
			Project p=it.next();
			if(!p.getProjectId().contains(keyword))
				it.remove();
		}
		return list;
	}

	@Override
	public List<String[]> viewPushEvents(String publisherId, String projectId) {
		// TODO Auto-generated method stub
		List<String[]>list=dtDao.viewPushEvents(publisherId, projectId);
		return list;
	}

	@Override
	public byte[] downloadTags(String publisherId, String projectId) {
		// TODO Auto-generated method stub
		return dtDao.downloadTags(publisherId, projectId);
	}

	@Override
	public Project getAProject(String publisherId,String projectId) {
		// TODO Auto-generated method stub
		Project pj=null;
		try {
			pj=pjDao.getAProject(publisherId,projectId);
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(pj==null)
			throw new TargetNotFoundException();
		return pj;
	}

	@Override
	public double viewProjectProgress(String publisherId, String projectId) {
		// TODO 自动生成的方法存根
		try {
			return dtDao.viewProjectProgress(publisherId, projectId);
		} catch (ClassNotFoundException | IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return -1;
	}

	@Override
	public List<String> viewWorkers(String publisherId, String projectId) {
		// TODO 自动生成的方法存根 
		return dtDao.viewWorkers(publisherId, projectId);
	}

}
