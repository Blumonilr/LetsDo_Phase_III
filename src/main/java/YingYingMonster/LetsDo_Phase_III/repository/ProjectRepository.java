package YingYingMonster.LetsDo_Phase_III.repository;

import YingYingMonster.LetsDo_Phase_III.entity.Project;
import YingYingMonster.LetsDo_Phase_III.entity.TestProject;
import YingYingMonster.LetsDo_Phase_III.entity.MarkMode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface ProjectRepository extends JpaRepository<Project,Long> {
    
    public Project findById(long id);

    @Query("select p from Project p where p.publisherId =?1 and (p.projectName like %?2% or p.tagRequirement like %?2%)")
    public List<Project> findByPublisherIdAndStringAttributes(long publisherId,String key);

    public List<Project> findByPublisherId(long publisherId);

    public List<Project> findByProjectName(String projectName);

    public Project findByPublisherIdAndProjectName(long publisherId,String projectName);

    public List<Project> findByType(MarkMode type);

    @Query("select p from Project p where p.endDate >= ?1")
    public List<Project> findProjectsProcessing(String dateInstance);

    @Query("select p from Project p where p.endDate < ?1")
    public List<Project> findProjectsEnded(String dateInstance);

    @Modifying
    @Transactional(rollbackOn = Exception.class)
    @Query("update Project p set p.money = ?2 where p.id =?1")
    public void updateMoney(long id,int money);

    @Modifying
    @Transactional(rollbackOn = Exception.class)
    @Query("update Project p set p.currWorkerNum = p.currWorkerNum +1 where p.id =?1")
    public void updateCurrWorkerNum(long id);

    @Modifying
    @Transactional(rollbackOn = Exception.class)
    @Query("update Project p set p.picNum = ?2 where p.id =?1")
    public void updatePicNum(long id, int picNum);

    @Modifying
    @Transactional(rollbackOn = Exception.class)
    @Query("update Project p set p.testProject =?2 where p.id=?1")
    public void updateTestProject(long id, TestProject testProject);

}
