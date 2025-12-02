package ek.dfofb.projektkalkulationsvaerktoej.controller;

import ek.dfofb.projektkalkulationsvaerktoej.model.Project;
import ek.dfofb.projektkalkulationsvaerktoej.service.ProjectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("project")
public class ProjectController {
    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/list")
    public String listProjects(Model model) {
        model.addAttribute("projects", projectService.getAllProjects());
        return "list-all-projects";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        Project project = new Project();
        project.setActive(true);
        model.addAttribute("project", project);
        return "create-project-form";
    }

    @PostMapping("/create")
    public String handleCreateForm(@ModelAttribute Project project) {
        projectService.createProject(project);
        return "redirect:/project/list";
    }
}
