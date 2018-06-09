package YingYingMonster.LetsDo_Phase_III.serviceImpl;

import YingYingMonster.LetsDo_Phase_III.entity.Image;
import YingYingMonster.LetsDo_Phase_III.entity.Tag;
import YingYingMonster.LetsDo_Phase_III.repository.ImageRepository;
import YingYingMonster.LetsDo_Phase_III.repository.TagRepository;
import YingYingMonster.LetsDo_Phase_III.repository.TestProjectRepository;
import YingYingMonster.LetsDo_Phase_III.service.TestProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TestProjectServiceImpl implements TestProjectService {

    @Autowired
    TestProjectRepository testProjectRepository;

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    TagRepository tagRepository;

    @Override

    public List<Image> getAPageOfImages(long testProjectId, int page) {
        return imageRepository.findByProjectId(testProjectId, new PageRequest(page, 5));
    }

    @Override
    public void uploadAnswer(Tag tag) {

    }

    @Override
    public List<Tag> viewAnswer(long testProjectId, int page) {
        return null;
    }
}
