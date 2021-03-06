package YingYingMonster.LetsDo_Phase_III.repository;

import YingYingMonster.LetsDo_Phase_III.entity.Image;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {

    public Image findById(long id);

    public List<Image> findByProjectId(long projectId);

    public List<Image> findByProjectId(long projectId, Sort sort);

    public Page<Image> findByProjectIdAndIsFinishedFalseAndIsTestFalse(long projectId, Pageable pageable);

    public Page<Image> findByProjectIdAndIsFinishedTrueAndIsTestFalse(long projectId, Pageable pageable);

    public List<Image> findByProjectIdAndIsFinishedFalseAndIsTestTrue(long projectId);

    public Page<Image> findByProjectIdAndIsFinishedTrueAndIsTestTrue(long projectId, Pageable pageable);

    public List<Image> findByprojectIdAndIsTest(long projectId, boolean isTest);

    public List<Image> findByProjectIdAndIsFinished(long projectId, boolean isFinished);

    @Modifying
    @Transactional(rollbackOn = Exception.class)
    @Query("update Image i set i.isTest = ?2 where i.id =?1")
    public void updateIsTest(long id, boolean isTest);

    @Modifying
    @Transactional(rollbackOn = Exception.class)
    @Query("update Image i set i.isFinished = ?2 where i.id =?1")
    public void updateIsFinished(long id, boolean isFinished);

    @Modifying
    @Transactional(rollbackOn = Exception.class)
    @Query("update Image i set i.currentNum=i.currentNum+?2 where i.id=?1")
    public void updateCurrentNum(long id,int num);
}

