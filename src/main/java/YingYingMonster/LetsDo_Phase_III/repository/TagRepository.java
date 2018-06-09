package YingYingMonster.LetsDo_Phase_III.repository;

import YingYingMonster.LetsDo_Phase_III.entity.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Long> {

    public List<Tag> findByImageId(long imageId);

    /**
     * 此方法可以用于获得一个project的所有tag
     * @param list
     * @return
     */
    @Query("select t from Tag t where t.imageId in ?1")
    public List<Tag>findByImageIds(List<Long>list);

    List<Tag> findByProjectId(long id, Pageable pageable);
}


