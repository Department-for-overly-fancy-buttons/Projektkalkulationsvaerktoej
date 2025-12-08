package ek.dfofb.projektkalkulationsvaerktoej.controller;

import ek.dfofb.projektkalkulationsvaerktoej.model.Account;
import ek.dfofb.projektkalkulationsvaerktoej.model.Project;
import ek.dfofb.projektkalkulationsvaerktoej.model.Task;
import ek.dfofb.projektkalkulationsvaerktoej.service.ProjectService;
import ek.dfofb.projektkalkulationsvaerktoej.service.TaskService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("project")
public class ProjectController {
    private final ProjectService projectService;
    private final TaskService taskService;

    public ProjectController(ProjectService projectService, TaskService taskService) {
        this.projectService = projectService;
        this.taskService = taskService;
    }

    @GetMapping("/list")
    public String listProjects(Model model, HttpSession httpSession) {
        Account account = (Account) httpSession.getAttribute("account");
        if (account == null) {
            return "redirect:/account/login";
        }
        model.addAttribute("projects", projectService.getAllProjects());
        return "list-all-projects";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model, HttpSession httpSession) {
        Account account = (Account) httpSession.getAttribute("account");
        if (account == null) {
            return "redirect:/account/login";
        }
        Project project = new Project();
        project.setIsActive(true);
        model.addAttribute("project", project);
        return "create-project-form";
    }

    @PostMapping("/create")
    public String handleCreateForm(@ModelAttribute Project project) {
        projectService.createProject(project);
        return "redirect:/project/list";
    }

    @PostMapping("/getID")
    public String saveCurrentProjectID(String projectName, int projectID, HttpSession httpSession) {
        httpSession.setAttribute(projectName, projectID);
        return "redirect:/project/" + projectName;
    }


    @GetMapping("/{projectName}")
    public String showProject(Model model, @PathVariable String projectName, HttpSession httpSession) {
        Account account = (Account) httpSession.getAttribute("account");
        if (account == null) {
            return "redirect:/account/login";
        }
        if (httpSession.getAttribute(projectName) == null) {
            return "redirect:/project/list";
        }
        int projectID = (int) httpSession.getAttribute(projectName);
        Project project = projectService.getProjectByID(projectID);
        List<Task> tasks = taskService.getAllTasksForProjects(projectID);

        //Skal nok rykkes til servicelaget
        int hours = 0;
        for (Task task : tasks) {
            hours += taskService.hoursLeftOnTask(task.getTaskID());
        }
        model.addAttribute("hourEstimate", hours);
        model.addAttribute("project", project);
        model.addAttribute("tasks", tasks);
        return "show-project";
    }

    @GetMapping("/{projectName}/edit")
    public String editProject(Model model, @PathVariable String projectName, HttpSession httpSession) {
        Account account = (Account) httpSession.getAttribute("account");
        if (account == null) {
            return "redirect:/account/login";
        }
        if (httpSession.getAttribute(projectName) == null) {
            return "redirect:/project/list";
        }
        int projectID = (int) httpSession.getAttribute(projectName);
        Project project = projectService.getProjectByID(projectID);
        model.addAttribute("project", project);
        return "edit-project-form";
    }


    @PostMapping("/edit")
    public String updateProject(@ModelAttribute Project project, HttpSession httpSession) {
        projectService.updateProject(project);
        String projectName = projectService.getProjectByID(project.getProjectID()).getName();
        return saveCurrentProjectID(projectName, project.getProjectID(), httpSession);
    }
}
