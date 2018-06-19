package YingYingMonster.LetsDo_Phase_III.controller;

import YingYingMonster.LetsDo_Phase_III.entity.json.ExtraProjectInfo;
import YingYingMonster.LetsDo_Phase_III.entity.json.SystemInfo;
import YingYingMonster.LetsDo_Phase_III.entity.role.Publisher;
import YingYingMonster.LetsDo_Phase_III.entity.role.Worker;
import YingYingMonster.LetsDo_Phase_III.service.UserService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import YingYingMonster.LetsDo_Phase_III.service.AdminService;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;

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
    public String systemInfo() throws ParseException {
        Gson gson=new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        SystemInfo systemInfo=new SystemInfo(adminService);
        return gson.toJson(systemInfo);
    }

    //系统信息
    @PostMapping("/extraSystemDetail")
    @ResponseBody
    public String extraInfo() throws ParseException {
        Gson gson=new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        ExtraProjectInfo extraProjectInfo=new ExtraProjectInfo(adminService);
        return gson.toJson(extraProjectInfo);
    }

    @PostMapping("/workerDetail")
    @ResponseBody
    public String workerDetail(@RequestParam("workerId")String workerId) throws ParseException {
        Gson gson=new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        Worker worker=adminService.finByWorkerId(Long.parseLong(workerId));
        return gson.toJson(worker);
    }

    @PostMapping("/publisherDetail")
    @ResponseBody
    public String publisherDetail(@RequestParam("publisherId")String publisherId) throws ParseException {
        Gson gson=new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        Publisher publisher =adminService.findByPublisherId(Long.parseLong(publisherId));
        return gson.toJson(publisher);
    }

}
