package ek.dfofb.projektkalkulationsvaerktoej.controller;

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

    @PostMapping("/getID")
    public String saveCurrentProjectID(String projectName, int projectID, HttpSession httpSession) {
        httpSession.setAttribute(projectName, projectID);
        return "redirect:/project/" + projectName;
    }

    @PostMapping("/task/getID")
    public String saveCurrentTaskID(String taskName, int taskID, HttpSession httpSession) {
        String projectName = projectService.getProjectByID(taskService.getTaskByID(taskID).getProjectID()).getName();
        httpSession.setAttribute("currentTask", taskID);
        int parentID = taskService.getTaskByID(taskID).getParentID();
        if (parentID == 0) {
            return "redirect:/project/" + projectName + "/" + taskName;
        }
        return "redirect:/project/" + projectName + "/" + taskService.getTaskByID(parentID).getName() + "/" + taskName;
    }

    @GetMapping("/{projectName}")
    public String showProject(Model model, @PathVariable String projectName, HttpSession httpSession) {
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

    @GetMapping("/{projectName}/create/task")
    public String createTask(Model model, @PathVariable String projectName, HttpSession httpSession) {
        if (httpSession.getAttribute(projectName) == null) {
            return "redirect:/project/list";
        }
        Task task = new Task();
        task.setProjectID((Integer) httpSession.getAttribute(projectName));
        model.addAttribute("task", task);
        return "create-task-form";
    }

    @GetMapping("/{projectName}/{taskName}/create/task")
    public String createSubTask(Model model, @PathVariable String projectName, @PathVariable String taskName, HttpSession httpSession) {
        int taskID = (Integer) httpSession.getAttribute("currentTask");
        String currentTask = taskService.getTaskByID(taskID).getName();
        if (httpSession.getAttribute(projectName) == null || httpSession.getAttribute("currentTask") == null || !currentTask.equalsIgnoreCase(taskName)) {
            return "redirect:/project/list";
        }
        Task task = new Task();
        task.setProjectID((Integer) httpSession.getAttribute(projectName));
        task.setParentID((Integer) httpSession.getAttribute("currentTask"));
        model.addAttribute("task", task);
        return "create-sub-task-form";
    }

    @PostMapping("/create/task")
    public String addTask(@ModelAttribute Task task) {
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
        int taskID = (Integer) httpSession.getAttribute("currentTask");
        String name = taskService.getTaskByID(taskID).getName();
        if (httpSession.getAttribute(projectName) == null || httpSession.getAttribute("currentTask") == null || !name.equalsIgnoreCase(taskName)) {
            return "redirect:/project/list";
        }
        model.addAttribute("task", taskService.getTaskByID(taskID));
        model.addAttribute("tasks", taskService.getAllSubTasks(taskID));
        model.addAttribute("hourEstimate", taskService.hoursLeftOnTask(taskID));
        model.addAttribute("projectName", projectName);
        return "show-task";
    }

    @GetMapping("/{projectName}/{taskName}/{subTaskName}")
    public String showSubTask(Model model, @PathVariable String projectName, @PathVariable String taskName, @PathVariable(required = false) String subTaskName, HttpSession httpSession) {
        int taskID = (Integer) httpSession.getAttribute("currentTask");
        int mainTaskID = taskService.getTaskByID(taskID).getParentID();
        String currentTask = taskService.getTaskByID(taskID).getName();
        String mainTaskName = taskService.getTaskByID(mainTaskID).getName();
        if (httpSession.getAttribute(projectName) == null || httpSession.getAttribute("currentTask") == null
                || !currentTask.equalsIgnoreCase(subTaskName) || !mainTaskName.equalsIgnoreCase(taskName)) {
            return "redirect:/project/list";
        }

        model.addAttribute("task", taskService.getTaskByID(taskID));
        model.addAttribute("tasks", taskService.getAllSubTasks(taskID));
        model.addAttribute("projectName", projectName);
        model.addAttribute("subTaskName", currentTask);
        model.addAttribute("mainTaskID", mainTaskID);
        model.addAttribute("mainTask", mainTaskName);
        model.addAttribute("hourEstimate", taskService.hoursLeftOnTask(taskID));
        return "show-task";
    }

}
