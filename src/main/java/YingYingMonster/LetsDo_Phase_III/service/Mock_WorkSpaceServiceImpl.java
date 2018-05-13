package YingYingMonster.LetsDo_Phase_III.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Mock_WorkSpaceServiceImpl {
	public byte[] getPicture(String picId) {
		String path = "../../resources/static/pic/tools/cirlce.png";
		byte[]data=null;
		File file=new File(path);
		FileInputStream fis=null;
		
		if(file.exists()){
			try {
				fis=new FileInputStream(file);
				data=new byte[fis.available()];
				fis.read(data);
				fis.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("mock!!!");
		return data;
	}
}
