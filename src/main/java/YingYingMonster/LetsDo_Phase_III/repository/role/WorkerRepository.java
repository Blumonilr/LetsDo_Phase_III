package YingYingMonster.LetsDo_Phase_III.repository.role;

import YingYingMonster.LetsDo_Phase_III.entity.role.Worker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WorkerRepository extends JpaRepository<Worker,Long> {

    @Query("select u from Worker u where u.name like %?1%")
    public List<Worker> findByNameLike(String key);

}
