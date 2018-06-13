package YingYingMonster.LetsDo_Phase_III.service;

import YingYingMonster.LetsDo_Phase_III.entity.Ability;
import YingYingMonster.LetsDo_Phase_III.entity.Label;

import java.util.List;

public interface LabelService {

    public List<Label> findAllLabel();

    public List<Label> findWorkerAllLabel(long workerId);

    public List<String> findProjectAllLabel(long projectId);

    public Label findWorkerLabel(long workerId,String labelName);

    public boolean addWorkerLabel(long workerId,Label userLabel);

    public boolean addProjectLabel(Label projectLabel,long projectId);

    public boolean updateUserAbility(long workerId, Ability newAbility);
}
