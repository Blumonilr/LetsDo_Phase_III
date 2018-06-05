package YingYingMonster.LetsDo_Phase_III.repository;

import YingYingMonster.LetsDo_Phase_III.entity.ProjectLabel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectLabelRepository extends JpaRepository<ProjectLabel,Long> {
    public List<ProjectLabel> findAll();

    public List<ProjectLabel> findByProjectId(long projectId);
}
