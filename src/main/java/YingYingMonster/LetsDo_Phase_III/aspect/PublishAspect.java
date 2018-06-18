package YingYingMonster.LetsDo_Phase_III.aspect;

import YingYingMonster.LetsDo_Phase_III.entity.Project;
import YingYingMonster.LetsDo_Phase_III.entity.TestProject;
import YingYingMonster.LetsDo_Phase_III.entity.event.PublishEvent;
import YingYingMonster.LetsDo_Phase_III.repository.event.PublishEventRepository;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Aspect
@Component
public class PublishAspect {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    PublishEventRepository publishEventRepository;

    @Pointcut(value = "execution(* YingYingMonster.LetsDo_Phase_III.service.PublisherService.createProject(..))")
    public void publishPoint(){}

    @AfterReturning(value = "publishPoint()", pointcut = "publishPoint()", returning = "project")
    public void recordPublish(Project project) {
        logger.info("publish aspect add publish event");
        PublishEvent publishEvent = new PublishEvent(project.getPublisherId(), project.getId(), new Date(), null);
        publishEventRepository.saveAndFlush(publishEvent);
        logger.info("publish aspect end");
    }

    @Pointcut(value = "execution(* YingYingMonster.LetsDo_Phase_III.service.PublisherService.addTestProject(..))")
    public void addTestPoint(){}

    @AfterReturning(value = "addTestPoint()", pointcut = "addTestPoint()", returning = "testProject")
    public void updateInviteCode(TestProject testProject) {
        logger.info("publish aspect update invite code");
        PublishEvent publishEvent = publishEventRepository.findByProjectId(testProject
                .getProject().getId());
        publishEvent.setInviteCode(testProject.getInviteCode());
        publishEventRepository.saveAndFlush(publishEvent);
        logger.info("publish aspect end");
    }

}
