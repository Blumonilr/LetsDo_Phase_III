package YingYingMonster.LetsDo_Phase_III.daoImpl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import YingYingMonster.LetsDo_Phase_III.dao.MockDB;
import YingYingMonster.LetsDo_Phase_III.model.Persistent;

@Component
public class MockDBImpl implements MockDB {

	@Autowired
	private String ROOT;
	
	@Autowired
	private CSVHandler handler;
	
	private HashMap<String,MockTable>tables;
	
	//for test
	public HashMap<String,MockTable>getTables(){
		return tables;
	}
	
	public MockDBImpl(){
		System.out.println("db initialize");
		tables=new HashMap<>();
		String path=System.getProperty("user.home").replaceAll("\\\\", "/")+"/database/tables";
		File file=new File(path);
		String[]fileNames=file.list();
		if(fileNames!=null){			
			for(String str:fileNames){
				System.out.println(str+" already exists!");
				tables.put(str, new MockTable(path+"/"+str));
			}
		}
	}
	
	@Override
	public boolean createTable(String name) throws IOException {
		// TODO Auto-generated method stub
		if(tables.get(name)!=null){
			System.out.println(name+" already exist!");
			return false;
		}
		
		String path=ROOT+"/tables/"+name;
		File file=new File(path);
		file.mkdirs();
		
		MockTable table=new MockTable(path);
		tables.put(name, table);
		return true;
	}

	@Override
	public boolean insert(String tableName, Persistent obj) throws FileNotFoundException, IOException {
		// TODO Auto-generated method stub
		if(tables.get(tableName)==null)
			return false;
		
		return tables.get(tableName).insert(obj);
	}

	@Override
	public List<Persistent> readTable(String tableName) throws FileNotFoundException, ClassNotFoundException, IOException {
		// TODO Auto-generated method stub
		if(tables.get(tableName)==null)
			return null;
		return tables.get(tableName).readAll();
	}

	@Override
	public boolean modify(String tableName,Persistent obj) throws FileNotFoundException, IOException {
		// TODO Auto-generated method stub
		if(tables.get(tableName)==null)
			return false;
		return tables.get(tableName).modify(obj);
	}

	@Override
	public boolean delete(String tableName,Persistent obj) {
		// TODO Auto-generated method stub
		if(tables.get(tableName)==null)
			return false;
		return tables.get(tableName).delete(obj);
	}

	@Override
	public Persistent retrieve(String tableName, String key) throws FileNotFoundException, ClassNotFoundException, IOException {
		// TODO Auto-generated method stub
		MockTable table=tables.get(tableName);
		if(table==null)
			return null;
		return table.retrieve(key);
	}

}
