package YingYingMonster.LetsDo_Phase_III.RepositoryTest;

import YingYingMonster.LetsDo_Phase_III.Initializer;
import YingYingMonster.LetsDo_Phase_III.entity.TextNode;
import YingYingMonster.LetsDo_Phase_III.repository.TextNodeRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestTextNodeRepository {

    @Autowired
    TextNodeRepository tr;

    @Test
    public void test1(){
        TextNode tn1=new TextNode("动物",null,false,null);
        tr.save(tn1);
        List<String> m1=new ArrayList<>();
        m1.add("年龄:老年_壮年_幼年");
        m1.add("性别:公_母");
        TextNode tn2=new TextNode("牛","动物",true,m1);
        tr.save(tn2);
    }

    @Test
    public void test2(){
        TextNode t1=tr.findByName("牛");
        System.out.println(t1.getAttributions().get(0));
    }

    @Test
    public void test3(){
        List<TextNode> list=tr.findFathers();
        for(TextNode t:list){
            System.out.println(t.getName());
        }
    }

    @Test
    public void testover(){
        tr.deleteAll();
    }
}
