package YingYingMonster.LetsDo_Phase_III;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.context.request.RequestContextListener;

@SpringBootApplication
@EnableScheduling
public class LetsDoPhaseIIApplication {

	//add sth.
	public static void main(String[] args) {
		ConfigurableApplicationContext cac=SpringApplication.run(LetsDoPhaseIIApplication.class, args);
		System.out.println();
		Initializer init=new Initializer();
		init.initialize();
	}
}
