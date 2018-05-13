package YingYingMonster.LetsDo_Phase_III.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import YingYingMonster.LetsDo_Phase_III.service.AdminService;

import java.io.IOException;

@Controller
@RequestMapping("/admin")
public class adminController {
    @Autowired
    AdminService adminService;

    //管理员界面
    @GetMapping("/")
    public String publishPage(){
        return "admin/monitor";
    }

    //系统信息
    @PostMapping("/systemDetail")
    @ResponseBody
    public String systemInfo(){
        int userNum= 0;
        int projectNum= 0;
        try {
            userNum = adminService.viewUserNum();
            projectNum = adminService.viewProjectNum();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "{\"userNum\":\""+Integer.toString(userNum)+"\","+"\"projectNum\":\""+Integer.toString(projectNum)+"\"}";
    }
}
