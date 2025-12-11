package ek.dfofb.projektkalkulationsvaerktoej.controller;

import ek.dfofb.projektkalkulationsvaerktoej.model.Account;
import ek.dfofb.projektkalkulationsvaerktoej.model.Permission;
import ek.dfofb.projektkalkulationsvaerktoej.model.Task;
import ek.dfofb.projektkalkulationsvaerktoej.service.AuthorizationService;
import ek.dfofb.projektkalkulationsvaerktoej.service.ProjectService;
import ek.dfofb.projektkalkulationsvaerktoej.service.RoleService;
import ek.dfofb.projektkalkulationsvaerktoej.service.TaskService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("project")
public class TaskController {

    private final ProjectService projectService;
    private final TaskService taskService;
    private final RoleService roleService;
    private final AuthorizationService authorizationService;

    public TaskController(ProjectService projectService, TaskService taskService, RoleService roleService,
                          AuthorizationService authorizationService) {
        this.projectService = projectService;
        this.taskService = taskService;
        this.roleService = roleService;
        this.authorizationService = authorizationService;
    }

    @PostMapping("/task/getID")
    public String saveCurrentTaskID(String taskName, int taskID, HttpSession httpSession) {
        if (httpSession.getAttribute("account") == null) {
            return "redirect:/account/login";
        }
        int projectID = taskService.getTaskByID(taskID).getProjectID();
        String projectName = projectService.getProjectByID(projectID).getName();
        httpSession.setAttribute(projectName, projectID);
        httpSession.setAttribute("currentTask", taskID);
        int parentID = taskService.getTaskByID(taskID).getParentID();
        if (parentID == 0) {
            return "redirect:/project/" + projectName + "/" + taskName;
        }
        return "redirect:/project/" + projectName + "/" + taskService.getTaskByID(parentID).getName() + "/" + taskName;
    }

    @GetMapping("/{projectName}/create/task")
    public String createTask(Model model, @PathVariable String projectName, HttpSession httpSession) {
        Account account = (Account) httpSession.getAttribute("account");
        if (account == null) {
            return "redirect:/account/login";
        }
        if (httpSession.getAttribute("currentProject") == null) {
            return "redirect:/project/list";
        }
        if (!authorizationService.hasPermission(account.getRoleID(), Permission.ADD_TASKS)) {
            return "redirect:/project";
        }
        Task task = new Task();
        task.setProjectID((Integer) httpSession.getAttribute("currentProject"));
        model.addAttribute("task", task);
        return "create-task-form";
    }

    @GetMapping("/{projectName}/{taskName}/create/task")
    public String createSubTask(Model model, @PathVariable String projectName, @PathVariable String taskName, HttpSession httpSession) {
        Account account = (Account) httpSession.getAttribute("account");
        if (account == null) {
            return "redirect:/account/login";
        }
        if (!authorizationService.hasPermission(account.getRoleID(), Permission.ADD_PROJECTS)) {
            return "redirect:/project";
        }
        int taskID = (Integer) httpSession.getAttribute("currentTask");
        String currentTask = taskService.getTaskByID(taskID).getName();
        if (httpSession.getAttribute("currentProject") == null || httpSession.getAttribute("currentTask") == null || !currentTask.equalsIgnoreCase(taskName)) {
            return "redirect:/project/list";
        }

        Task task = new Task();
        task.setProjectID((Integer) httpSession.getAttribute("currentProject"));
        task.setParentID((Integer) httpSession.getAttribute("currentTask"));
        model.addAttribute("task", task);
        return "create-sub-task-form";
    }

    @PostMapping("/create/task")
    public String addTask(@ModelAttribute Task task, HttpSession httpSession) {
        Account account = (Account) httpSession.getAttribute("account");
        if (account == null) {
            return "redirect:/account/login";
        }
        if (!authorizationService.hasPermission(account.getRoleID(), Permission.ADD_PROJECTS) || task.getIsCompleted()) {
            return "redirect:/project";
        }
        taskService.addTask(task);
        if (task.getParentID() != 0) {
            String projectName = projectService.getProjectByID(task.getProjectID()).getName();
            String parentName = taskService.getTaskByID(task.getParentID()).getName();
            return ("redirect:/project/" + projectName + "/" + parentName);
        }
        String projectName = projectService.getProjectByID(task.getProjectID()).getName();
        return ("redirect:/project/" + projectName);
    }

    @GetMapping("/{projectName}/{taskName}")
    public String showTask(Model model, @PathVariable String projectName, @PathVariable String taskName, HttpSession httpSession) {
        Account account = (Account) httpSession.getAttribute("account");
        if (account == null) {
            return "redirect:/account/login";
        }
        if (!authorizationService.hasPermission(account.getRoleID(), Permission.VIEW_PROJECT)) {
            return "redirect:/project";
        }
        int taskID = (Integer) httpSession.getAttribute("currentTask");
        String name = taskService.getTaskByID(taskID).getName();
        if (httpSession.getAttribute("currentProject") == null || httpSession.getAttribute("currentTask") == null || !name.equalsIgnoreCase(taskName)) {
            return "redirect:/project/list";
        }

        model.addAttribute("task", taskService.getTaskByID(taskID));
        model.addAttribute("tasks", taskService.getAllSubTasks(taskID));
        model.addAttribute("projectName", projectName);
        model.addAttribute("role", roleService.getRoleFromID(account.getRoleID()));
        model.addAttribute("assignedToTask", taskService.getAllAccountsAssignedToTask(taskID));
        return "show-task";
    }

