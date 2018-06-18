package YingYingMonster.LetsDo_Phase_III.serviceImpl;

import YingYingMonster.LetsDo_Phase_III.entity.Image;
import YingYingMonster.LetsDo_Phase_III.entity.MarkMode;
import YingYingMonster.LetsDo_Phase_III.entity.Project;
import YingYingMonster.LetsDo_Phase_III.entity.Tag;
import YingYingMonster.LetsDo_Phase_III.model.ProjectState;
import YingYingMonster.LetsDo_Phase_III.repository.ImageRepository;
import YingYingMonster.LetsDo_Phase_III.repository.ProjectRepository;
import YingYingMonster.LetsDo_Phase_III.repository.TagRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestPublisherServiceImpl {
    @Autowired
    PublisherServiceImpl publisherService;
    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    ImageRepository imageRepository;
    @Autowired
    TagRepository tagRepository;
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

    @Test
    public void testDownload() throws Exception {
        Project pj=new Project(MarkMode.SQUARE,10,"test",5,2,"2018-7-19",null,0,0.7,100,null);
        long projectId=projectRepository.saveAndFlush(pj).getId();
        pj.setProjectState(ProjectState.closed);
        projectRepository.saveAndFlush(pj);
        System.out.println(pj.getProjectState());
        FileInputStream fis=new FileInputStream(new File("C:/Users/TF/Desktop/文件/QQ图片20180503142107.jpg"));
        ByteArrayOutputStream bos=new ByteArrayOutputStream(1024);
        byte[] b = new byte[1024*1024];
        int len = -1;
        while((len = fis.read(b)) != -1) {
            bos.write(b, 0, len);
        }
        Image i1=new Image(projectId,bos.toByteArray(),2,5,2,true,false);
        long imageId=imageRepository.saveAndFlush(i1).getId();
        Tag t1=new Tag(8,imageId,projectId,bos.toByteArray(),"<!--   Copyright w3school.com.cn  -->\n" +
                "<note>\n" +
                "<to>George</to>\n" +
                "<from>John</from>\n" +
                "<heading>Reminder</heading>\n" +
                "<body>Don't forget the meeting!</body>\n" +
                "</note>",true);
        tagRepository.saveAndFlush(t1);
        publisherService.downloadTags(projectId);
    }
}
