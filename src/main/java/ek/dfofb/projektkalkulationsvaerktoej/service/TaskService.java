package ek.dfofb.projektkalkulationsvaerktoej.service;

import ek.dfofb.projektkalkulationsvaerktoej.model.Account;
import ek.dfofb.projektkalkulationsvaerktoej.model.Task;
import ek.dfofb.projektkalkulationsvaerktoej.repository.interfaces.ITaskRepository;
import org.springframework.dao.DataAccessException;
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
        return taskRepository.getTaskByID(taskID);
    }

    public List<Task> getAllTasksForProjects(int projectID) {
        return taskRepository.getAllTasksForProjects(projectID);
    }

    public List<Task> getAllSubTasks(int taskID) {
        return taskRepository.getAllSubTasks(taskID);
    }

    public List<Task> getAllTasksForAccount(int accountID) {
        return taskRepository.getAllTasksForAccount(accountID);
    }

    public List<Account> getAllAccountsAssignedToTask(int taskID) {
        return taskRepository.getAllAccountsAssignedToTask(taskID);
    }

    public boolean assignAccountToTask(int accountID, int taskID) {
        return taskRepository.assignAccountToTask(accountID, taskID);
    }

    public boolean addTask(Task task) {
        return taskRepository.addTask(task);
    }

    public Task updateTask(Task task) {
        return taskRepository.updateTask(task);
    }

    public boolean deleteTask(int taskID) throws DataAccessException {
        return taskRepository.deleteTask(taskID);
    }

    public int timeUntilDeadline(int taskID) {
        Task task = getTaskByID(taskID);
        return (int)ChronoUnit.DAYS.between(LocalDate.now(),task.getDeadLine().toLocalDate());
    }

    public int hoursLeftOnTask(int taskID) {
        Task task = getTaskByID(taskID);
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
            } else if (getAllSubTasks(subTask.getTaskID()).isEmpty()) {
                hours += subTask.getHourEstimate();
            } else {
                hours += getHoursForSubTasks(getAllSubTasks(subTask.getTaskID()));
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

}
