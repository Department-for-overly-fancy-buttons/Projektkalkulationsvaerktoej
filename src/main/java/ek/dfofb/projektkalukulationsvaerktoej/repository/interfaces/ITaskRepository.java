package ek.dfofb.projektkalukulationsvaerktoej.repository.interfaces;

import ek.dfofb.projektkalukulationsvaerktoej.model.Account;
import ek.dfofb.projektkalukulationsvaerktoej.model.Task;

import java.util.List;
import java.util.Set;

public interface ITaskRepository {

    Task getTaskByID(int taskID);

    Set<Task> getAllTasksForProjects(int projectID);

    Set<Task> getAllSubTasks(int taskID);

    Set<Task> getAllTasksForAccount(int accountID);

    Set<Account> getAllAssignedToTask(int taskID);

    boolean addTask(Task task);

    Task updateTask(Task task);

    boolean deleteTask(int taskID);

}
