package YingYingMonster.LetsDo_Phase_III.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import YingYingMonster.LetsDo_Phase_III.model.Persistent;

public interface MockDB {

	public boolean createTable(String name)throws IOException;
	
	public boolean insert(String tableName,Persistent obj) throws FileNotFoundException, IOException;
	
	/**
	 * 获得某个表的全部对象
	 * @param tableName
	 * @return
	 * @throws FileNotFoundException
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public List<Persistent>readTable(String tableName) throws FileNotFoundException, ClassNotFoundException, IOException;
	
	/**
	 * 根据key获得单个对象
	 * @param tableName
	 * @param key
	 * @return
	 * @throws FileNotFoundException
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public Persistent retrieve(String tableName,String key) throws FileNotFoundException, ClassNotFoundException, IOException;
	
	public boolean modify(String tableName,Persistent obj) throws FileNotFoundException, IOException;
	
	public boolean delete(String tableName,Persistent obj);
	
}
