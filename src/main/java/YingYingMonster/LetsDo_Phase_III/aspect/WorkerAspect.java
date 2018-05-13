package YingYingMonster.LetsDo_Phase_III.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import YingYingMonster.LetsDo_Phase_III.dao.MockDB;
import YingYingMonster.LetsDo_Phase_III.model.Tag;
import YingYingMonster.LetsDo_Phase_III.model.User;
import YingYingMonster.LetsDo_Phase_III.model.Worker;

@Aspect
@Component
public class WorkerAspect {

	@Autowired
	MockDB db;
	
	//Worker push时触发
	@Pointcut("execution(* YingYingMonster.LetsDo_Phase_III.dao."
			+ "WorkerDAO.push(..))&&args(workerId,publisherId,projectId)")
	public void pushListener(String workerId,String publisherId,String projectId){}
	
	//Worker uploadTag时触发
	@Pointcut("execution(* YingYingMonster.LetsDo_Phase_III.dao."
			+ "WorkerDAO.uploadTag(..))"
			+ "&&args(workerId,publisherId,projectId,tagId,tag)")
	public void uploadTagListener(String workerId,String publisherId,String projectId,
			String tagId,Tag tag){}
	
	@Around("uploadTagListener(workerId,publisherId,projectId,tagId,tag)")
	public boolean afterUploadTag(ProceedingJoinPoint pjp,String workerId,String publisherId,
			String projectId,String tagId,Tag tag) throws Throwable{
		boolean flag=(boolean) pjp.proceed();
		//上传成功修改worker已做tag数量
		if(flag){
			Worker wk=(Worker) db.retrieve("users", workerId);
			wk.setTagNum(wk.getTagNum()+1);
			db.modify("users", wk);
//			System.out.println("worker "+workerId+" upload a tag");
		}
		
		return flag;
	}
	
	@Around("pushListener(workerId,publisherId,projectId)")
	public int afterPush(ProceedingJoinPoint pjp,
			String workerId,String publisherId,String projectId) throws Throwable{
		int count=(int) pjp.proceed();

		System.out.println("worker "+workerId+" successfully push "+count+" tags");
		
		Worker wk=(Worker) db.retrieve("users", workerId);
		int exp=wk.getExp()+count*10;
		//level up if exp exceeds the bound
		if(exp>=wk.getGap()){
			wk.setExp(exp-wk.getGap());
			wk.setLevel(wk.getLevel()+1);
		}else{
			wk.setExp(exp);
		}
		wk.setPassedTagNum(wk.getPassedTagNum()+count);
		db.modify("users", wk);
		
		return count;
	}
}
