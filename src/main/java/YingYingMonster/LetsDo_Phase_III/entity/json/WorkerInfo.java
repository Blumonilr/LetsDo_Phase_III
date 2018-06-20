package YingYingMonster.LetsDo_Phase_III.entity.json;

import YingYingMonster.LetsDo_Phase_III.entity.Project;
import YingYingMonster.LetsDo_Phase_III.entity.role.Worker;
import YingYingMonster.LetsDo_Phase_III.service.AdminService;
import YingYingMonster.LetsDo_Phase_III.service.WorkerService;
import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

public class WorkerInfo {
    @Expose
    private Worker worker;
    @Expose
    private List<Project> projects;

    public WorkerInfo(String workerId, AdminService adminService,WorkerService workerService) {
        this.worker=adminService.finByWorkerId(Long.parseLong(workerId));
        List<Project> temp=workerService.viewMyProjects(Long.parseLong(workerId),"");
        this.projects=(temp==null)?new ArrayList<>():temp;
    }

    public Worker getWorker() {
        return worker;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }
}
