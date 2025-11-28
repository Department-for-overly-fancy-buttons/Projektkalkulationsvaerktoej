package ek.dfofb.projektkalkulationsvaerktoej.controller;

import ek.dfofb.projektkalkulationsvaerktoej.model.Project;
import ek.dfofb.projektkalkulationsvaerktoej.service.ProjectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/project")
public class ProjectController
{
    private final ProjectService projectService;

    public ProjectController(ProjectService projectService)
    {
        this.projectService = projectService;
    }

    @GetMapping("/list")
    public String listProjects(Model model)
    {
        model.addAttribute("projects", projectService.getAllProjects());
        return "project/list";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model)
    {
        Project project = new Project();
        project.setActive(true);
        model.addAttribute("project", project);
        return "project/create";
    }

    @PostMapping("/create")
    public String handleCreateForm(@ModelAttribute("project") Project project)
    {
        projectService.createProject(project);
        return "redirect:/project/list";
    }

    @PostMapping("/delete/{id}")
    public String deleteProject(@PathVariable("id") int projectId)
    {
        projectService.deleteProject(projectId);
        return "redirect:/project/list";
    }
}
