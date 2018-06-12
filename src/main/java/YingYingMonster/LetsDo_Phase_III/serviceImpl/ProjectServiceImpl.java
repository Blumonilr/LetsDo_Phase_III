package YingYingMonster.LetsDo_Phase_III.serviceImpl;

import YingYingMonster.LetsDo_Phase_III.entity.Image;
import YingYingMonster.LetsDo_Phase_III.repository.ImageRepository;
import YingYingMonster.LetsDo_Phase_III.repository.ProjectRepository;
import YingYingMonster.LetsDo_Phase_III.service.ProjectService;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.io.*;

@Component
public class ProjectServiceImpl implements ProjectService  {

    @Autowired
    ImageRepository imageRepository;

    @Override
    public byte[] getProjectOverview(long projectId) throws IOException {
        byte[] data = null;
        String path = System.getProperty("user.dir")+"/projectOverview/pj" + projectId + ".jpg";
        File file = new File(path);
        if (file.exists()){
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(Thumbnails.of(file).scale(1.0).asBufferedImage(), "jpg", byteArrayOutputStream);
            data = byteArrayOutputStream.toByteArray();
        }
        return data;
    }
}
