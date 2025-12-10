package ek.dfofb.projektkalkulationsvaerktoej.controller;

import ek.dfofb.projektkalkulationsvaerktoej.model.*;
import ek.dfofb.projektkalkulationsvaerktoej.service.AuthorizationService;
import ek.dfofb.projektkalkulationsvaerktoej.service.ProjectService;
import ek.dfofb.projektkalkulationsvaerktoej.service.RoleService;
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
    private final RoleService roleService;
    private final AuthorizationService authorizationService;

    public ProjectController(ProjectService projectService, TaskService taskService, RoleService roleService,
                             AuthorizationService authorizationService) {
        this.projectService = projectService;
        this.taskService = taskService;
        this.roleService = roleService;
        this.authorizationService = authorizationService;
    }

    @GetMapping("/list")
    public String listProjects(Model model, HttpSession httpSession) {
        Account account = (Account) httpSession.getAttribute("account");
        if (account == null) {
            return "redirect:/account/login";
        }
        model.addAttribute("projects", projectService.getAllProjects());
        model.addAttribute("role", roleService.getRoleFromID(account.getRoleID()));
        return "list-all-projects";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model, HttpSession httpSession) {
        Account account = (Account) httpSession.getAttribute("account");
        if (account == null) {
            return "redirect:/account/login";
        }
        if (!authorizationService.hasPermission(account.getRoleID(), Permission.ADD_PROJECTS)) {
            return "redirect:/project";
        }
        Project project = new Project();
        project.setIsActive(true);
        model.addAttribute("project", project);
        return "create-project-form";
    }

    @PostMapping("/create")
    public String handleCreateForm(@ModelAttribute Project project, HttpSession httpSession) {
        Account account = (Account) httpSession.getAttribute("account");
        if (account == null) {
            return "redirect:/account/login";
        }
        if (!authorizationService.hasPermission(account.getRoleID(), Permission.ADD_PROJECTS)) {
            return "redirect:/project";
        }
        projectService.createProject(project);
        return "redirect:/project/list";
    }

    @PostMapping("/getID")
    public String saveCurrentProjectID(String projectName, int projectID, HttpSession httpSession) {
        if (httpSession.getAttribute("account") == null) {
            return "redirect:/account/login";
        }
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
        if (!authorizationService.hasPermission(account.getRoleID(), Permission.VIEW_PROJECT)) {
            return "redirect:/project";
        }
        int projectID = (int) httpSession.getAttribute(projectName);
        Project project = projectService.getProjectByID(projectID);
        List<Task> tasks = taskService.getAllTasksForProjects(projectID);

        //Skal nok rykkes til servicelaget
        int hoursInitial = 0;
        int hoursRemaining = 0;
        int hoursSpent = 0;
        for (Task task : tasks) {
            hoursRemaining += taskService.hoursLeftOnTask(task.getTaskID(), false);
            hoursInitial += taskService.hoursLeftOnTask(task.getTaskID(), true);
            hoursSpent += task.getHoursSpentOnTask();
        }
        model.addAttribute("hourEstimateRemaining", hoursRemaining);
        model.addAttribute("hourEstimateInitial", hoursInitial);
        model.addAttribute("project", project);
        model.addAttribute("tasks", tasks);
        model.addAttribute("role", roleService.getRoleFromID(account.getRoleID()));
        model.addAttribute("hoursSpent",hoursSpent);
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
        if (!authorizationService.hasPermission(account.getRoleID(), Permission.EDIT_PROJECTS)) {
            return "redirect:/project";
        }
        int projectID = (int) httpSession.getAttribute(projectName);
        Project project = projectService.getProjectByID(projectID);
        model.addAttribute("project", project);
        return "edit-project-form";
    }


    @PostMapping("/edit")
    public String updateProject(@ModelAttribute Project project, HttpSession httpSession) {
        Account account = (Account) httpSession.getAttribute("account");
        if (account == null) {
            return "redirect:/account/login";
        }
        if (!authorizationService.hasPermission(account.getRoleID(), Permission.EDIT_PROJECTS)) {
            return "redirect:/project";
        }
        projectService.updateProject(project);
        String projectName = projectService.getProjectByID(project.getProjectID()).getName();
        return saveCurrentProjectID(projectName, project.getProjectID(), httpSession);
    }
}
