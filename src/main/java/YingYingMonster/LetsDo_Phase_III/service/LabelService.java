package YingYingMonster.LetsDo_Phase_III.service;

import YingYingMonster.LetsDo_Phase_III.entity.Label;
import YingYingMonster.LetsDo_Phase_III.entity.UserLabel;

import java.util.List;

public interface LabelService {

    public List<Label> findAllLabel();

    public List<UserLabel> findWorkerAllLabel(long workerId);

    public List<String> findProjectAllLabel(long projectId);

    public UserLabel findWorkerLabel(long workerId,String labelName);

    public boolean addWorkerLabel(UserLabel userLabel);

    public boolean addProjectLabel(Label projectLabel,long projectId);

    public boolean deleteWorkerLabel(UserLabel userLabel);

    public boolean updateWorkerLabel(UserLabel userLabel);

    public void updateWorkerAfterProject(long workerId,long projectId);
}
