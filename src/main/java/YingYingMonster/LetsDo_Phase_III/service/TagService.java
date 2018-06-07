package YingYingMonster.LetsDo_Phase_III.service;

import YingYingMonster.LetsDo_Phase_III.model.ObjectTag;

import java.util.List;

public interface TagService {
	public List<String> getProjectTags(String projectId,String publisherId);
	public boolean addTag(ObjectTag tag);
	public boolean modifyTag(ObjectTag tag);
}
