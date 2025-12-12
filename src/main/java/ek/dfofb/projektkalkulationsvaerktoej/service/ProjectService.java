package ek.dfofb.projektkalkulationsvaerktoej.service;

import ek.dfofb.projektkalkulationsvaerktoej.exceptions.DatabaseOperationException;
import ek.dfofb.projektkalkulationsvaerktoej.exceptions.DuplicateProjectException;
import ek.dfofb.projektkalkulationsvaerktoej.exceptions.DuplicateTasklistEntryException;
import ek.dfofb.projektkalkulationsvaerktoej.exceptions.ProjectNotFoundException;
import ek.dfofb.projektkalkulationsvaerktoej.model.Account;
import ek.dfofb.projektkalkulationsvaerktoej.model.Project;
import ek.dfofb.projektkalkulationsvaerktoej.model.Task;
import ek.dfofb.projektkalkulationsvaerktoej.repository.interfaces.IProjectRepository;
import ek.dfofb.projektkalkulationsvaerktoej.repository.interfaces.ITaskRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
//TODO: Vi kan eventuelt her lave en default startDate /isActive hvis ikke vi allerede har sat det manuelt.
// Det kan vi eventuelt snakke om i skolen

@Service
public class ProjectService {

    //TODO: Hvordan håndtere vi startdate efter deadline eller et project i fortiden

    private final IProjectRepository projectRepository;
    private final ITaskRepository taskRepository;

    public ProjectService(IProjectRepository projectRepository, ITaskRepository taskRepository) {
        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;
    }

    public List<Project> getAllProjects() {
        try {
            return projectRepository.getAllProjects();
        } catch (DataAccessException exception) {
            throw new DatabaseOperationException("A fatal error has occurred while attempting to access projects");
        }
    }

    public Project getProjectByID(int id) {
        try {
            Project project = projectRepository.getProjectByID(id);
            int sum;

            //DOes this exxception checking make sense?
            try {
                List<Task> tasks = taskRepository.getAllTasksForProjects(id);
                sum = tasks.stream().mapToInt(Task::getHourEstimate).sum();
            } catch (DataAccessException exception) {
                throw new DatabaseOperationException("A fatal error has occurred while attempting to access the tasks within " + project.getName());
            }

            project.setHourEstimate(sum);
            return project;

        } catch (DataAccessException exception) {
            throw new ProjectNotFoundException("Failed to find a project with the corresponding id: " + id);
        }
    }

    public boolean createProject(Project project) {
        try {
            return projectRepository.addProject(project);
        } catch (DataIntegrityViolationException exception) {
            throw new DuplicateProjectException("A project of the chosen name (" + project.getName() + ") already exists");
        } catch (DataAccessException exception) {
            throw new DatabaseOperationException("A fatal error has occurred while attempting to create project");
        }
        // Vi kan eventuelet her lave en default startDate /isActive hvis ikke vi allerede har sat det manuelt.
        // Det kan vi eventuelt snakke om i skolen
    }

    public Project updateProject(Project project) {
        try {
            return projectRepository.updateProject(project);
        } catch (DataIntegrityViolationException exception) {
            throw new DuplicateProjectException("A project of the chosen name (" + project.getName() + ") already exists");
        } catch (DataAccessException exception) {
            throw new DatabaseOperationException("A fatal error has occurred while attempting to update project");
        }
    }

    public List<Project> getAllProjectsForAccount(int accountID) throws DataAccessException {
        try {
            return projectRepository.getAllProjectsForAccount(accountID);
        } catch (DataAccessException exception) {
            throw new DatabaseOperationException("A fatal error has occurred while attempting to update project");
        }
    }

    public boolean assignAccountToProject(int accountID, int projectID) throws DataAccessException {
        try {
            return projectRepository.assignAccountToProject(accountID, projectID);
        } catch (DataIntegrityViolationException exception) {
            throw new DuplicateTasklistEntryException("an account with id (" + accountID + ") is already assigned a project with id (" + projectID + ")");
        }
    }

    public List<Account> getAllAccountsAssignedToProject(int projectID) {
        try {
            return projectRepository.getAllAssignedToProject(projectID);
        } catch (DataAccessException exception) {
            throw new DatabaseOperationException("A fatal error has occurred, while attempting to access accounts assigned to project, with id:" + projectID);
        }
    }

    public boolean deleteProject(int ProjectID){
        try {
            return projectRepository.deleteProject(ProjectID);
        }
        catch (DataAccessException exception){
            throw new DatabaseOperationException("Could not remove project");
        }
    }

}
