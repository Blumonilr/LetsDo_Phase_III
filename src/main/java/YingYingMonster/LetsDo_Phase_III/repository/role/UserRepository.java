package YingYingMonster.LetsDo_Phase_III.repository.role;

import YingYingMonster.LetsDo_Phase_III.entity.role.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    public User findById(long id);

    public User findByIdAndPw(long id,String pw);

    @Query("select u from User u where u.name like %?1%")
    public List<User> findByNameLike(String name);

    @Query("select u from User u where u.email like %?1%")
    public List<User> findByEmailLike(String email);

    @Query("select u from User u where u.intro like %?1%")
    public List<User> findByIntroLike(String intro);

    @Query("select u from User u where (u.intro like %?1% or u.name like %?1% or u.email like %?1%)")
    public List<User> findByStringAttr(String key);

    @Query("update User u set u.money =u.money +?2 where u.id =?1")
    public void changeMoney(long id, long money);

    public void deleteById(long id);

//    @Query("delete from User u where u.id in ?1")
    public void deleteByIdIn(List<Long> ids);

    public void deleteByName(String name);

    public void deleteByNameIn(List<String> names);

    @Query(value = "select * from users where dtype='AD'", nativeQuery = true)
    public User getAnAdmin();
}
