package YingYingMonster.LetsDo_Phase_III.repository;

import YingYingMonster.LetsDo_Phase_III.entity.Project;
import YingYingMonster.LetsDo_Phase_III.model.MarkMode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface ProjectRepository extends JpaRepository<Project,Long> {
    public Project findById(long id);

    public List<Project> findByPublisherId(long publisherId);

    public List<Project> findByProjectName(String projectName);

    public Project findByPublisherIdAndProjectName(long publisherId,String projectName);

    public List<Project> findByType(MarkMode type);

    @Query("select p from Project p where p.startDate <= ?1 and p.endDate >= ?1")
    public List<Project> findProjectsProcessing(String dateInstance);

    @Query("select p from Project p where p.startDate > ?1")
    public List<Project> findProjectsUnstart(String dateInstance);

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

}
