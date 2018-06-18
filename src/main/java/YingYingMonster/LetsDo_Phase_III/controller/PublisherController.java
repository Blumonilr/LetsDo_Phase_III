package YingYingMonster.LetsDo_Phase_III.controller;

import YingYingMonster.LetsDo_Phase_III.entity.MarkMode;
import YingYingMonster.LetsDo_Phase_III.model.ProjectState;
import YingYingMonster.LetsDo_Phase_III.service.LabelService;
import YingYingMonster.LetsDo_Phase_III.service.ProjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import YingYingMonster.LetsDo_Phase_III.entity.*;
import YingYingMonster.LetsDo_Phase_III.service.PublisherService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/publisherPage")
public class PublisherController {

    @Autowired
    private PublisherService publisherService;
    @Autowired
    private LabelService labelService;
    @Autowired
    private ProjectService projectService;

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    //发布者界面
    @GetMapping("/publish")
    public String publishPage(){
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        String userId=(String)session.getAttribute("userId");
        if(userId!=null){
            //已登录
            return "publisher/publish";
        } else {
            //如果未登录返回登录页面
            return "redirect:/user/login";
        }
    }


    //请求发布项目
    @PostMapping("/publish")
    @ResponseBody
    public String createProject(@RequestParam(value = "file")MultipartFile dataSet,
                                @RequestParam("projectName")String projectName,
                                @RequestParam("markMode")String markMode,
                                @RequestParam("maxNumPerPic")String maxNumPerPic,
                                @RequestParam("minNumPerPic")String minNumPerPic,
                                @RequestParam("endDate")String endDate,
                                @RequestParam("tagRequirement")String tagRequirement,
                                @RequestParam("levelLimit")String levelLimit,
                                @RequestParam("testAccuracy")String testAccuracy,
                                @RequestParam("money")String money,
                                @RequestParam("tags")String tags) {
        System.out.println("进入了方法发布项目的方法");
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        String publisherId = (String) session.getAttribute("userId");
        MarkMode type = null;
        if (markMode.equals("框选标注")) {
            type = MarkMode.SQUARE;
        } else if (markMode.equals("区域标注")) {
            type = MarkMode.AREA;
        }
        int payment = Integer.parseInt(money);

        String[] tagList=tags.split(",");
        List<String> labelList=new ArrayList<>(Arrays.asList(tagList));

        Project project = new Project(type, Long.parseLong(publisherId), projectName,
                Integer.parseInt(maxNumPerPic), Integer.parseInt(minNumPerPic), endDate, tagRequirement, Integer.parseInt(levelLimit),
                Double.parseDouble(testAccuracy), payment,labelList);

        logger.info("方法开始");
        project = publisherService.createProject(project, dataSet);
        logger.info("方法执行结束");
        logger.info("project object={}", project);


        if (project.getId() != 0) {
            return "success";
        } else {
            return "fail";
        }
    }

    @PostMapping("/getLabels")
    @ResponseBody
    public String getLabels(){
        List<Label> list=labelService.findAllLabel();
        StringBuilder sb=new StringBuilder();
        for (int i=0;i<list.size();i++) {
            if (i == list.size() - 1)
                sb.append(list.get(i).getName());
            else
                sb.append(list.get(i).getName() + ",");
        }
        return sb.toString();
    }

    @PostMapping("/publishTestPage")
    @ResponseBody
    public String askPublishTest(@RequestParam("projectId") String projectId){
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        if(publisherService.getAProject(Long.parseLong(projectId)).getTestProject()==null){
            session.setAttribute("testSet",1);
            return "noTest";
        } else {
            return "alreadyHaveTest";
        }
    }

    @GetMapping("/publishTestPage")
    public String publishTestPage(){
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        String userId=(String)session.getAttribute("userId");
        if(userId==null){
            return "redirect:/user/login";
        }
        if(session.getAttribute("testSet")!=null){
            return "publisher/publishTest";
        } else {
            return "redirect:/project/publisher/projectDetail";
        }
    }

    @PostMapping("/publishTest")
    @ResponseBody
    public String publishTest(@RequestParam(value = "file")MultipartFile dataSet,
                              @RequestParam("projectId") String projectId){
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        TestProject testProject;
        testProject=publisherService.addTestProject(Long.parseLong(projectId),dataSet);
        if(testProject.getId()!=0) {
            session.removeAttribute("testSet");
            return testProject.getInviteCode();
        }else{
            return "fail";
        }
    }

    @GetMapping("/editTagTree")
    public String tagTreePage(){
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        String userId=(String)session.getAttribute("userId");
        if(userId==null){
            return "redirect:/user/login";
        }
        return "publisher/uploadTagTree";
    }

    @PostMapping("/editTagTree")
    @ResponseBody
    public String tagTree(@RequestParam String projectId){
        Project project=projectService.getAProject(Long.parseLong(projectId));
        String tagTree=project.getTagTree();
        if(tagTree==null){
            return projectService.getInitialTextNodeTree();
        }else{
            return tagTree;
        }
    }

    @PostMapping("/uploadTagTree")
    @ResponseBody
    public String uploadTagTree(@RequestParam String projectId,@RequestParam String tagTree){
        projectService.setProjectCustomTextNode(Long.parseLong(projectId),tagTree);
        return "上传成功";
    }

    @PostMapping("/downloadCheck")
    @ResponseBody
    public String downloadCheck(@RequestParam String projectId){
        Project project=publisherService.getAProject(Long.parseLong(projectId));
        if(project.getProjectState()!=ProjectState.closed){
            return "project not finished";
        }else {
            return "success";
        }
    }

    @GetMapping("/download")
    public void downloadResult(@RequestParam String projectId) throws Exception{
        HttpServletResponse response=((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        Project project=publisherService.getAProject(Long.parseLong(projectId));
        if(project.getProjectState()!=ProjectState.closed){
            return ;
        }
        //设置响应头，控制浏览器下载该文件
        response.setContentType("application/form-data");
        response.setHeader("content-disposition", "attachment;filename=" + project.getProjectName()+"result.zip");
        //创建输出流
        OutputStream outputStream = response.getOutputStream();
        //创建缓冲区
        byte file[] = publisherService.downloadTags(Long.parseLong(projectId));
        //输出缓冲区的内容到浏览器，实现文件下载
        outputStream.write(file);
        outputStream.flush();
        //关闭输出流
        outputStream.close();
    }

    //请求提交记录界面
    @GetMapping("/{projectName}/pushEvents")
    public String pushEventsPage(@PathVariable("id")String id,@PathVariable("projectName")String projectName){
        return "pushEvents";
    }

    //异步请求提交记录信息
    @PostMapping("/{projectName}/pushEvents")
    @ResponseBody
    public String pushEvents(@PathVariable("id")String id,@PathVariable("projectName")String projectName){
        return "消息记录";
        //返回消息记录
    }


    //查看项目内图片列表
    @GetMapping("/{projectName}/pictures")
    public String pictureListPage(@PathVariable("projectName")String projectName){
        return "pictureListPage";
    }

    @PostMapping("/{projectName}/pictures")
    @ResponseBody
    public String pictureList(@PathVariable("projectName")String projectName){
        return null;//返回图片列表
    }

    //查看标注情况
    @GetMapping("/{projectName}/{pictureId}")
    public String pictureTagPage(){
        return "tagPage";
    }

    @PostMapping("/{projectName}/{pictureId}")
    @ResponseBody
    public String pictureTag(@PathVariable("projectName")String projectName,@PathVariable("pictureId")String pictureId){
        return null;//返回各种图片信息，问下jbs
    }
}
