package YingYingMonster.LetsDo_Phase_III.service;

import YingYingMonster.LetsDo_Phase_III.entity.Image;
import YingYingMonster.LetsDo_Phase_III.entity.Tag;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {
    public int saveImages(MultipartFile multipartFile, long projectId, boolean isTest);

    public List<Image> findProjectAllImage(long projectId);

    public Tag calculateResult(long imageId);
}
