package YingYingMonster.LetsDo_Phase_III.serviceImpl;

import YingYingMonster.LetsDo_Phase_III.entity.ProjectLabel;
import YingYingMonster.LetsDo_Phase_III.entity.UserLabel;
import YingYingMonster.LetsDo_Phase_III.repository.LabelRepository;
import YingYingMonster.LetsDo_Phase_III.repository.ProjectLabelRepository;
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
    ProjectLabelRepository prlr;
    @Autowired
    LabelRepository lbr;

    @Override
    public List<UserLabel> findWorkerAllLabel(long workerId) {
        return uslr.findByUserId(workerId);
    }

    @Override
    public List<ProjectLabel> findProjectAllLabel(long projectId) {
        return prlr.findByProjectId(projectId);
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
    public boolean addProjectLabel(ProjectLabel projectLabel) {
        if(prlr.findByProjectIdAndName(projectLabel.getProjectId(),projectLabel.getName())!=null)
            return false;
        ProjectLabel pl=prlr.save(projectLabel);
        if(pl==null)
            return false;
        return true;
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
}
