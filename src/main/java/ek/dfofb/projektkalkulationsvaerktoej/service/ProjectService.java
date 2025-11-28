package ek.dfofb.projektkalkulationsvaerktoej.service;

import ek.dfofb.projektkalkulationsvaerktoej.model.Project;
import ek.dfofb.projektkalkulationsvaerktoej.model.Task;
import ek.dfofb.projektkalkulationsvaerktoej.repository.ProjectRepository;
import ek.dfofb.projektkalkulationsvaerktoej.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService
{
    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;

    public ProjectService(ProjectRepository projectRepository, TaskRepository taskRepository)
    {
        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;
    }

    public List<Project> getAllProjects()
    {
        return projectRepository.getAllProjects();
    }

    public Project getProjectByID(int id)
    {
        Project project = projectRepository.getProjectByID(id);

        List<Task> tasks = taskRepository.getAllTasksForProjects(id);
        int sum = tasks.stream().mapToInt(this::getTotalHoursForTask).sum();

        project.setHourEstimate(sum);
        return project;
    }

    private int getTotalHoursForTask(Task task)
    {
        int total = task.getHourEstimate();
        if (task.getTasks() != null)
        {
            for (Task sub : task.getTasks())
            {
                total += getTotalHoursForTask(sub);
            }
        }
        return total;
    }

    public boolean createProject(Project project)
    {
        return projectRepository.addProject(project);
        // Vi kan eventuelet her lave en default startDate /isActive hvis ikke vi allerede har sat det manuelt.
        // Det kan vi eventuelt snakke om i skolen
    }

    public Project updateProject(Project project)
    {
        return projectRepository.updateProject(project);
    }
}
