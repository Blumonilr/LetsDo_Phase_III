package YingYingMonster.LetsDo_Phase_III.aspect;

import java.util.List;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import YingYingMonster.LetsDo_Phase_III.service.WorkerService;

@Aspect
//@Component
public class MyProjectsDebug {

	@Autowired WorkerService wks;
	
	@Around("execution(* YingYingMonster.LetsDo_Phase_III.controller."
			+ "MyProjectsController.getList(..))&&args(userId)")
	public String getList(ProceedingJoinPoint pjp,String userId) throws Throwable{
		System.out.println("============");
		System.out.println("MyProjectsController.getList");
		
//		List<String>list=wks.viewMyProjects(userId);
//		for(String str:list){
//			boolean flag=wks.isPjFinished(userId, str);
//			System.out.println("==="+flag);
//			System.out.println("==="+str);
//		}
//		System.out.println("===getList for : "+userId);
		
		String res=(String) pjp.proceed();
		System.out.println("==="+res);
		return res;
	}
}
