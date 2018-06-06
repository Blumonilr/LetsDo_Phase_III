package YingYingMonster.LetsDo_Phase_III.dao;

import YingYingMonster.LetsDo_Phase_III.model.ObjectTag;

import java.util.List;

public interface TagDAO {
    public List<String> getProjectTags(String projectId, String publisherId);
    public boolean addTag(ObjectTag tag);
    public List<ObjectTag> findTagsByObject(String objectName);
    public boolean deleteTag(String objectName);
    public boolean modifyTag(ObjectTag tag);
}
