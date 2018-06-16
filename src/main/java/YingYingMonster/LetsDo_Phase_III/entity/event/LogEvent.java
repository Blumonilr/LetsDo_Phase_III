package YingYingMonster.LetsDo_Phase_III.entity.event;

import YingYingMonster.LetsDo_Phase_III.entity.role.User;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

@Entity
@Table(name="log_events")
public class LogEvent {

    @Id @GeneratedValue
    private long id;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private int constantDays;
    private Date checkDate;

    public LogEvent(User user, int constantDays, Date checkDate) {
        this.user = user;
        this.constantDays = constantDays;
        this.checkDate = checkDate;
    }

    public LogEvent() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getConstantDays() {
        return constantDays;
    }

    public void checkLogin() {
        //获取今天凌晨时间
        Calendar today = Calendar.getInstance();
        today.set(today.get(Calendar.YEAR),today.get(Calendar.MONTH),today.get(Calendar.DATE),0,0,0);
        //获取昨天凌晨时间
        Calendar yesterday = Calendar.getInstance();
        yesterday.set(yesterday.get(Calendar.YEAR),yesterday.get(Calendar.MONTH),yesterday.get(Calendar.DATE)-1,0,0,0);

        if (checkDate==null){
            setCheckDate(Calendar.getInstance().getTime());
            constantDays=1;
        }
        //判断用户签到时间是否是在今天凌晨之前
        else if(checkDate.before(today.getTime())){
            //如果上次签到是昨天凌晨之前，说明没有连续签到
            if(checkDate.before(yesterday.getTime())){
                //将签到天数归为1
                constantDays=1;
            }
            else {
                constantDays++;
                setCheckDate(Calendar.getInstance().getTime());
            }
        }
    }

    public Date getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(Date checkDate) {
        checkDate = checkDate;
    }

}
