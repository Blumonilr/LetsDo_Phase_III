package YingYingMonster.LetsDo_Phase_III.repository;

import YingYingMonster.LetsDo_Phase_III.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {


    public User findByIdAndPw(long id,String pw);

    @Query("select u from User u where u.name like %?1%")
    public List<User> findByNameLike(String name);

    @Query("update User u set u.money =u.money +?2 where u.id =?1")
    public void changeMoney(long id, long money);
}
