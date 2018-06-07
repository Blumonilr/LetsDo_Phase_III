package YingYingMonster.LetsDo_Phase_III.service;

import YingYingMonster.LetsDo_Phase_III.entity.Image;

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
}
