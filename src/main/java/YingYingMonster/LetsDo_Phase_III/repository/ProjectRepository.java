package YingYingMonster.LetsDo_Phase_III.repository;

import YingYingMonster.LetsDo_Phase_III.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project,Long> {
    public Project findById(long id);

    public List<Project> findByPublisherId(long publisherId);

    public List<Project> findByProjectId(String publisherId);

    public Project findByPublisherIdAndProjectId(long publisherId,String projectId);

    @Query("select p from Project p where p.startDate <= ?1 and p.endDate >= ?1")
    public List<Project> findProjectsProcessing(String dateInstance);

    @Query("select p from Project p where p.startDate > ?1")
    public List<Project> findProjectsUnstart(String dateInstance);

    @Query("select p from Project p where p.endDate < ?1")
    public List<Project> findProjectsEnded(String dateInstance);

    @Query("update Project p set p.money = ?2 where p.id =?1")
    public void updateMoney(long id,int money);

    @Query("update Project p set p.currWorkerNum = p.currWorkerNum + 1 where p.id =?1")
    public void updateCurrWorkerNum(long id);

    @Query("update Project p set p.picNum = ?2 where p.id =?1")
    public void updatePicNum(long id, int picNum);
}
