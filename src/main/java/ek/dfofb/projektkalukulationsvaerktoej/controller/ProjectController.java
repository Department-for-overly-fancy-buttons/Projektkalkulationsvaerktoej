package ek.dfofb.projektkalukulationsvaerktoej.controller;

import ek.dfofb.projektkalukulationsvaerktoej.model.Project;
import ek.dfofb.projektkalukulationsvaerktoej.service.ProjectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/project")
public class ProjectController
{
    private final ProjectService projectService;
    public ProjectController(ProjectService projectService){
        this.projectService = projectService;
    }

    @GetMapping("/view-projects")
    public String showAllProjects(Model model){
        ArrayList<Project> projects = new ArrayList<>();
        ArrayList<Boolean> subtasks = new ArrayList<>();
        subtasks.add(true);
        subtasks.add(true);
        subtasks.add(false);
        subtasks.add(false);
        subtasks.add(false);

        projects.add(new Project("Data analyse", "27-11-2025", subtasks));

        subtasks.add(false);
        subtasks.add(false);

        projects.add(new Project("Marketing for ecomcon", "28-01-2022", subtasks));
        model.addAttribute("projectList", projects);

        List<Integer> listofcompletedtaskpercentage = new ArrayList<>();
        listofcompletedtaskpercentage.add(40);
        listofcompletedtaskpercentage.add(28);

        model.addAttribute("percentOfProgressDone", listofcompletedtaskpercentage);
        return "project-overview";
    }


}
