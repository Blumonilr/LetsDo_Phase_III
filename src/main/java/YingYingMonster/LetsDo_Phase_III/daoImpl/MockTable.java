package YingYingMonster.LetsDo_Phase_III.daoImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import YingYingMonster.LetsDo_Phase_III.model.Persistent;

public class MockTable {

	public String path;
	
	public MockTable(String path){
		this.path=path;		
	}

	public boolean insert(Persistent obj) throws FileNotFoundException, IOException {
		File file=new File(path+"/"+obj.getKey()+".object");
		if(file.exists())
			return false;
		
		ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream(file));
		oos.writeObject(obj);
		oos.flush();
		oos.close();
		
		return true;
		
	}

	public List<Persistent> readAll() throws FileNotFoundException, IOException, ClassNotFoundException {
		// TODO Auto-generated method stub
		List<Persistent> list = new ArrayList<>();
		String[]arr=new File(path).list();
		
		if(arr!=null){			
			for(String str:arr){
				ObjectInputStream ois=new ObjectInputStream(
						new FileInputStream(
								new File(path+"/"+str)));
				Persistent obj=(Persistent) ois.readObject();
				list.add(obj);
				ois.close();
			}
		}
		return list;
	}

	public boolean modify(Persistent obj) throws FileNotFoundException, IOException {
		// TODO Auto-generated method stub
		File file=new File(path+"/"+obj.getKey()+".object");
		if(!file.exists())
			return false;
		
		ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream(file));
		oos.writeObject(obj);
		oos.flush();
		oos.close();
		
		return true;
	}

	public boolean delete(Persistent obj) {
		// TODO Auto-generated method stub
		File file=new File(path+"/"+obj.getKey()+".object");
		if(!file.exists())
			return false;
		else{
			file.delete();
			return true;
		}
	}

	public Persistent retrieve(String key) throws FileNotFoundException, IOException, ClassNotFoundException {
		// TODO Auto-generated method stub
		File file=new File(path+"/"+key+".object");
		if(file.exists()){			
			System.out.println(file.getPath());
			ObjectInputStream ois=new ObjectInputStream(new FileInputStream(file));
			Persistent obj=(Persistent) ois.readObject();
			ois.close();
			return obj;
		}
		return null;
	}
	
}
