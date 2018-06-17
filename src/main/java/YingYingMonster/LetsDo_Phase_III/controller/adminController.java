package YingYingMonster.LetsDo_Phase_III.controller;

import YingYingMonster.LetsDo_Phase_III.entity.json.SystemInfo;
import YingYingMonster.LetsDo_Phase_III.entity.role.Worker;
import YingYingMonster.LetsDo_Phase_III.service.UserService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import YingYingMonster.LetsDo_Phase_III.service.AdminService;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class adminController {
    @Autowired
    AdminService adminService;
    @Autowired
    UserService userService;

    //管理员界面
    @GetMapping("/")
    public String publishPage(){
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        String userId=(String)session.getAttribute("userId");
        if(userId!=null){
            //已登录
            return "admin/monitor";
        } else {
            //如果未登录返回登录页面
            return "redirect:/user/login";
        }
    }

    //系统信息
    @PostMapping("/systemDetail")
    @ResponseBody
    public String systemInfo(){
        Gson gson=new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        SystemInfo systemInfo=new SystemInfo(adminService);
        List<Worker> list=adminService.workerAccuracyRank();
        int length=list.size()>=100?100:list.size();
        systemInfo.setWorkerTop100(new String[length][6]);
        toArray(list,systemInfo.getWorkerTop100(),length);
        return gson.toJson(systemInfo);
    }

    private void toArray(List<Worker> list,String[][] top100,int length){
        for(int i=0;i<length;i++){
            Worker worker=list.get(i);
            top100[i][0]=Integer.toString(i);
            top100[i][1]=worker.getName();
            top100[i][2]=worker.getStringAbilities();
            top100[i][3]=Double.toString(worker.getAccuracy());
            top100[i][4]=Integer.toString(adminService.workInProjectNum(worker.getId()));
            top100[i][5]=Integer.toString(adminService.workInProjectNum(worker.getId()));
        }
    }
}
