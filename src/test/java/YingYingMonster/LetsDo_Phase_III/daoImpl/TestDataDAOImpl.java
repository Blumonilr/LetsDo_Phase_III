package YingYingMonster.LetsDo_Phase_III.daoImpl;

import static org.junit.Assert.*;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import YingYingMonster.LetsDo_Phase_III.dao.DataDAO;
import YingYingMonster.LetsDo_Phase_III.model.Project;
import YingYingMonster.LetsDo_Phase_III.service.AdminService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestDataDAOImpl {
	
	@Autowired
	DataDAO dataDAOImpl;
	@Autowired
	AdminService admin;
	
//	@Test
	public void test(){}
	
//	@Test
	public void testUploadDataSet(){
		byte[] dataSet=null;
		ByteArrayOutputStream bos=null;  
		BufferedInputStream in=null;  
		File des=new File("C:/Users/TF/Desktop/文件/中文test.zip");
		try{   
			bos=new ByteArrayOutputStream((int)des.length());  
			in=new BufferedInputStream(new FileInputStream(des));  
			int buf_size=1024;  
			byte[] buffer=new byte[buf_size];  
			int len=0;  
			while(-1 != (len=in.read(buffer,0,buf_size))){  
				bos.write(buffer,0,len);  
			}  
			dataSet=bos.toByteArray();
			assertEquals(3,dataDAOImpl.uploadDataSet("11112", "test", 4,dataSet));
		}
		catch(Exception e){  
			e.printStackTrace();   
		}  
		finally{  
			try{  
				if(in!=null){  
					in.close();  
				}  
				if(bos!=null){  
					bos.close();  
				}  
			}  
			catch(Exception e){   
				e.printStackTrace();    
			}  
		}
	}
	
//	@Test
//	public void testDownLoadTags(){
//		assertEquals(null,dataDAOImpl.downloadTags("11111", "中文test"));
//	}
	
//	@Test
//	public void testViewProgress() throws FileNotFoundException, ClassNotFoundException, IOException{
//		assertEquals(0.5,dataDAOImpl.viewProjectProgress("11111", "中文test"),0.01);
//	}
	
	@Test
	public void testHandler(){
		String[] str0=new String[]{"publisherId","projectId","start","end","state"};
		String[] str1=new String[]{"111","test1","2017-4-11","2017-4-20","null"};
		String[] str2=new String[]{"112","test2","2018-4-11","2018-4-30","null"};
		String[] str3=new String[]{"113","test3","2018-5-10","2018-5-20","true"};
		List<String[]> list=new ArrayList<>();
		list.add(str0);
		list.add(str1);
		list.add(str2);
		list.add(str3);
		dataDAOImpl.modifyDateStatus(list);
	}
	
//	@Test
	public void testOutput(){
		List<String[]> output=dataDAOImpl.readProjectsDate();
		for(String[] str:output){
			System.out.println(str[0]+" "+str[1]+" "+str[2]+" "+str[3]+" "+str[4]);
		}
	}
	
//	@Test
//	public void testadmin(){
//		try {
//			List<Project> list=admin.viewProjectDone();
//			for(Project pro:list){
//				System.out.println(pro.getProjectId());
//			}
//		} catch (FileNotFoundException e) {
//			// TODO 自动生成的 catch 块
//			e.printStackTrace();
//		} catch (ClassNotFoundException e) {
//			// TODO 自动生成的 catch 块
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO 自动生成的 catch 块
//			e.printStackTrace();
//		}
//	}
}
