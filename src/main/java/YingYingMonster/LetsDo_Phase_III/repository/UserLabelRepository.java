package YingYingMonster.LetsDo_Phase_III.repository;

import YingYingMonster.LetsDo_Phase_III.entity.UserLabel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserLabelRepository extends JpaRepository<UserLabel,Long> {
    public List<UserLabel> findAll();

    public List<UserLabel> findByUserId(long userId);

    @Query("update UserLabel ul set ul.accuracy = ?2 where id =?1")
    public void updateAccuracy(long id,double accuracy);

    @Query("update UserLabel ul set ul.efficiency = ?2 where id =?1")
    public void updateEfficiency(long id,double efficiency);
}
