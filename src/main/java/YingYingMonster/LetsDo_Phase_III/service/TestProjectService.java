package YingYingMonster.LetsDo_Phase_III.service;

import YingYingMonster.LetsDo_Phase_III.entity.Image;
import YingYingMonster.LetsDo_Phase_III.entity.Tag;
import YingYingMonster.LetsDo_Phase_III.entity.TestProject;

import java.util.List;

public interface TestProjectService {

    /**
     * 分页返回没做过的image
     * 一页5张图
     * @param testProjectId
     * @return
     */
    public List<Image> getAPageOfImages(long testProjectId);

    /**
     * 上传/修改答案
     * @param workerId
     * @param tag
     */
    public Tag uploadAnswer(long workerId,Tag tag);

    public TestProject getTestProjectByInviteCode(String inviteCode);

    /**
     * 分页查找项目已经做好的答案
     * @param testProjectId
     * @param page
     * @param pageSize
     * @return
     */
    public List<Tag> viewAnswer(long testProjectId, int page,int pageSize);

    public String viewTagRequirement(long testProjectId);

    /**
     *
     * @param testProjectId
     * @return   每一个String的格式为   key_opt1_opt2_opt3_.._optn
     */
    public List<String> getTextLabel(long testProjectId);

    public long getProjectPublisherId(long testProjectId);

    public long getTrueProjectId(long testProjectId);
}
