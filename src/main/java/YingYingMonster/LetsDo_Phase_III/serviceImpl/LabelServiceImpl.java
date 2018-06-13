package YingYingMonster.LetsDo_Phase_III.serviceImpl;

import YingYingMonster.LetsDo_Phase_III.entity.*;
import YingYingMonster.LetsDo_Phase_III.repository.*;
import YingYingMonster.LetsDo_Phase_III.repository.event.CommitEventRepository;
import YingYingMonster.LetsDo_Phase_III.repository.role.UserRepository;
import YingYingMonster.LetsDo_Phase_III.service.LabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class LabelServiceImpl implements LabelService {
    @Autowired
    AbilityRepository abr;
    @Autowired
    LabelRepository lbr;
    @Autowired
    ProjectRepository pjr;
    @Autowired
    CommitEventRepository cer;
    @Autowired
    UserRepository usr;

    @Override
    public List<Label> findAllLabel() {
        return lbr.findAll();
    }

    @Override
    public List<Label> findWorkerAllLabel(long workerId) {
        List<Ability> list=abr.findByUser(usr.findById(workerId));
        List<Label> labels=new ArrayList<>();
        for(Ability a:list){
            labels.add(a.getLabel());
        }
        return labels;
    }

    @Override
    public List<String> findProjectAllLabel(long projectId) {
        return pjr.findById(projectId).getLabels();
    }

    @Override
    public Label findWorkerLabel(long workerId, String labelName) {
        List<Ability> list=abr.findByUser(usr.findById(workerId));
        for(Ability a:list){
            if (a.getLabel().getName().equals(labelName))
                return a.getLabel();
        }
        return null;
    }

    @Override
    public boolean addWorkerLabel(long workerId,Label userLabel) {
        Ability ability=new Ability(usr.findById(workerId),userLabel);
        if(abr.saveAndFlush(ability)!=null)
            return true;
        return false;
    }

    @Override
    public boolean addProjectLabel(Label projectLabel,long projectId) {
        Project pr=pjr.findById(projectId);
        pr.addLabel(projectLabel);
        if(pjr.saveAndFlush(pr)!=null)
            return true;
        return false;
    }

    @Override
    public boolean updateUserAbility(long workerId, Ability newAbility) {
        List<Ability> list=abr.findByUser(usr.findById(workerId));
        for(Ability a:list){
            if (a.getLabel().getName().equals(newAbility.getLabel().getName())) {
                a.setAccuracy(newAbility.getAccuracy());
                a.setBias(newAbility.getBias());
                abr.saveAndFlush(a);
                return true;
            }
        }
        return false;
    }


}
