package YingYingMonster.LetsDo_Phase_III.controller;

import YingYingMonster.LetsDo_Phase_III.entity.Project;
import YingYingMonster.LetsDo_Phase_III.entity.role.Publisher;
import YingYingMonster.LetsDo_Phase_III.entity.role.User;
import YingYingMonster.LetsDo_Phase_III.entity.role.Worker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import YingYingMonster.LetsDo_Phase_III.service.UserService;
import YingYingMonster.LetsDo_Phase_III.service.WorkerService;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private WorkerService wkservice;

    @GetMapping("/signUp")
//    @ApiOperation(value = "访问用户注册界面")
    public String visitRegisterPage(){
        return "user/signUp";
    }

    @GetMapping("/publisherSignUp")
    public String publisherRegisterPage(){
        return "user/publisherSignUp";
    }

    @GetMapping("/workerSignUp")
    public String workerRegisterPage(){
        return "user/workerSignUp";
    }

    @PostMapping("/publisherSignUp")
    @ResponseBody
    public String publisherRegister(@RequestParam("password")String password
            ,@RequestParam("nickName")String nickName
            ,@RequestParam("email")String email){
        Publisher publisher=new Publisher();
        publisher.setName(nickName);
        publisher.setPw(password);
        publisher.setEmail(email);
        publisher.setIntro("");
        publisher=(Publisher) userService.register(publisher);
        return Long.toString(publisher.getId());
    }

    @PostMapping("/workerSignUp")
    @ResponseBody
    public String workerRegister(@RequestParam("password")String password
            ,@RequestParam("nickName")String nickName
            ,@RequestParam("email")String email){
        Worker worker=new Worker();
        worker.setName(nickName);
        worker.setPw(password);
        worker.setEmail(email);
        worker.setIntro("");
        worker=(Worker)userService.register(worker);
        return Long.toString(worker.getId());
    }

    @GetMapping("/login")
//    @ApiOperation(value = "访问用户登录界面")
    public String visitLoginPage(){
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        String userId=(String)session.getAttribute("userId");
        User user=null;
        if(userId!=null){
            user=userService.getUser(Long.parseLong(userId));
            return classify(user);
        } else {
            //如果未登录返回登录页面
            return "user/Login";
        }
    }

    @PostMapping("/login")
//    @ApiOperation(value = "用户登录，成功后返回用户工作界面；失败则返回登录界面，显示错误信息")
    /**
     * 表单里的input模块的name属性决定了参数名
     */
    @ResponseBody
    public String login(@RequestParam("userId")String userId
            ,@RequestParam("password")String password) {
        User user  = userService.login(Long.parseLong(userId), password);
        if (user==null) {
            return "login failed";
        }

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        HttpSession session = request.getSession();

        session.setAttribute("userId", userId);
        session.setMaxInactiveInterval(7200);

        Cookie cookie = new Cookie("userId", userId);
        cookie.setMaxAge(1209600);
        cookie.setPath("/");
        response.addCookie(cookie);

        return "login succeed";
    }

    private String classify(User user) {
        if(user instanceof Publisher) {
            return "redirect:/publisherPage/publish";
        }else if(user instanceof Worker){
            return "redirect:/myProjects/projects";
        }else {
            return "redirect:/admin/";
        }
    }

    @PostMapping("/logout")
    @ResponseBody
    public String logout(){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        if(!session.isNew()) {
            session.invalidate();
            return "success";
        }else{
            return "false";
        }
    }

    @GetMapping("/modify")
//    @ApiOperation(value="访问修改信息页面",notes="一定要根据账号密码做身份验证，否则可能会修改到别的用户的数据！")
    public String visitModifyPage(Model model,
                                  @RequestParam(value="id",required=true)String id,
                                  @RequestParam(value="pw",required=true)String pw){

        User user=userService.getUser(Long.parseLong(id));
        model.addAttribute("user", user);
//        if(user.getPw().equals(pw))
//            return "workSpace";
//        else
            return "error";//身份验证失败返回错误页

    }

    @PostMapping("/modify")
