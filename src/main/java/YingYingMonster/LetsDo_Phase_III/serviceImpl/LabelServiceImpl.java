package YingYingMonster.LetsDo_Phase_III.serviceImpl;

import YingYingMonster.LetsDo_Phase_III.entity.*;
import YingYingMonster.LetsDo_Phase_III.repository.CommitEventRepository;
import YingYingMonster.LetsDo_Phase_III.repository.LabelRepository;
import YingYingMonster.LetsDo_Phase_III.repository.ProjectRepository;
import YingYingMonster.LetsDo_Phase_III.repository.UserLabelRepository;
import YingYingMonster.LetsDo_Phase_III.service.LabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LabelServiceImpl implements LabelService {
    @Autowired
    UserLabelRepository uslr;
    @Autowired
    LabelRepository lbr;
    @Autowired
    ProjectRepository pjr;
    @Autowired
    CommitEventRepository cer;

    @Override
    public List<Label> findAllLabel() {
        return lbr.findAll();
    }

    @Override
    public List<UserLabel> findWorkerAllLabel(long workerId) {
        return uslr.findByUserId(workerId);
    }

    @Override
    public List<String> findProjectAllLabel(long projectId) {
        return pjr.findById(projectId).getLabels();
    }

    @Override
    public UserLabel findWorkerLabel(long workerId, String labelName) {
        return uslr.findByUserIdAndName(workerId,labelName);
    }

    @Override
    public boolean addWorkerLabel(UserLabel userLabel) {
        if(uslr.findByUserIdAndName(userLabel.getUserId(),userLabel.getName())!=null)
            return false;
        UserLabel ul=uslr.save(userLabel);
        if(ul==null)
            return false;
        return true;
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
    public boolean deleteWorkerLabel(UserLabel userLabel) {
        if(uslr.findById(userLabel.getId())==null)
            return false;
        uslr.delete(userLabel);
        return true;
    }


    @Override
    public boolean updateWorkerLabel(UserLabel userLabel) {
        if(uslr.findById(userLabel.getId())==null)
            return false;
        uslr.save(userLabel);
        return true;
    }

    @Override
    public void updateWorkerAfterProject(long workerId,long projectId) {
        List<CommitEvent> commits=cer.findByProjectid(projectId);
        for(CommitEvent commit:commits){

        }
    }
}
