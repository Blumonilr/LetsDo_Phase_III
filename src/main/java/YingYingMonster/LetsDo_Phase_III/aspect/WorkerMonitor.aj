package YingYingMonster.LetsDo_Phase_III.aspect;


import YingYingMonster.LetsDo_Phase_III.entity.CommitEvent;
import YingYingMonster.LetsDo_Phase_III.entity.Project;
import YingYingMonster.LetsDo_Phase_III.entity.Tag;
import YingYingMonster.LetsDo_Phase_III.repository.AbilityRepository;
import YingYingMonster.LetsDo_Phase_III.repository.CommitEventRepository;
import YingYingMonster.LetsDo_Phase_III.service.ProjectService;
import YingYingMonster.LetsDo_Phase_III.service.UserService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Aspect
@Component
public aspect WorkerMonitor {

    @Autowired
    CommitEventRepository commitEventRepository;
    @Autowired
    AbilityRepository abilityRepository;
    @Autowired
    UserService userService;
    @Autowired
    ProjectService projectService;

    /**
     * 项目测试集答案制作切点
     * @param workerId
     * @param tag
     */
    @Pointcut(value = "execution(* YingYingMonster.LetsDo_Phase_III.service.TestProjectService" +
            ".uploadAnswer(..)) &&args(workerId,tag) ", argNames = "workerId,tag")
    public void innerTestPoint(long workerId,Tag tag){}

    /**
     * execute when worker joins a project
     * @param workerId
     * @param projectId
     */
    @Pointcut(value = "execution(int YingYingMonster.LetsDo_Phase_III.service." +
            "WorkerService.joinProject(..))&&args(workerId,projectId) ", argNames = "workerId,projectId")
    public void joinProject(long workerId, long projectId) {}


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

    @AfterReturning(value = "joinProject(workerId,projectId)",returning = "res")
    public void updateAbilityWhenJoin(long workerId, long projectId,int res) {

        if (res == 0) {
            Project project = projectService.getAProject(projectId);

        }
    }
}
