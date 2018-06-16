package YingYingMonster.LetsDo_Phase_III.repository.role;

import YingYingMonster.LetsDo_Phase_III.entity.role.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PublisherRepository extends JpaRepository<Publisher,Long> {

    @Query("select u from Publisher u where u.name like %?1%")
    public List<Publisher> findByNameLike(String name);
}
