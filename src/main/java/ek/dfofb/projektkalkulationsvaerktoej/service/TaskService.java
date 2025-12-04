package ek.dfofb.projektkalkulationsvaerktoej.service;

import ek.dfofb.projektkalkulationsvaerktoej.exceptions.*;
import ek.dfofb.projektkalkulationsvaerktoej.model.Account;
import ek.dfofb.projektkalkulationsvaerktoej.model.Task;
import ek.dfofb.projektkalkulationsvaerktoej.repository.interfaces.ITaskRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class TaskService {

    private ITaskRepository taskRepository;

    public TaskService(ITaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task getTaskByID(int taskID) {
        try {
            Task task = taskRepository.getTaskByID(taskID);
            task.setHourEstimate(hoursLeftOnTask(taskID));
            return task;
        } catch (DataAccessException exception) {
            throw new TaskNotFoundException("Failed to find a task, with the corresponding id:" + taskID);
        }
    }

    public List<Task> getAllTasksForProjects(int projectID) {
        try {
            List<Task> tasks = taskRepository.getAllTasksForProjects(projectID);
            for (Task task : tasks) {
                task.setHourEstimate(hoursLeftOnTask(task.getTaskID()));
            }
            return tasks;
        } catch (DataAccessException exception) {
            throw new DatabaseOperationException("A fatal error has occurred, while attempting to access tasks for project, with id: " + projectID);
        }
    }

    public List<Task> getAllSubTasks(int taskID) {
        try {
            List<Task> tasks = taskRepository.getAllSubTasks(taskID);
            for (Task task : tasks) {
                task.setHourEstimate(hoursLeftOnTask(task.getTaskID()));
            }
            return tasks;
        } catch (DataAccessException exception) {
            throw new DatabaseOperationException("A fatal error has occurred, while attempting to access subtasks for task, with id:" + taskID);
        }
    }

    public List<Task> getAllTasksForAccount(int accountID) {
        try {
            return taskRepository.getAllTasksForAccount(accountID);
        } catch (DataAccessException exception) {
            throw new DatabaseOperationException("A fatal error has occurred, while attempting to access tasks assigned to account with id: " + accountID);
        }
    }

    public List<Account> getAllAccountsAssignedToTask(int taskID) {
        try {
            return taskRepository.getAllAccountsAssignedToTask(taskID);
        } catch (DataAccessException exception) {
            throw new DatabaseOperationException("A fatal error has occurred, while attempting to access accounts assigned to task, with id:" + taskID);
        }
    }

    public boolean assignAccountToTask(int accountID, int taskID) {
        try {
            return taskRepository.assignAccountToTask(accountID, taskID);
        } catch (DataIntegrityViolationException exception) {
            throw new DuplicateTasklistEntryException("an account with id (" + accountID + ") is already assigned a task with id (" + taskID + ")");
        }
    }

    public boolean addTask(Task task) {
        try {
            return taskRepository.addTask(task);
        } catch (DataIntegrityViolationException exception) {
            throw new DuplicateTaskException("A task of the chosen name (" + task.getName() + ") already exists in this project");
        } catch (DataAccessException e) {
            throw new DatabaseOperationException("A fatal error has occurred while attempting to create task");
        }
    }

    public Task updateTask(Task task) {
        try {
            return taskRepository.updateTask(task);
        } catch (DataIntegrityViolationException exception) {
            throw new DuplicateTaskException("A task of the chosen name (" + task.getName() + ") already exists in this project");
        } catch (DataAccessException exception) {
            throw new DatabaseOperationException("A fatal error has occurred while attempting to update task");
        }
    }

    public boolean deleteTask(int taskID) throws DataAccessException {
        try {
            return taskRepository.deleteTask(taskID);
        } catch (DataIntegrityViolationException exception) {
            throw new TaskNotFoundException("A task, with id (" + taskID + ") could not be found");
        } catch (DataAccessException exception) {
            throw new DatabaseOperationException("A fatal error has occurred while attempting to delete task");
        }
    }

    public int timeUntilDeadline(int taskID) {
        Task task = getTaskByID(taskID);
        return (int) ChronoUnit.DAYS.between(LocalDate.now(), task.getDeadLine().toLocalDate());
    }

    public int hoursLeftOnTask(int taskID) {
        Task task = taskRepository.getTaskByID(taskID);
        if (task.isCompleted()) {
            return 0;
        }
        List<Task> subTasks = getAllSubTasks(task.getTaskID());
        if (subTasks.isEmpty()) {
            return task.getHourEstimate();
        }
        return getHoursForSubTasks(subTasks);
    }

    private int getHoursForSubTasks(List<Task> subTasks) {
        int hours = 0;
        for (Task subTask : subTasks) {
            if (subTask.isCompleted()) {
            } else if (taskRepository.getAllSubTasks(subTask.getTaskID()).isEmpty()) {
                hours += subTask.getHourEstimate();
            } else {
                hours += getHoursForSubTasks(taskRepository.getAllSubTasks(subTask.getTaskID()));
            }
        }
        return hours;
    }

    public int percentOfProgressDone(int taskID) {
        if (getTaskByID(taskID).isCompleted()) {
            return 100;
        }
        List<Task> subTasks = getAllSubTasks(taskID);
        double completedTasks = 0;
        double notCompletedTasks = 0;
        for (Task subTask : subTasks) {
            if (subTask.isCompleted()) {
                completedTasks += 1;
            } else {
                notCompletedTasks += 1;
            }
        }
        if (completedTasks == 0) {
            return 0;
        }
        return (int) (completedTasks / (completedTasks + notCompletedTasks) * 100);
    }

    public boolean markAsDone(int taskID) {
        Task task = getTaskByID(taskID);
        if (!task.isCompleted()) {
            task.setCompleted(true);
            updateTask(task);
            return true;
        }
        task.setCompleted(false);
        updateTask(task);
        return false;
    }

}
