package YingYingMonster.LetsDo_Phase_III.service;

import YingYingMonster.LetsDo_Phase_III.entity.Image;
import YingYingMonster.LetsDo_Phase_III.entity.Tag;

import java.util.List;

public interface TestProjectService {

    /**
     * 分页返回image
     * 一页5张图
     * @param testProjectId
     * @param page 页号
     * @return
     */
    public List<Image> getAPageOfImages(long testProjectId,int page);

    /**
     * 上传/修改答案
     * @param tag
     */
    public void uploadAnswer(Tag tag);

    /**
     * 分页查找项目已经做好的答案
     * @param testProjectId
     * @param page
     * @return
     */
    public List<Tag> viewAnswer(long testProjectId, int page);
}
