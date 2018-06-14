package YingYingMonster.LetsDo_Phase_III.aspect;

import YingYingMonster.LetsDo_Phase_III.entity.Project;
import YingYingMonster.LetsDo_Phase_III.entity.TestProject;
import YingYingMonster.LetsDo_Phase_III.entity.event.PublishEvent;
import YingYingMonster.LetsDo_Phase_III.repository.event.PublishEventRepository;
import com.sun.org.apache.xerces.internal.util.SynchronizedSymbolTable;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Aspect
@Component
public aspect PublisherAspect {

    @Autowired
    PublishEventRepository publishEventRepository;

    @Pointcut(value = "execution(* YingYingMonster.LetsDo_Phase_III.service" +
            ".PublisherService.createProject(..))")
    public void publishEvent() {}

    @Pointcut(value = "execution(* YingYingMonster.LetsDo_Phase_III.service" +
            ".PublisherService.addTestProject(..))")
    public void addTestProject(){}

    @AfterReturning(value = "publishEvent()",returning = "project")
    public void createPublishEvent(Project project) {
        System.out.println("create publish event");
        PublishEvent publishEvent = new PublishEvent(project.getPublisherId(), project.getId(),
                new Date(), null);

        publishEventRepository.saveAndFlush(publishEvent);
    }

    @AfterReturning(value = "addTestProject()", returning = "testProject")
    public void setInviteCode(TestProject testProject) {
        System.out.println("add invite code to publish event");
        PublishEvent publishEvent = publishEventRepository.findByProjectId(testProject
                .getProject().getId());
        publishEvent.setInviteCode(testProject.getInviteCode());
        publishEventRepository.saveAndFlush(publishEvent);
    }
}
