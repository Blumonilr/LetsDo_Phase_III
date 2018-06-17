package YingYingMonster.LetsDo_Phase_III.repository;

import YingYingMonster.LetsDo_Phase_III.entity.TestProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface TestProjectRepository extends JpaRepository<TestProject, Long> {

    public TestProject findByInviteCode(String code);

    @Modifying
    @Transactional(rollbackOn = Exception.class)
    @Query("update TestProject t set t.inviteCode=?2 where t.id=?1 ")
    public void updateInviteCode(long id, String code);

    public TestProject findById(long testProjectId);

}
