package YingYingMonster.LetsDo_Phase_III.service;

import YingYingMonster.LetsDo_Phase_III.entity.Image;
import YingYingMonster.LetsDo_Phase_III.entity.Tag;
import YingYingMonster.LetsDo_Phase_III.entity.TestProject;
import YingYingMonster.LetsDo_Phase_III.entity.TextNode;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface TestProjectService {

    public TestProject addTestProject(long projectId, MultipartFile multipartFile);

    /**
     * 分页返回没做过的image
     * 一页5张图
     *
     * @param testProjectId
     * @return
     */
    public List<Image> getAPageOfImages(int pageId, long testProjectId);

    public List<Image> getAllTestImages(int testProjectId);

    /**
     * 上传/修改答案
     * @param workerId
     * @param tag
     */
    public Tag uploadAnswer(long workerId,Tag tag);

    public TestProject getTestProjectByInviteCode(String inviteCode);

    public List<Tag> viewAnswers(long testProjectId);

    public String viewTagRequirement(long testProjectId);

    /**
     *
     * @param testProjectId
     * @return   每一个String的格式为   key_opt1_opt2_opt3_.._optn
     */
    public List<TextNode> getTextLabel(long testProjectId);

    public long getProjectPublisherId(long testProjectId);

    public long getTrueProjectId(long testProjectId);
}
