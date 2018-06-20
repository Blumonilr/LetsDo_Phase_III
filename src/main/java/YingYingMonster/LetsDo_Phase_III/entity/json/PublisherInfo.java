package YingYingMonster.LetsDo_Phase_III.entity.json;

import YingYingMonster.LetsDo_Phase_III.entity.Project;
import YingYingMonster.LetsDo_Phase_III.entity.role.Publisher;
import YingYingMonster.LetsDo_Phase_III.service.AdminService;
import YingYingMonster.LetsDo_Phase_III.service.PublisherService;
import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

public class PublisherInfo {
    @Expose
    private Publisher publisher;
    @Expose
    private List<Project> projects;

    public PublisherInfo(String publisherId, AdminService adminService,PublisherService publisherService) {
        this.publisher=adminService.findByPublisherId(Long.parseLong(publisherId));
        List<Project> temp= publisherService.findProjectByPublisherId(Long.parseLong(publisherId));
        this.projects=(temp==null)?new ArrayList<>():temp;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }
}
