package YingYingMonster.LetsDo_Phase_III.service;

import YingYingMonster.LetsDo_Phase_III.entity.Tag;

import java.util.List;

public interface TagService {
	public List<Tag> getProjectTags(long projectId);
	public List<Tag> getImageTags(long imageId);
	public double calculateAccuracy(long tagId);
}