//    @ApiOperation(value = "用户修改自身信息")
    public String modify(@ModelAttribute("user")User user){
//        if(userService.modify(user))
//            return "workSpace";
//        else
            return "wrong";
    }

    @GetMapping("/userDetail/{userId}")
    public String userPage(@PathVariable("userId") String username/*这个参数后面可以删掉了*/) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        String userId = (String) session.getAttribute("userId");
        if (userId != null) {
            //已登录
            User user = userService.getUser(Long.parseLong(userId));
            if (user instanceof Publisher) {
                return "user/publisherDetail";
            } else {
                return "user/workerDetail";
            }
        } else {
            //如果未登录返回登录页面
            return "redirect:/user/login";
        }
    }

    @PostMapping("/userDetail/{userId}")
    @ResponseBody
    public String userDetail(@PathVariable("userId") String userId){
        User user=userService.getUser(Long.parseLong(userId));
        String result="{";
        if (user instanceof Publisher){
            result+="\"userType\":\"publisher\",";
        }else{
            result+="\"userType\":\"worker\",";
        }
        result+="\"userId\":\""+user.getId()+"\",";
        result+="\"nickName\":\""+user.getName()+"\",";
        result+="\"email\":\""+user.getEmail()+"\",";
        result+="\"intro\":\""+user.getIntro()+"\",";
        result+="\"money\":\""+user.getMoney()+"\"";
        if (user instanceof Worker){
         //这里填Worker多出来的
        }
        result+="}";
        return result;
    }

    @GetMapping("/labels/{userId}")
    @ResponseBody
    public String getLabels(@PathVariable("userId") String userId) {
    	String res = "";//name_val,
    	Calendar cld = Calendar.getInstance();
    
    	Map<String, Integer> map = wkservice.viewWorkerMonthLabel(Long.parseLong(userId), cld);
    
    	for(Map.Entry<String,Integer > entry:map.entrySet()){  
    	      System.out.println("key=" +entry.getKey() +" and value="+entry.getValue());  
    	      res = res+entry.getKey()+"_"+entry.getValue()+",";  	      
    	  }  
    	res = res.substring(0,res.length()-1);
    	return res;
    }
    
    
    /**
     *返回某一月的项目类型
     * @param userId
     * @return
     */
    @GetMapping("/monthlabels/{userId}")
    @ResponseBody
    public String getMonthLabels(HttpServletRequest request, HttpServletResponse response,
    		@PathVariable("userId") String userId) {
    	String res = "";//name_val,
    	int month = Integer.parseInt(request.getParameter("month"));
    	
    	Calendar cld = Calendar.getInstance();
    	int current_month = cld.get(Calendar.MONTH)+1;
    	int dif = month - current_month ;
    	System.out.println("DIF: "+dif);
    	cld.add(Calendar.MONTH,dif );
    
    	Map<String, Integer> map = wkservice.viewWorkerMonthLabel(Long.parseLong(userId), cld);
    
    	for(Map.Entry<String,Integer > entry:map.entrySet()){  
    	      System.out.println("key=" +entry.getKey() +" and value="+entry.getValue());  
    	      res = res+entry.getKey()+"_"+entry.getValue()+",";  	      
    	  }  
    	if(res.equals("")) {
    		return res;
    	}
    	res = res.substring(0,res.length()-1);
    	return res;
    }
    
    /**
     * 返回6月的项目数量
     * @param userId
     * @return
     */
    @GetMapping("/pjnumbers/{userId}")
    @ResponseBody
    public String getpjnumbers(@PathVariable("userId") String userId) {
    	String res = "";//name_val,
    	String month = "";
    	String data = "";
    	Calendar cld = Calendar.getInstance();
    	int current_month = cld.get(Calendar.MONTH);
    	cld.add(Calendar.MONTH, -current_month);
    
//    	int cm = cld.get(Calendar.MONTH);
//    	
//    	for(int i=0;i<cm;i++) {
//    		
//    	}
    	
    	List<Project> list = wkservice.viewWorkerMonthJoinProject(Long.parseLong(userId), cld);
    
    	int len = list.size();
    	
    	return len+"";
    }
    
    
    
    @GetMapping("/abilities/{userId}")
    @ResponseBody
    public String getAbilities(@PathVariable("userId") String userId) {
    	String res = "";//labelname_accu_count_bias
    	List<String> list = wkservice.getWorkerAbilitiesInString(Long.parseLong(userId));
    	String labels = "";
    	String accu = "";
    	String count = "";
    	String bias = "";
    	
    	int len = list.size();
    	for(int i=0;i<len;i++) {
    		String tip = list.get(i);
    		String[] datas = tip.split("_");
    		labels += datas[0];
    		accu += datas[1];
    		count += datas[2];
    		bias += datas[3];
    		
    		if(i != len-1) {
    			labels += "_";
        		accu += "_";
        		count += "_";
        		bias += "_";
    		}
    	}
    	
    	return labels+","+accu+","+count+","+bias;
    }
    
    
}