    @GetMapping("/{projectName}/{taskName}/{subTaskName}")
    public String showSubTask(Model model, @PathVariable String projectName, @PathVariable String taskName, @PathVariable(required = false) String subTaskName, HttpSession httpSession) {
        Account account = (Account) httpSession.getAttribute("account");
        if (account == null) {
            return "redirect:/account/login";
        }
        if (!authorizationService.hasPermission(account.getRoleID(), Permission.VIEW_PROJECT)) {
            return "redirect:/project";
        }
        int taskID = (Integer) httpSession.getAttribute("currentTask");
        int mainTaskID = taskService.getTaskByID(taskID).getParentID();
        String currentTask = taskService.getTaskByID(taskID).getName();
        String mainTaskName = taskService.getTaskByID(mainTaskID).getName();
        if (httpSession.getAttribute("currentProject") == null || httpSession.getAttribute("currentTask") == null
                || !currentTask.equalsIgnoreCase(subTaskName) || !mainTaskName.equalsIgnoreCase(taskName)) {
            return "redirect:/project/list";
        }
        model.addAttribute("task", taskService.getTaskByID(taskID));
        model.addAttribute("tasks", taskService.getAllSubTasks(taskID));
        model.addAttribute("projectName", projectName);
        model.addAttribute("subTaskName", currentTask);
        model.addAttribute("mainTaskID", mainTaskID);
        model.addAttribute("mainTask", mainTaskName);
        model.addAttribute("role", roleService.getRoleFromID(account.getRoleID()));
        model.addAttribute("assignedToTask", taskService.getAllAccountsAssignedToTask(taskID));
        return "show-task";
    }


    @GetMapping("/{projectName}/{taskName}/edit")
    public String editTask(Model model, @PathVariable String projectName, @PathVariable String taskName, HttpSession httpSession) {
        Account account = (Account) httpSession.getAttribute("account");
        if (account == null) {
            return "redirect:/account/login";
        }
        if (!authorizationService.hasPermission(account.getRoleID(), Permission.EDIT_TASKS)) {
            return "redirect:/project";
        }
        if (httpSession.getAttribute("currentProject") == null || httpSession.getAttribute("currentTask") == null ||
                !taskService.getTaskByID((Integer) httpSession.getAttribute("currentTask")).getName().equalsIgnoreCase(taskName)) {
            return "redirect:/project/list";
        }
        Task task = taskService.getTaskByID((Integer) httpSession.getAttribute("currentTask"));
        model.addAttribute("task", task);
        return "edit-task-form";
    }

    @GetMapping("/{projectName}/{taskName}/complete")
    public String completeTask(Model model, @PathVariable String projectName, @PathVariable String taskName, HttpSession httpSession) {
        Account account = (Account) httpSession.getAttribute("account");
        if (account == null) {
            return "redirect:/account/login";
        }
        if (httpSession.getAttribute("currentProject") == null || httpSession.getAttribute("currentTask") == null ||
                !taskService.getTaskByID((Integer) httpSession.getAttribute("currentTask")).getName().equalsIgnoreCase(taskName)) {
            return "redirect:/project/list";
        }
        Task task = taskService.getTaskByID((Integer) httpSession.getAttribute("currentTask"));
        for (Task subTask : taskService.getAllSubTasks(task.getTaskID())) {
            if (!subTask.getIsCompleted()) {
                return "redirect:/project/list";
            }
        }
        model.addAttribute("task", task);
        return "complete-task-form";
    }


    @PostMapping("/task/edit")
    public String updateTask(@ModelAttribute Task task, HttpSession httpSession) {
        Account account = (Account) httpSession.getAttribute("account");
        if (account == null) {
            return "redirect:/account/login";
        }
        if (!authorizationService.hasPermission(account.getRoleID(), Permission.EDIT_TASKS)) {
            return "redirect:/project";
        }
        taskService.updateTask(task);
        String taskName = taskService.getTaskByID(task.getTaskID()).getName();
        return saveCurrentTaskID(taskName, task.getTaskID(), httpSession);
    }

    @PostMapping("/task/complete")
    public String markTaskAsDone(@ModelAttribute Task task, HttpSession httpSession) {
        if (httpSession.getAttribute("account") == null) {
            return "redirect:/account/login";
        }
        taskService.markAsDone(task.getTaskID(),task.getHoursSpentOnTask());
        String taskName = taskService.getTaskByID(task.getTaskID()).getName();
        return saveCurrentTaskID(taskName, task.getTaskID(), httpSession);
    }

}
