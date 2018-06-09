package YingYingMonster.LetsDo_Phase_III.RepositoryTest;

import YingYingMonster.LetsDo_Phase_III.entity.Image;
import YingYingMonster.LetsDo_Phase_III.repository.ImageRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestImageRepository {

    @Autowired
    ImageRepository ir;

    @Test
    public void test1(){
        Image i1=new Image(1,null,2,5,0,false,false);
        Image i2=new Image(1,null,2,5,0,false,true);
        Image i3=new Image(1,null,2,5,0,false,true);
        Image i4=new Image(2,null,2,5,0,false,false);
        long id=ir.save(i1).getId();
        ir.save(i2);
        ir.save(i3);
        ir.save(i4);
        ir.flush();
        System.out.println(ir.findById(id));
        ir.updateIsFinished(id,true);
        ir.updateIsTest(id,true);
        System.out.println(ir.findById(id));
    }

    @Test
    public void test2(){
        System.out.println(ir.findByProjectId(1));
        System.out.println(ir.findByProjectIdAndIsFinished(1,false));
        System.out.println(ir.findByprojectIdAndIsTest(1,true));
    }

    @Test
    public void testover(){
        ir.deleteAll();;
    }
}
