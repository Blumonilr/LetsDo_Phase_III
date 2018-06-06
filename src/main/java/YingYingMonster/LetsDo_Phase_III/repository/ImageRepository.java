package YingYingMonster.LetsDo_Phase_III.repository;

import YingYingMonster.LetsDo_Phase_III.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image,Long> {
    public Image findById(long id);

    public List<Image> findByProjectId(long projectId);

    public List<Image> findByprojectIdAndIsTest(long projectId,boolean isTest);

    public List<Image> findByProjectIdAndIsFinished(long projectId,boolean isFinished);

    @Query("update Image i set i.isTest = ?2 where i.id =?1")
    public void updateIsTest(long id,boolean isTest);

    @Query("update Image i set i.isFinished = ?2 where i.id =?1")
    public void updateIsFinished(long id,boolean isFinished);
}

