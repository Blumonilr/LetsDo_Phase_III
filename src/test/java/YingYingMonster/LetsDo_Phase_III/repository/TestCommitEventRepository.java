package YingYingMonster.LetsDo_Phase_III.repository;

import YingYingMonster.LetsDo_Phase_III.entity.event.CommitEvent;
import YingYingMonster.LetsDo_Phase_III.model.CommitResult;
import YingYingMonster.LetsDo_Phase_III.repository.event.CommitEventRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestCommitEventRepository {
    @Autowired
    CommitEventRepository repository;

    @Test
    public void test_add(){
        CommitEvent commitEvent1 = new CommitEvent(161250103, 12,
                123, 4, new Date());
        CommitEvent commitEvent2 = new CommitEvent(161250050, 12,
                124, 4, new Date());
        CommitEvent commitEvent3 = new CommitEvent(161250103, 12,
                125, 5, new Date(118, 5, 5));


//        commitEvent1.setCommitResult(CommitResult.passed);
//        commitEvent2.setCommitResult(CommitResult.passed);
//        commitEvent3.setCommitResult(CommitResult.rejected);

        repository.saveAndFlush(commitEvent1);
        repository.saveAndFlush(commitEvent2);
        repository.saveAndFlush(commitEvent3);

    }

    @Test
    public void test_findByCommitResult(){
        List<CommitEvent> list0 = repository.findByCommitMsg(CommitEvent.EVALUATING);
        List<CommitEvent> list1 = repository.findByCommitMsg(CommitEvent.PASSED);
        List<CommitEvent> list2 = repository.findByCommitMsg(null);

        assertEquals(1, list0.size());
        assertEquals(1, list1.size());
        assertEquals(1, list2.size());
    }

    @Test
    public void test_findByDate(){
        Date start = new Date(System.currentTimeMillis()-86400000*2);  //两天前
        Date end = new Date();

        List<CommitEvent> list0 = repository.findByCommitTimeBetween(start, end);
//        List<CommitEvent> list1 = repository.findByCommitTimeBetween(start, null);
        List<CommitEvent> list2 = repository.findByCommitTimeBetween(end, end);
//        List<CommitEvent> list3 = repository.findByCommitTimeBetween(null, null);

        System.out.println(list0.size());
//        System.out.println(list1.size());
        System.out.println(list2.size());
//        System.out.println(list3.size());
    }
}
