package YingYingMonster.LetsDo_Phase_III.serviceImpl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestPublisherServiceImpl {
    @Autowired
    PublisherServiceImpl publisherService;

    /**
     * test 6 times
     * 1625599 * 1 time,
     * 10000000+ * 5 times
     * you can calculate the possibility yourself :)
     */
    @Test public void test_uuid(){
        String uuid, old_uuid;
//        uuid = old_uuid = publisherService.generateUUID();
//        for (int i = 0; i < 10000000; i++) {
//            uuid = publisherService.generateUUID();
//            if (uuid.equals(old_uuid)) {
//                System.out.println("duplicate at " + i + " times");
//                break;
//            } else {
//                old_uuid = uuid;
//            }
//        }
    }
}
