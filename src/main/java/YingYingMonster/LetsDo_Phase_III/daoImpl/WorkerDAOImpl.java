package YingYingMonster.LetsDo_Phase_III.daoImpl;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.util.ListUtils;

import YingYingMonster.LetsDo_Phase_III.dao.MockDB;
import YingYingMonster.LetsDo_Phase_III.dao.WorkerDAO;
import YingYingMonster.LetsDo_Phase_III.model.Data;
import YingYingMonster.LetsDo_Phase_III.model.Project;
import YingYingMonster.LetsDo_Phase_III.model.Tag;
import YingYingMonster.LetsDo_Phase_III.model.Worker;

@Component
public class WorkerDAOImpl implements WorkerDAO {

	@Autowired
	String root;
	
	@Autowired
	MockDB db;
	
	@Autowired
	SerializeHandler serialize;
	
	@Autowired
	CSVHandler csv;
	
	@Override
	public List<Project> viewWorkerProject(String workerId) throws FileNotFoundException, ClassNotFoundException, IOException {
		// TODO Auto-generated method stub
		List<Project>list=new ArrayList<>();
		File wkDir=new File(root+"/workers/"+workerId);
		if(!wkDir.exists())
			return null;
		
		Iterator<String>keys=Stream.of(wkDir.list())
				.map(x->x.substring(0, x.lastIndexOf("_"))).iterator();
		
		while(keys.hasNext())
			list.add((Project) db.retrieve("projects", keys.next()));
			
		return list;
	}

	@Override
	public int fork(String workerId, String publisherId, String projectId) 
			throws FileNotFoundException, ClassNotFoundException, IOException {
		// TODO Auto-generated method stub
		
		if(workerId==null||publisherId==null||projectId==null){

//			System.out.println("+++null args in fork");
			return -1;
		}
		
		File folder=new File(root+"/workers/"+workerId);
		if(!folder.exists()){
//			System.out.println("+++worker not exist");
			return -2;
		}
		
		Iterator<String>it=Stream.of(folder.list())
				.filter(x->x.substring(0,x.lastIndexOf("_")).equals(publisherId+"_"+projectId))
				.iterator();
		
		if(it.hasNext()){
//			System.out.println("+++duplicate");
			return -3;//不能重复fork
		}
		
		File file=new File(root+"/dataSet/"+publisherId+"_"+projectId);
		Project pj=(Project) db.retrieve("projects", publisherId+"_"+projectId);
		Worker wk=(Worker) db.retrieve("users", workerId);

		if(pj==null||!file.exists())
			return -4;//找不到project对象,文件遗失
		
		if(pj.getCurrWorkerNum()==pj.getMaxWorkerNum()){
			
			return -5;//人数已满
		}
		
		if(pj.getWorkerRequirement().getLevelLimit()>wk.getLevel()){
			return -6;//用户不符合要求
		}
		
		//分包
		int[]pkgs=pj.getPkgs();
		int ptr=0;
		while(ptr<pkgs.length&&pkgs[ptr]==0)
			ptr++;//找到人数未满的包
		
		if(ptr>=pkgs.length){
			System.out.println("full");
			return -5;//人数已满，fork失败
		}
		
		pkgs[ptr]--;
		pj.setPkgs(pkgs);
		pj.setCurrWorkerNum(pj.getCurrWorkerNum()+1);
		db.modify("projects", pj);
		
		//修改worker对象
		List<String>pjs=wk.getUnfinishedPj();
		pjs.add(publisherId+"_"+projectId);
		wk.setUnfinishedPj(pjs);
		db.modify("users", wk);
		
		//log in publisher's fork.csv
		csv.appendCSV(new String[]{workerId}, 
				root+"/publishers/"+publisherId+"/"+projectId+"/fork.csv");
				
		//在workers下建立文件夹
		File pjFolder=new File(root+"/workers/"+workerId+"/"+publisherId+"_"+projectId+"_"+(ptr+1));
		pjFolder.mkdirs();
		return 0;
	}

	@Override
	public boolean uploadTag(String workerId,String publisherId, String projectId, String tagId, Tag tag) 
			throws FileNotFoundException, IOException {
		// TODO Auto-generated method stub
		File folder=new File(root+"/workers/"+workerId);
		if(!folder.exists())
			return false;
		
		Iterator<String>it=Stream.of(folder.list())
				.filter(x->x.substring(0,x.lastIndexOf("_")).equals(publisherId+"_"+projectId))
				.iterator();
		
		if(!it.hasNext())
			return false;
		
		String pk=it.next();
		
		//score the tag
		tag.setScore((int) (Math.random()*101));
		
		serialize.writeObj(root+"/workers/"+workerId+"/"+pk+"/"+tagId+".tag", tag);
		return true;
	}

	@Override
	public Tag downloadTag(String workerId,String publisherId, String projectId, String tagId) 
			throws FileNotFoundException, ClassNotFoundException, IOException {
		// TODO Auto-generated method stub
		File folder=new File(root+"/workers/"+workerId);
		if(!folder.exists())
			return null;
		
		Iterator<String>it=Stream.of(folder.list())
				.filter(x->x.substring(0,x.lastIndexOf("_")).equals(publisherId+"_"+projectId))
				.iterator();
		
		if(!it.hasNext())
			return null;
		
		String pk=it.next();
		
		Tag tag=(Tag) serialize.readObj(root+"/workers/"+workerId+"/"+pk+"/"+tagId+".tag");
		return tag;
	}

