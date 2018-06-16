package YingYingMonster.LetsDo_Phase_III.repository.event;

import YingYingMonster.LetsDo_Phase_III.entity.event.JoinEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

public interface JoinEventRepository extends JpaRepository<JoinEvent, Long> {
    public JoinEvent findByWorkerIdAndProjectId(long workerId, long projectId);

//    @Query("select j.projectId from JoinEvent j where j.workerId=?1")
    public List<JoinEvent> findByWorkerId(long workerId);

    public List<JoinEvent> findByWorkerIdAndActiveTrue(long workerId);

//    @Query("select j.workerId from JoinEvent j where j.projectId=?1")
    public List<JoinEvent> findByProjectId(long projectId);

    public List<JoinEvent> findByProjectIdAndActiveTrue(long projectId);

    public List<JoinEvent> findByDateBetween(Date start, Date end);

    public List<JoinEvent> findByDateBefore(Date date);

    public List<JoinEvent> findByDateAfter(Date date);

    @Modifying
    @Transactional(rollbackOn = Exception.class)
    @Query("update JoinEvent j set j.workState =?3 where j.workerId=?1 and j.projectId=?2")
    public void setWorkState(long workerId, long projectId, String workState);

    @Modifying
    @Transactional(rollbackOn = Exception.class)
    @Query("update JoinEvent j set j.testScore=?3 where j.workerId=?1 and j.projectId=?2")
    public void setTestScore(long workerId, long projectId, double testScore);

}
