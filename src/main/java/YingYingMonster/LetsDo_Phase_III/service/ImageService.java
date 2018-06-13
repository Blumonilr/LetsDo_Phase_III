package YingYingMonster.LetsDo_Phase_III.service;

import YingYingMonster.LetsDo_Phase_III.entity.Tag;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    public int saveImages(MultipartFile multipartFile, long projectId, boolean isTest);

    public Tag calculateResult(long imageId);
}