	@Override
	public List<String> viewUndoData(String workerId,String publisherId, String projectId) {
		// TODO Auto-generated method stub
		String pkgId=pkgId(workerId, publisherId, projectId);
//		System.out.println(pkgId);
		
		File pj=new File(root+"/dataSet/"+publisherId+"_"+projectId+"/"+pkgId);
		List<String>list=Stream.of(pj.list()).map(x->x.split("\\.")[0]).collect(Collectors.toList());
		list.removeAll(viewDoneData(workerId,publisherId,projectId));
		return list;
	}

	@Override
	public List<String> viewDoneData(String workerId,String publisherId, String projectId) {
		// TODO Auto-generated method stub
		File wkfd=new File(root+"/workers/"+workerId);
		Iterator<String>it=Stream.of(wkfd.list())
				.filter(x->x.substring(0,x.lastIndexOf("_")).equals(publisherId+"_"+projectId))
				.iterator();
		if(!it.hasNext())
			return null;
		File pj=new File(wkfd.getPath()+"/"+it.next());
//		System.out.println(pj.getPath());
		return Stream.of(pj.list()).map(x->x.split("\\.")[0]).collect(Collectors.toList());
	}

	@Override
	public Data getAData(String workerId,String publisherId, String projectId, String dataId) throws IOException {
		// TODO Auto-generated method stub
		String pkgId=pkgId(workerId, publisherId, projectId);
		Data data=new Data();
		
		File pkg=new File(root+"/dataSet/"+publisherId+"_"+projectId+"/"+pkgId);
		//找到对应的data文件名
		String dtid=Stream.of(pkg.list()).filter(x->x.split("\\.")[0].equals(dataId))
				.collect(Collectors.toList()).get(0);
//		System.out.println(root+"/dataSet/"+publisherId+"_"+projectId+"/"+pkgId+"/"+dtid);
		//获得图片尺寸
		BufferedImage bi=ImageIO.read(
				new File(pkg.getPath()+"/"+dtid));
		data.setWidth(bi.getWidth());
		data.setHeight(bi.getHeight());
		
		//获得byte数组
		FileImageInputStream fiis=new FileImageInputStream(
				new File(pkg.getPath()+"/"+dtid));
		ByteArrayOutputStream baos=new ByteArrayOutputStream();
		int btnum=0;
		byte[]buf=new byte[1024];
		while((btnum=fiis.read(buf))!=-1)
			baos.write(buf, 0, btnum);
		data.setData(baos.toByteArray());
		fiis.close();
		baos.close();
		
		return data;
	}

	/**
	 * 获得worker分得的包名
	 * @param wkId
	 * @param pubId
	 * @param pjId
	 * @return
	 */
	private String pkgId(String wkId,String pubId,String pjId){
		File wkfd=new File(root+"/workers/"+wkId);
		Iterator<String>it=Stream.of(wkfd.list())
				.filter(x->x.substring(0,x.lastIndexOf("_")).equals(pubId+"_"+pjId))
				.iterator();
		if(!it.hasNext())
			return null;
		String pkgId=it.next();
		pkgId="pac"+pkgId.substring(pkgId.lastIndexOf("_")+1);
		return pkgId;
	}
	
	@Override
	public int push(String workerId, String publisherId, String projectId) throws FileNotFoundException, ClassNotFoundException, IOException {
		// TODO Auto-generated method stub
		File wkfd=new File(root+"/workers/"+workerId);
		Iterator<String>it=Stream.of(wkfd.list())
				.filter(x->x.substring(0,x.lastIndexOf("_")).equals(publisherId+"_"+projectId))
				.iterator();
		if(!it.hasNext())
			return -1;
		
		int count=0;
		
		Project pj=(Project) db.retrieve("projects", publisherId+"_"+projectId);
		
		File pkg=new File(root+"/workers/"+workerId+"/"+it.next());
		
		String[]tags=pkg.list();
		for(String str:tags){
			Tag tag=(Tag)serialize.readObj(pkg.getPath()+"/"+str);
			//比较tag是否符合要求，若符合要求，才push
			if(tag.getScore()>=pj.getTagRequirement().getGradesLimit()){
				count++;
				
				//send tag,worker要在tag名字前加上自己的id!
				serialize.writeObj(root+"/publishers/"+publisherId+"/"+projectId+"/tags/"+workerId+"_"+str,
						tag);
			}
		}
		
		//log in the pushEvents
		String date=new SimpleDateFormat("yyyy-MM-dd HH:mm::ss").format(new Date());
		csv.appendCSV(new String[]{workerId,date,"commitId"}, 
				root+"/publishers/"+publisherId+"/"+projectId+"/pushEvents.csv");

		//修改worker对象
		Worker wk=(Worker) db.retrieve("users", workerId);
		List<String>pjs=wk.getUnfinishedPj();
		pjs.remove(publisherId+"_"+projectId);
		wk.setUnfinishedPj(pjs);
		pjs=wk.getFinishedPj();
		pjs.add(publisherId+"_"+projectId);
		wk.setFinishedPj(pjs);
		db.modify("users", wk);
		
		return count;
	}

	@Override
	public List<String> viewFinishedPj(String wkId) throws FileNotFoundException, ClassNotFoundException, IOException {
		// TODO Auto-generated method stub
		Worker wk=(Worker) db.retrieve("users", wkId);
		return wk.getFinishedPj();
	}

	@Override
	public List<String> viewUnfinishedPj(String wkId) throws FileNotFoundException, ClassNotFoundException, IOException {
		// TODO Auto-generated method stub
		Worker wk=(Worker) db.retrieve("users", wkId);
		return wk.getUnfinishedPj();
	}

	@Override
	public boolean isPjFinished(String wkId, String pjKey) throws FileNotFoundException, ClassNotFoundException, IOException {
		// TODO Auto-generated method stub
		Worker wk=(Worker) db.retrieve("users", wkId);
		return wk.getFinishedPj().contains(pjKey);
	}

}
