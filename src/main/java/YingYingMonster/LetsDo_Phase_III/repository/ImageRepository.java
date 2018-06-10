package YingYingMonster.LetsDo_Phase_III.repository;

import YingYingMonster.LetsDo_Phase_III.entity.Image;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface ImageRepository extends JpaRepository<Image,Long> {
    public Image findById(long id);

    @Query("select i from Image i where i.projectId=?1")
    public Image getOneByProjectId(long projectId);

    public List<Image> findByProjectId(long projectId);

    public Page<Image> findByProjectIdAndIsFinishedFalse(long projectId, Pageable pageable);

    public Page<Image> findByProjectIdAndIsFinishedTrue(long projectId, Pageable pageable);

    public Page<Image> findByProjectIdAndIsFinishedFalseAndIsTestTrue(long projectId, Pageable pageable);

    public Page<Image> findByProjectIdAndIsFinishedTrueAndIsTestTrue(long projectId, Pageable pageable);

    public List<Image> findByprojectIdAndIsTest(long projectId,boolean isTest);

    public List<Image> findByProjectIdAndIsFinished(long projectId,boolean isFinished);

    @Modifying
    @Transactional(rollbackOn = Exception.class)
    @Query("update Image i set i.isTest = ?2 where i.id =?1")
    public void updateIsTest(long id,boolean isTest);

    @Modifying
    @Transactional(rollbackOn = Exception.class)
    @Query("update Image i set i.isFinished = ?2 where i.id =?1")
    public void updateIsFinished(long id,boolean isFinished);
}

