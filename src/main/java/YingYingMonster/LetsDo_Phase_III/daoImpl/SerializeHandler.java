package YingYingMonster.LetsDo_Phase_III.daoImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.springframework.stereotype.Component;

@Component
public class SerializeHandler {

	public void writeObj(String path,Serializable obj) throws FileNotFoundException, IOException{
		File file=new File(path);
		
		ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream(file));
		oos.writeObject(obj);
		oos.flush();
		oos.close();
	}
	
	public Object readObj(String path) throws FileNotFoundException, IOException, ClassNotFoundException{
		File file=new File(path);
		if(!file.exists())
			return null;
		ObjectInputStream ois=new ObjectInputStream(new FileInputStream(file));
		Object obj=ois.readObject();
		ois.close();
		return obj;
	}
}
