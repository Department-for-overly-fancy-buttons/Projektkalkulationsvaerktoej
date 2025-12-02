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
        for(Task task: tasks){
            hours += taskService.hoursLeftOnTask(task.getTaskID());
        }
        model.addAttribute("hourEstimate",hours);
        model.addAttribute("project", project);
        model.addAttribute("tasks",tasks);
        return "showProject";
    }

    @GetMapping("/{projectName}/create/task")
    public String createTask(Model model, @PathVariable String projectName, HttpSession httpSession) {
        if (httpSession.getAttribute(projectName) == null) {
            return "redirect:/project/list";
        }
        Task task = new Task();
        task.setProjectID((Integer) httpSession.getAttribute(projectName));
        int projectID = (Integer) httpSession.getAttribute(projectName);
        System.out.println(projectID);
        model.addAttribute("task", task);
        return "create-task-form";
    }

    @PostMapping("/create/task")
    public String addTask(@ModelAttribute Task task) {
        taskService.addTask(task);
        if(task.getParentID()!=0){
            //skal redirecte til tasken den er lavet under
            return ("redirect:/project/list");
        }
        String projectName = projectService.getProjectByID(task.getProjectID()).getName();
        return ("redirect:/project/" + projectName);
    }
}
