package YingYingMonster.LetsDo_Phase_III.aspect;

import java.io.File;
import java.util.Iterator;
import java.util.stream.Stream;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.springframework.beans.factory.annotation.Autowired;

//@Aspect
//@Component
public class WorkerDAODebug {

	@Autowired String root;
	
	@Around("execution(* YingYingMonster.LetsDo_Phase_III.dao.WorkerDAO.fork(..))"
			+ "&&args(workerId,publisherId,projectId)")
	public boolean fork(ProceedingJoinPoint pjp,String workerId,String publisherId,
			String projectId) throws Throwable{
		
		if(workerId==null||publisherId==null||projectId==null){
			System.out.println("+++null args in fork");
			return false;
		}
		
		File folder=new File(root+"/workers/"+workerId);
		if(!folder.exists()){
			System.out.println("+++worker not exist");
			return false;
		}
		
		Iterator<String>it=Stream.of(folder.list())
				.filter(x->x.substring(0,x.lastIndexOf("_")).equals(publisherId+"_"+projectId))
				.iterator();
		
		if(it.hasNext()){
			System.out.println("+++duplicate");
			return false;//不能重复fork
		}
		
		return (boolean) pjp.proceed();
		
	}
}
