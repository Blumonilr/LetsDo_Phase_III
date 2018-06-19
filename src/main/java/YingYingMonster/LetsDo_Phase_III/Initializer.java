package YingYingMonster.LetsDo_Phase_III;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import YingYingMonster.LetsDo_Phase_III.entity.Label;
import YingYingMonster.LetsDo_Phase_III.entity.TextNode;
import YingYingMonster.LetsDo_Phase_III.entity.role.Administrator;
import YingYingMonster.LetsDo_Phase_III.entity.role.User;
import YingYingMonster.LetsDo_Phase_III.repository.LabelRepository;
import YingYingMonster.LetsDo_Phase_III.repository.TextNodeRepository;
import YingYingMonster.LetsDo_Phase_III.repository.role.UserRepository;
import YingYingMonster.LetsDo_Phase_III.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

public class Initializer {

	private ApplicationContext context=SpringUtils.getApplicationContext();
	private String root=context.getBean(String.class);
//	private MockDB db=context.getBean(MockDB.class);
//	private CSVHandler handler=context.getBean(CSVHandler.class);
	private TextNodeRepository tr=context.getBean(TextNodeRepository.class);
	private LabelRepository lr=context.getBean(LabelRepository.class);

	private final Logger logger = LoggerFactory.getLogger(getClass());

	public void initialize(){
		System.out.println(System.getProperty("user.dir"));
		System.out.println("initializing...");
		System.out.println("===============");
		UserRepository userRepository = context.getBean(UserRepository.class);
		UserService userService = context.getBean(UserService.class);

		Administrator administrator= (Administrator) userRepository.getAnAdmin();
		if (administrator == null) {
			administrator = new Administrator("admin", "admin", "email", "intro", 0);
			administrator = (Administrator) userService.register(administrator);
			logger.info("register an administrater, id = " + administrator.getId() + " , pw = 'admin' ");
		}


		initTextNodeTree(new File(System.getProperty("user.dir").replaceAll("\\\\", "/")+"/TextNodeTree"));
		
	}

	public void initTextNodeTree(File file){
		try {
			InputStreamReader insReader = new InputStreamReader(
					new FileInputStream(file), "UTF-8");
			BufferedReader br=new BufferedReader(insReader);
			String line=null;
			String currentFather=null;
			while((line=br.readLine())!=null){
				if(line.startsWith("		")){
					continue;
				}
				else if(line.startsWith("    ")) {
					List<String> attri = new ArrayList<>();
					String attribution = null;
					while ((attribution = br.readLine())!=null&&attribution.startsWith("        ")) {
						attri.add(attribution.replaceAll(" ",""));
					}
					TextNode son = new TextNode(line.replace(" ", ""), currentFather, true, attri);
					Label label=new Label(line.replace(" ", ""));
					lr.save(label);
					tr.save(son);
				}
				else{
					currentFather=line;
					TextNode father=new TextNode(currentFather,null,false,null);
					tr.save(father);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
