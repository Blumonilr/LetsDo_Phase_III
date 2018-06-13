package YingYingMonster.LetsDo_Phase_III.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import YingYingMonster.LetsDo_Phase_III.entity.Tag;
import YingYingMonster.LetsDo_Phase_III.model.ObjectTag;
import YingYingMonster.LetsDo_Phase_III.repository.TagRepository;
import YingYingMonster.LetsDo_Phase_III.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

@Component
public class TagServiceImpl implements TagService {

	@Autowired
	TagRepository tgr;

	@Override
	public List<Tag> getProjectTags(long projectId) {
		return tgr.findByProjectId(projectId,PageRequest.of(0,5));
	}

	@Override
	public List<Tag> getImageTags(long imageId) {
		return tgr.findByImageId(imageId);
	}

	@Override
	public double calculateAccuracy(long tagId) {
		return 0;
	}
}
