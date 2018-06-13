package YingYingMonster.LetsDo_Phase_III.repository;

import YingYingMonster.LetsDo_Phase_III.entity.Ability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AbilityRepository extends JpaRepository<Ability, Long> {

//    @Query("select a from Ability a where a.user=?1")
    public List<Ability> findByUser(long userId);

    public Ability findById(long id);

    public List<Ability> findByLabel(long labelId);
}
