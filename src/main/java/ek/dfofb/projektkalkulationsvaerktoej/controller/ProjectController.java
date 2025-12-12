package ek.dfofb.projektkalkulationsvaerktoej.controller;

import ek.dfofb.projektkalkulationsvaerktoej.model.*;
import ek.dfofb.projektkalkulationsvaerktoej.service.*;
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
    private final AccountService accountService;
    private final AuthorizationService authorizationService;

    public ProjectController(ProjectService projectService, TaskService taskService, RoleService roleService,
                             AuthorizationService authorizationService, AccountService accountService) {
        this.projectService = projectService;
        this.taskService = taskService;
        this.roleService = roleService;
        this.accountService = accountService;
        this.authorizationService = authorizationService;
    }

    @GetMapping()
    public String myTasks(Model model, HttpSession httpSession) {
        Account account = (Account) httpSession.getAttribute("account");
        if (account == null) {
            return "redirect:/account/login";
        }
        model.addAttribute("tasks", taskService.getAllTasksForAccount(account.getAccountID()));
        model.addAttribute("task", new Task());
        model.addAttribute("projects", projectService.getAllProjectsForAccount(account.getAccountID()));
        return "show-my-tasks";
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
        httpSession.setAttribute("currentProject", projectID);
        return "redirect:/project/" + projectName;
    }


    @GetMapping("/{projectName}")
    public String showProject(Model model, @PathVariable String projectName, HttpSession httpSession) {
        Account account = (Account) httpSession.getAttribute("account");
        if (account == null) {
            return "redirect:/account/login";
        }
        if (httpSession.getAttribute("currentProject") == null) {
            return "redirect:/project/list";
        }
        if (!authorizationService.hasPermission(account.getRoleID(), Permission.VIEW_PROJECT)) {
            return "redirect:/project";
        }
        int projectID = (int) httpSession.getAttribute("currentProject");
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
        model.addAttribute("hoursSpent", hoursSpent);
        //todo move to service?
        List<Account> accountsAssignedToProject = projectService.getAllAccountsAssignedToProject(projectID);
        List<Account> accounts = accountService.getAllAccounts();
        for (int i = 0; i < accounts.size(); i++) {
            if (accountsAssignedToProject.contains(accounts.get(i))) {
                accounts.remove(accounts.get(i));
                i--;
            }
        }
        model.addAttribute("projectMembers", accountsAssignedToProject);
        model.addAttribute("accounts", accounts);
        model.addAttribute("account", new Account());
        return "show-project";
    }

    @GetMapping("/{projectName}/edit")
    public String editProject(Model model, @PathVariable String projectName, HttpSession httpSession) {
        Account account = (Account) httpSession.getAttribute("account");
        if (account == null) {
            return "redirect:/account/login";
        }
        if (httpSession.getAttribute("currentProject") == null) {
            return "redirect:/project/list";
        }
        if (!authorizationService.hasPermission(account.getRoleID(), Permission.EDIT_PROJECTS)) {
            return "redirect:/project";
        }
        int projectID = (int) httpSession.getAttribute("currentProject");
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

    @PostMapping("/assign")
    public String assignAccountToProject(@ModelAttribute Account account, HttpSession httpSession, Model model) {
        Account myAccount = (Account) httpSession.getAttribute("account");
        if (myAccount == null) {
            return "redirect:/account/login";
        }
        if (!authorizationService.hasPermission(myAccount.getRoleID(), Permission.ADD_PROJECTS)) {
            return "redirect:/project";
        }
        Project project = projectService.getProjectByID((Integer) httpSession.getAttribute("currentProject"));
        projectService.assignAccountToProject(account.getAccountID(), project.getProjectID());
        return showProject(model, project.getName(), httpSession);
    }

    @PostMapping("/remove/assigned/account")
    public String removeAccountFromProject(@ModelAttribute Account account, HttpSession httpSession, Model model) {
        Account myAccount = (Account) httpSession.getAttribute("account");
        if (myAccount == null) {
            return "redirect:/account/login";
        }
        if (!authorizationService.hasPermission(myAccount.getRoleID(), Permission.ADD_PROJECTS)) {
            return "redirect:/project";
        }
        Project project = projectService.getProjectByID((Integer) httpSession.getAttribute("currentProject"));
        projectService.removeAccountFromProject(account.getAccountID(), project.getProjectID());
        return showProject(model, project.getName(), httpSession);
    }

    @PostMapping("/delete")
    public String deleteProject(@ModelAttribute Project project, HttpSession httpSession) {
        Account account = (Account) httpSession.getAttribute("account");
        if (account == null) {
            return "redirect:/account/login";
        }
        if (!authorizationService.hasPermission(account.getRoleID(), Permission.DELETE_PROJECTS)) {
            return "redirect:/project";
        }
        projectService.deleteProject(project.getProjectID());
        return "redirect:list";
    }

}
