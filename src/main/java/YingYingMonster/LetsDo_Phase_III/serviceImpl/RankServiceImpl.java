package YingYingMonster.LetsDo_Phase_III.serviceImpl;

import YingYingMonster.LetsDo_Phase_III.entity.role.User;
import YingYingMonster.LetsDo_Phase_III.service.RankService;
import com.fasterxml.classmate.types.ResolvedRecursiveType;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class RankServiceImpl implements RankService {

	
	@Override
	public List<User> rankByExp() throws FileNotFoundException, ClassNotFoundException, IOException {
		// TODO Auto-generated method stub
//		return usrDao.findUsers(null).stream().filter(x->x instanceof Worker)
//				.map(x->(Worker)x).sorted(new Comparator<Worker>(){
//
//			@Override
//			public int compare(Worker o1, Worker o2) {
//				// TODO Auto-generated method stub
//				if(o1.getLevel()<o2.getLevel()
//						||(o1.getLevel()==o2.getLevel()&&o1.getExp()<o2.getExp()))
//					return 1;
//
//				if(o1.getLevel()==o2.getLevel()&&o1.getExp()==o2.getExp())
//					return 0;
//
//				return -1;
//			}
//
//		}).collect(Collectors.toList());
		return null;
	}

	@Override
	public List<User> rankByAccuracy() throws FileNotFoundException, ClassNotFoundException, IOException {
		// TODO Auto-generated method stub
//		return usrDao.findUsers(null).stream().filter(x->x instanceof Worker)
//				.map(x->(Worker)x).sorted(new Comparator<Worker>(){
//
//			@Override
//			public int compare(Worker o1, Worker o2) {
//				// TODO Auto-generated method stub
//				double ac1,ac2;
//				if(o1.getTagNum()==0)
//					ac1=0.0;
//				else
//					ac1=(o1.getPassedTagNum()+0.0)/o1.getTagNum();
//
//				if(o2.getTagNum()==0)
//					ac2=0.0;
//				else
//					ac2=(o2.getPassedTagNum()+0.0)/o2.getTagNum();
//
//				if(ac1<ac2)
//					return 1;
//
//				if(ac1==ac2)
//					return 0;
//
//				return -1;
//			}
//
//		}).collect(Collectors.toList());
		return null;
	}

}
