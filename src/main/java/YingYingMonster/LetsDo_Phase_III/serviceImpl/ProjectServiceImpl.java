package YingYingMonster.LetsDo_Phase_III.serviceImpl;

import YingYingMonster.LetsDo_Phase_III.entity.Image;
import YingYingMonster.LetsDo_Phase_III.repository.ImageRepository;
import YingYingMonster.LetsDo_Phase_III.repository.ProjectRepository;
import YingYingMonster.LetsDo_Phase_III.service.ProjectService;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

public class ProjectServiceImpl implements ProjectService  {

    @Autowired
    ImageRepository imageRepository;

    @Override
    public String getProjectOverview(long projectId) throws IOException {
        String path = "static/images/home/pj" + projectId + ".jpg";
        File file = new File(path);
        if (!file.exists()){
            Image image = imageRepository.getOneByProjectId(projectId);
            Thumbnails.of(new ByteArrayInputStream(image.getPicture())).size(270, 210)
                    .outputFormat("jpg").toFile(path);

        }
        return path;
    }
}
