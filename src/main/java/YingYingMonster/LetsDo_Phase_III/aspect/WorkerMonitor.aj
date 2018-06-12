package YingYingMonster.LetsDo_Phase_III.aspect;


import YingYingMonster.LetsDo_Phase_III.entity.CommitEvent;
import YingYingMonster.LetsDo_Phase_III.entity.Tag;
import YingYingMonster.LetsDo_Phase_III.repository.CommitEventRepository;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Aspect
@Component
public aspect WorkerMonitor {

    @Autowired
    CommitEventRepository commitEventRepository;

    /**
     * 项目测试集答案制作切点
     * @param workerId
     * @param tag
     */
    @Pointcut(value = "execution(* YingYingMonster.LetsDo_Phase_III.service.TestProjectService" +
            ".uploadAnswer(..)) &&args(workerId,tag) ", argNames = "workerId,tag")
    public void innerTestPoint(long workerId,Tag tag){}


    /**
     *
     * @param proceedingJoinPoint
     * @param workerId
     * @param tag
     * @throws Throwable
     */
    @Around(value = "innerTestPoint(workerId,tag)")
    public void recordTestProjectCommitEvent(ProceedingJoinPoint proceedingJoinPoint,
                                             long workerId, Tag tag) throws Throwable {
        Tag res = (Tag) proceedingJoinPoint.proceed();
        CommitEvent commitEvent = new CommitEvent(workerId, res.getProjectId(), res.getId(),
                res.getImageId(), new Date());

        System.out.println("monitor in package aspect works!");
    }

//    @Pointcut(value = "execution() &&args(tag) ")
//    public void workPoint(Tag tag) {}
}
