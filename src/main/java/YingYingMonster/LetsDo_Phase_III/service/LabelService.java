package YingYingMonster.LetsDo_Phase_III.service;

import YingYingMonster.LetsDo_Phase_III.entity.ProjectLabel;
import YingYingMonster.LetsDo_Phase_III.entity.UserLabel;

import java.util.List;

public interface LabelService {

    public List<UserLabel> findWorkerAllLabel(long workerId);

    public List<ProjectLabel> findProjectAllLabel(long projectId);

    public UserLabel findWorkerLabel(long workerId,String labelName);

    public boolean addWorkerLabel(UserLabel userLabel);

    public boolean addProjectLabel(ProjectLabel projectLabel);

    public boolean deleteWorkerLabel(UserLabel userLabel);

    public boolean updateWorkerLabel(UserLabel userLabel);
}
