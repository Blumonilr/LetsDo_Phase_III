package YingYingMonster.LetsDo_Phase_III.serviceImpl;

import YingYingMonster.LetsDo_Phase_III.entity.Image;
import YingYingMonster.LetsDo_Phase_III.entity.Tag;
import YingYingMonster.LetsDo_Phase_III.entity.TestProject;
import YingYingMonster.LetsDo_Phase_III.repository.ImageRepository;
import YingYingMonster.LetsDo_Phase_III.repository.TagRepository;
import YingYingMonster.LetsDo_Phase_III.repository.TestProjectRepository;
import YingYingMonster.LetsDo_Phase_III.service.TestProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TestProjectServiceImpl implements TestProjectService {

    @Autowired
    TestProjectRepository testProjectRepository;

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    TagRepository tagRepository;

    @Override
    /**
     * 查看下一页未完成的测试集图片
     */
    public List<Image> getAPageOfImages(long testProjectId) {
        return imageRepository.findByProjectIdAndIsFinishedFalseAndIsTestTrue(testProjectId,
                PageRequest.of(0, 5)).stream().collect(Collectors.toList());
    }

    @Override
    public void uploadAnswer(Tag tag) {
        long imageId = tag.getImageId();
        imageRepository.updateIsFinished(imageId, true);
        tagRepository.saveAndFlush(tag);
    }

    @Override
    public TestProject getTestProjectByInviteCode(String inviteCode) {
        return testProjectRepository.findByInviteCode(inviteCode);
    }

    @Override
    public List<Tag> viewAnswer(long testProjectId, int page,int pageSize) {
        return tagRepository.findByProjectId(testProjectId, PageRequest.of(page, pageSize));
    }

    @Override
    public String viewTagRequirement(long testProjectId) {
        return testProjectRepository.findById(testProjectId).get().getProject().getTagRequirement();
    }

    @Override
    public List<String> getTextLabel(long testProjectId) {
        return null;
    }

    @Override
    public long getProjectPublisherId(long testProjectId) {
        return testProjectRepository.findById(testProjectId).get().getProject().getPublisherId();
    }

    @Override
    public long getTrueProjectId(long testProjectId) {
        return testProjectRepository.findById(testProjectId).get().getProject().getId();
    }
}
