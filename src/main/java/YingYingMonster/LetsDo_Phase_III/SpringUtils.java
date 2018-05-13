package YingYingMonster.LetsDo_Phase_III;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringUtils implements ApplicationContextAware {

	private static ApplicationContext ac;
	
	@Override
	public void setApplicationContext(ApplicationContext arg0) throws BeansException {
		// TODO Auto-generated method stub
		if(ac==null)
			ac=arg0;
	}

	//获取applicationContext    
    public static ApplicationContext getApplicationContext() {    
        return ac;    
    }
    
}
