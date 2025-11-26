package ek.dfofb.projektkalukulationsvaerktoej.repository.interfaces;

import ek.dfofb.projektkalukulationsvaerktoej.model.Account;
import ek.dfofb.projektkalukulationsvaerktoej.model.Task;

import java.util.List;
import java.util.Set;

public interface ITaskRepository {

    Task getTaskByID(int taskID);

    List<Task> getAllTasksForProjects(int projectID);

    List<Task> getAllSubTasks(int taskID);

    List<Task> getAllTasksForAccount(int accountID);

    List<Account> getAllAssignedToTask(int taskID);

    boolean assignAccountToTask(int accountID,int taskID);

    boolean addTask(Task task);

    Task updateTask(Task task);

    boolean deleteTask(int taskID);

}
