package YingYingMonster.LetsDo_Phase_III.aspect;


import YingYingMonster.LetsDo_Phase_III.csHandler;
import YingYingMonster.LetsDo_Phase_III.entity.*;
import YingYingMonster.LetsDo_Phase_III.entity.event.CommitEvent;
import YingYingMonster.LetsDo_Phase_III.entity.role.User;
import YingYingMonster.LetsDo_Phase_III.entity.role.Worker;
import YingYingMonster.LetsDo_Phase_III.repository.AbilityRepository;
import YingYingMonster.LetsDo_Phase_III.repository.event.CommitEventRepository;
import YingYingMonster.LetsDo_Phase_III.repository.LabelRepository;
import YingYingMonster.LetsDo_Phase_III.repository.role.UserRepository;
import YingYingMonster.LetsDo_Phase_III.service.ProjectService;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Aspect
@Component
public aspect WorkerAspect {

    @Autowired
    CommitEventRepository commitEventRepository;
    @Autowired
    AbilityRepository abilityRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ProjectService projectService;
    @Autowired
    LabelRepository labelRepository;
    @Autowired
    csHandler cshandler;


    @Pointcut(value = "execution(* YingYingMonster.LetsDo_Phase_III.service." +
            "TestProjectService.uploadAnswer(..))")
    public void addCommitEventInMakeAnswer(){}

    /**
     * execute when worker joins a project
     * @param workerId
     * @param projectId
     */
    @Pointcut(value = "execution(int YingYingMonster.LetsDo_Phase_III.service." +
            "WorkerService.joinProject(..))&&args(workerId,projectId) ", argNames = "workerId,projectId")
    public void joinProject(long workerId, long projectId) {}

    @Pointcut(value = "execution(* YingYingMonster.LetsDo_Phase_III.service.WorkerService.uploadTag(..))")
    public void addCommitEvent(){}



    /*=============================================================================================
    * ============================================================================================*/


    @AfterReturning(value = "addCommitEventInMakeAnswer()", returning = "tag")
    public void recordTestProjectCommitEvent(Tag tag) {

        System.out.println("add commit event in making test answer");
        CommitEvent commitEvent = new CommitEvent(tag.getWorkerId(), tag.getProjectId(), tag.getId(),
                tag.getImageId(), new Date());
        commitEventRepository.saveAndFlush(commitEvent);


    }

    @AfterReturning(value = "joinProject(workerId,projectId)",returning = "res")
    public void updateAbilityWhenJoin(long workerId, long projectId,int res) {

        if (res == 0) {
            Project project = projectService.getAProject(projectId);
            User user = userRepository.findById(workerId);

            List<Ability> abilities = abilityRepository.findByUser(user);
            List<String> user_label_names = abilities.stream().map(x -> x.getLabel().getName())
                    .collect(Collectors.toList());

            List<String> labels = project.getLabels();

            //worker已经有了project的label，更新bias
            abilities.stream().forEach(x->{
                if(labels.contains(x.getLabel().getName())){ x.setBias(x.getBias() + 1); }
            });
            abilityRepository.saveAll(abilities);
            //worker没有project的label，则新加一个ability
            for (String string : labels) {
                if (!user_label_names.contains(string)) {
                    Label label = null;
                    Ability ability = new Ability(user, label);
                    abilityRepository.save(ability);
                }
            }
            abilityRepository.flush();
        }
    }

    @AfterReturning(value = "addCommitEvent()",returning = "tag")
    public void recordWorkCommitEvent(Tag tag) {

        System.out.println("add commit event in working");
        CommitEvent commitEvent = new CommitEvent(tag.getWorkerId(), tag.getProjectId(), tag.getId(),
                tag.getImageId(), new Date());
        commitEventRepository.saveAndFlush(commitEvent);

        Worker worker = (Worker) userRepository.findById(tag.getWorkerId());
        worker.setTagNum(worker.getTagNum() + 1);
        userRepository.saveAndFlush(worker);



        Project project = projectService.getAProject(tag.getProjectId());
        for(String l:project.getLabels){
            Ability ability=abilityRepository.findByLabelAndUser(labelRepository.findByName(l),worker);
            ability.setLabelHistoryNum(ability.getLabelHistoryNum()+1);
            abilityRepository.saveAndFlush(ability);
        }

        try {
            System.out.println("java tries to connect to python");

            cshandler.post("http://localhost:5000/postImage",Long.toString(tag.getImageId())+"_"+project.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
