package YingYingMonster.LetsDo_Phase_III.aspect;

import YingYingMonster.LetsDo_Phase_III.csHandler;
import YingYingMonster.LetsDo_Phase_III.entity.*;
import YingYingMonster.LetsDo_Phase_III.entity.event.CommitEvent;
import YingYingMonster.LetsDo_Phase_III.entity.role.User;
import YingYingMonster.LetsDo_Phase_III.entity.role.Worker;
import YingYingMonster.LetsDo_Phase_III.repository.AbilityRepository;
import YingYingMonster.LetsDo_Phase_III.repository.ImageRepository;
import YingYingMonster.LetsDo_Phase_III.repository.LabelRepository;
import YingYingMonster.LetsDo_Phase_III.repository.ProjectRepository;
import YingYingMonster.LetsDo_Phase_III.repository.event.CommitEventRepository;
import YingYingMonster.LetsDo_Phase_III.repository.role.UserRepository;
import YingYingMonster.LetsDo_Phase_III.service.LabelService;
import YingYingMonster.LetsDo_Phase_III.service.ProjectService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.engine.CommentStructureHandler;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Aspect
@Component
public class WorkAspect {

    @Autowired
    CommitEventRepository commitEventRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ProjectService projectService;
    @Autowired
    AbilityRepository abilityRepository;
    @Autowired
    csHandler handler;
    @Autowired
    LabelRepository labelRepository;
    @Autowired
    ImageRepository imageRepository;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Pointcut(value = "execution(* YingYingMonster.LetsDo_Phase_III.service.TestProjectService.uploadAnswer(..))")
    public void makeTestAnswerCommitPoint  (){}

    @AfterReturning(value = "makeTestAnswerCommitPoint()", pointcut = "makeTestAnswerCommitPoint()", returning = "tag")
    public void recordMakingTestProjectAnswer(Tag tag){
        logger.info("work aspect record commit in making test project answer");
        CommitEvent commitEvent = new CommitEvent(tag.getWorkerId(), tag.getProjectId(), tag.getId(),
                tag.getImageId(), new Date());
        commitEventRepository.saveAndFlush(commitEvent);
        logger.info("work aspect end");
    }

    @Pointcut(value = "execution(* YingYingMonster.LetsDo_Phase_III.service.WorkerService.joinProject(..))&&args(workerId,projectId)")
    public void joinEventPoint(long workerId, long projectId) {}

    @Around(value = "joinEventPoint(workerId,projectId)")
    public int recordJoinEvent(ProceedingJoinPoint point, long workerId, long projectId) throws Throwable {
        logger.info("work aspect record join event");
        int res = (int) point.proceed();

        if (res == 0) {
            Project project = projectService.getAProject(projectId);
            User user = userRepository.findById(workerId);

            List<Ability> abilities = abilityRepository.findByUser(user);
            List<String> user_label_names = abilities.stream().map(x -> x.getLabel().getName())
                    .collect(Collectors.toList());
            logger.info("user ability==null? : {}", abilities == null);
            List<String> labels = project.getLabels();

            //worker已经有了project的label，更新bias
            abilities.stream().forEach(x->{
                if(labels.contains(x.getLabel().getName())){ x.setBias(x.getBias() + 1); }
            });
            abilityRepository.saveAll(abilities);
            //worker没有project的label，则新加一个ability
            for (String string : labels) {
                if (!user_label_names.contains(string)) {
                    Label label = labelRepository.findByName(string);
                    if (label == null) {
                        //这个label是上传者新加的，则先保存label对象
                        label = new Label(string);
                        label = labelRepository.saveAndFlush(label);
                    }

                    Ability ability = new Ability(user, label);
                    abilityRepository.save(ability);
                }
            }
            abilityRepository.flush();
        }
        logger.info("work aspect end");
        return res;
    }

    @Pointcut(value = "execution(* YingYingMonster.LetsDo_Phase_III.service.WorkerService.uploadTag(..))")
    public void commitPoint() {}

    @AfterReturning(value = "commitPoint()", pointcut = "commitPoint()", returning = "tag")
    public void recordCommitEvent(Tag tag) {
        logger.info("work aspect record commit event");

        // 记录commit event，同一张图片的commit event应该覆盖
        CommitEvent commitEvent = commitEventRepository.findByWorkeridAndImageid(tag.getWorkerId(), tag.getImageId());
        if (commitEvent == null) {
            commitEvent = new CommitEvent(tag.getWorkerId(), tag.getProjectId(), tag.getId(),
                    tag.getImageId(), new Date());
            commitEventRepository.saveAndFlush(commitEvent);
        } else {
            commitEvent.setTagid(tag.getId());
            commitEvent.setCommitTime(new Date());
            commitEventRepository.saveAndFlush(commitEvent);
        }

        // 更新image current num
        imageRepository.updateCurrentNum(tag.getImageId(), 1);

        // 更新worker经验、金钱
        Worker worker = (Worker) userRepository.findById(tag.getWorkerId());
        worker.setTagNum(worker.getTagNum() + 1);
        int exp = worker.getExp();
        if (exp + 10 > worker.getGAP()) {
            worker.setExp(exp + 10 - worker.getGAP());
            worker.setLevel(worker.getLevel() + 1);
        } else {
            worker.setExp(exp + 10);
        }
        worker.setMoney(worker.getMoney()+1);
        userRepository.saveAndFlush(worker);

        notifyPy(tag);

        logger.info("work aspect end");
    }

    private void notifyPy(Tag tag) {
        Image image = imageRepository.findById(tag.getImageId());
        Project project = projectService.getAProject(tag.getProjectId());
        if (image.isTest()) {
            try {
                logger.info("pthon evaluates test answer");
                handler.post("http://localhost:5000/postImage",
                        Long.toString(tag.getWorkerId())+"_"+Long.toString(tag.getImageId())+"_"+project.getType());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                logger.info("python evaluates answer");
                handler.post("http://localhost:5000/postImage",Long.toString(tag.getImageId())+"_"+project.getType());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
