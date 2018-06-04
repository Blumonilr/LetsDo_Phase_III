package YingYingMonster.LetsDo_Phase_III.repository;

import YingYingMonster.LetsDo_Phase_III.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
