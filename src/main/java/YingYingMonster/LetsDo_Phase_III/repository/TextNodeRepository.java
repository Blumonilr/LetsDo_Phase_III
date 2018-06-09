package YingYingMonster.LetsDo_Phase_III.repository;

import YingYingMonster.LetsDo_Phase_III.entity.TextNode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface TextNodeRepository extends JpaRepository<TextNode,Long> {

    public TextNode findByName(String name);

    public List<TextNode> findByFather(TextNode father);

    @Modifying
    @Transactional(rollbackOn = Exception.class)
    @Query("update TextNode t set tisLeaf = ?2 where t.name =?1")
    public void updateIsLeaf(String name,boolean isLeaf);
}
