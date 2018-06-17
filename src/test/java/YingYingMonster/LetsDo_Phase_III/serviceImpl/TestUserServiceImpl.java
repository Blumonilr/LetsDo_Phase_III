package YingYingMonster.LetsDo_Phase_III.serviceImpl;

import YingYingMonster.LetsDo_Phase_III.entity.role.Administrator;
import YingYingMonster.LetsDo_Phase_III.entity.role.Publisher;
import YingYingMonster.LetsDo_Phase_III.entity.role.User;
import YingYingMonster.LetsDo_Phase_III.entity.role.Worker;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestUserServiceImpl {
    @Autowired
    UserServiceImpl userService;

    @Test public void test_add(){
        Worker worker = new Worker("wk", "pw", "email", "intro",
                1000, 0, 0, 0, 0);
        Publisher publisher = new Publisher("pub", "pw", "email",
                "intro", 200000);
        Administrator administrator = new Administrator("管理员", "admin", "email",
                "intro", 0);

        long id1 = userService.register(worker).getId();
        long id2 = userService.register(publisher).getId();
        long id3 = userService.register(administrator).getId();

        Worker worker1 = (Worker) userService.getUser(id1);
        Publisher publisher1 = ((Publisher) userService.getUser(id2));
        Administrator administrator1 = (Administrator) userService.getUser(id3);

        assertEquals("wk", worker1.getName());
        assertEquals("pub", publisher1.getName());
        assertEquals("管理员", administrator1.getName());
    }

    @Test
    public void test_login() {
        User user = userService.login(14, "pw");
        assertEquals(true, user instanceof Worker );

        User user1 = userService.login(770, "pw");
        assertNull(user1);
    }

    @Test
    public void test_modify() {
        Worker worker = ((Worker) userService.login(77, "pw"));
        worker.setName("pub's worker");
        worker = ((Worker) userService.modify(worker));
        assertEquals("pub's worker", worker.getName());
    }

    @Test
    public void test_findByName(){
        List<User> list = userService.findUsersByName("");
        assertEquals(2, list.size());

    }

    @Test
    public void test_findWorker(){
        Worker wk=new Worker("wk", "pw", "email", "intro",
                1000, 0, 0, 0, 0);
        Publisher pub=new Publisher("wk", "pw", "email", "intro",
                1000);
        userService.register(wk);
        userService.register(pub);
        assertEquals(1,userService.findPublisherByNameLike("wk").size());
    }

    @Test
    public void test_workerActive(){
       assertEquals(1,userService.getUser(14).getLogEvent().getConstantDays());
    }
}
