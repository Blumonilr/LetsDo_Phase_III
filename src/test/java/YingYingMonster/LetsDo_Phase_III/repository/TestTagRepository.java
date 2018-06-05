package YingYingMonster.LetsDo_Phase_III.repository;

import YingYingMonster.LetsDo_Phase_III.entity.Tag;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestTagRepository {
    @Autowired
    TagRepository repository;

    @Test
    public void test_findByIds() {
        Tag tag1 = new Tag(1001, null, "xml");
        Tag tag2 = new Tag(1009, null, "xml");
        Tag tag3 = new Tag(1002, null, "xml");
        Tag tag4 = new Tag(1002, null, "xml");

        repository.saveAll(Arrays.asList(tag1, tag2, tag3
                , tag4));
        repository.flush();

        List<Long> list = new ArrayList<Long>();
        list.add((long) 1001);
        list.add((long) 1002);

        List<Tag> res = repository.findByImageIds(list);
        assertEquals(3, res.size());
    }
}
