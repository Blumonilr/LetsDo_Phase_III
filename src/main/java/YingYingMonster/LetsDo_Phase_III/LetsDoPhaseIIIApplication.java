package YingYingMonster.LetsDo_Phase_III;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.context.request.RequestContextListener;

@EnableAspectJAutoProxy
@SpringBootApplication
@EnableScheduling
public class LetsDoPhaseIIIApplication {

	//add sth.
	public static void main(String[] args) {
		ConfigurableApplicationContext cac=SpringApplication.run(LetsDoPhaseIIIApplication.class, args);
		System.out.println();
		Initializer init=new Initializer();
		init.initialize();
	}
}
