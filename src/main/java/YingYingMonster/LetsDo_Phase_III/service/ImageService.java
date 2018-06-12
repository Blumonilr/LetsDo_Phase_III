package YingYingMonster.LetsDo_Phase_III.service;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    public int saveImages(MultipartFile multipartFile, long projectId, boolean isTest);
}
