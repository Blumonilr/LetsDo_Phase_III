package YingYingMonster.LetsDo_Phase_III.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import YingYingMonster.LetsDo_Phase_III.model.ObjectTag;
import YingYingMonster.LetsDo_Phase_III.service.TagService;
import org.springframework.stereotype.Component;

@Component
public class TagServiceImpl implements TagService {

	@Override
	public List<String> getProjectTags(String projectId, String publisherId) {
		// TODO 自动生成的方法存根
		List<String> list=new ArrayList<String>();
		list.add("名称_牛_猪_羊_狗_猫");
		list.add("年龄_幼年_壮年_老年");
		list.add("肤色_黑_白_灰_黄_棕_灰_黑白_黑灰_灰白");
		list.add("性别_公_母");
		return list;
	}

	@Override
	public boolean modifyTag(ObjectTag tag) {
		return false;
	}

	@Override
	public boolean addTag(ObjectTag tag) {
		return false;
	}

}
