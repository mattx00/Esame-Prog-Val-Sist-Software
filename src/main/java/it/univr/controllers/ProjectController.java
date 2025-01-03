package it.univr.controllers;

import it.univr.models.Project;
import it.univr.models.User;
import it.univr.services.ProjectService;
import it.univr.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/projects/{projectId}")
    public String showDashboardPage(@PathVariable int projectId, HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");

        Project project = projectService.projectExists(projectId);

        if(project == null){
            System.out.println("male!!!");
        }
        
        model.addAttribute("project", project);

        System.out.println(project.toString());

        return "ProjectDetailPage";
    }
}
