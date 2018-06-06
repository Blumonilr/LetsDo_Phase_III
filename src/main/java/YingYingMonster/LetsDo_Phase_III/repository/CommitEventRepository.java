package YingYingMonster.LetsDo_Phase_III.repository;

import YingYingMonster.LetsDo_Phase_III.entity.CommitEvent;
import YingYingMonster.LetsDo_Phase_III.model.CommitResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface CommitEventRepository extends JpaRepository<CommitEvent, Long> {

    public List<CommitEvent> findByProjectid(long projectid);

    public List<CommitEvent> findByWorkerid(long workerid);

    /**
     *
     * @param start   not null!
     * @param end     not null!
     * @return
     */
    public List<CommitEvent> findByCommitTimeBetween(Date start, Date end);

    public List<CommitEvent> findByImageid(long imageid);

    public List<CommitEvent> findByCommitResult(CommitResult commitResult);

    public List<CommitEvent> findByWorkeridAndProjectidAndImageid(long workerid, long projectid, long imageid);

    public List<CommitEvent> findByWorkeridAndCommitTimeBetween(long workerid, Date start, Date end);

    //TBD...
}
