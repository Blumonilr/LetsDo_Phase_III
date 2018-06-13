package YingYingMonster.LetsDo_Phase_III.serviceImpl;

import YingYingMonster.LetsDo_Phase_III.entity.Image;
import YingYingMonster.LetsDo_Phase_III.entity.Project;
import YingYingMonster.LetsDo_Phase_III.entity.Tag;
import YingYingMonster.LetsDo_Phase_III.entity.TestProject;
import YingYingMonster.LetsDo_Phase_III.repository.ImageRepository;
import YingYingMonster.LetsDo_Phase_III.repository.ProjectRepository;
import YingYingMonster.LetsDo_Phase_III.repository.TagRepository;
import YingYingMonster.LetsDo_Phase_III.repository.TestProjectRepository;
import YingYingMonster.LetsDo_Phase_III.service.ImageService;
import YingYingMonster.LetsDo_Phase_III.service.TestProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class TestProjectServiceImpl implements TestProjectService {

    @Autowired
    TestProjectRepository testProjectRepository;

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    TagRepository tagRepository;

    @Autowired
    ImageService imageService;

    @Autowired
    ProjectRepository projectRepository;

    @Override
    public TestProject addTestProject(long projectId, MultipartFile multipartFile) {
        TestProject testProject = new TestProject();
        Project project = projectRepository.findById(projectId);
        testProject.setMarkMode(project.getType());

        int picNum = imageService.saveImages(multipartFile, projectId,true);
        testProject.setPicNum(picNum);
        testProject.setInviteCode(generateUUID());
        testProject = testProjectRepository.saveAndFlush(testProject);
        projectRepository.updateTestProject(projectId, testProject);

        return testProject;
    }

    private String generateUUID(){
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            int index = (int) (Math.random() * 4);
            sb.append(uuid.substring(i, i + 4).charAt(index));
        }

        return sb.toString();
    }

    @Override
    /**
     * 查看下一页未完成的测试集图片
     */
    public List<Image> getAPageOfImages(int pageId, long testProjectId) {
        return imageRepository.findByProjectIdAndIsFinishedFalseAndIsTestTrue(testProjectId,
                PageRequest.of(pageId, 5)).stream().collect(Collectors.toList());
    }

    @Override
    public Tag uploadAnswer(long workerId,Tag tag) {
        long imageId = tag.getImageId();
        imageRepository.updateIsFinished(imageId, true);
        tag.setResult(true);
        return tagRepository.saveAndFlush(tag);
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
