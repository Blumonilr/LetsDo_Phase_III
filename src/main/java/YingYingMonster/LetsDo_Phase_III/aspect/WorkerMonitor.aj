package YingYingMonster.LetsDo_Phase_III.aspect;


import YingYingMonster.LetsDo_Phase_III.entity.Tag;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public aspect WorkerMonitor {

    /**
     * 项目测试集答案制作切点
     * @param tag
     */
    @Pointcut(value = "execution(* YingYingMonster.LetsDo_Phase_III.service." +
            "TestProjectService.uploadAnswer(..)) &&args(tag) ", argNames = "tag")
    public void innerTestPoint(Tag tag){}
}
