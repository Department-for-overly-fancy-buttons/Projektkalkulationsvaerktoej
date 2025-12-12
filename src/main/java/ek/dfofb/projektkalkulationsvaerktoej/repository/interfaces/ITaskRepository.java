package ek.dfofb.projektkalkulationsvaerktoej.repository.interfaces;

import ek.dfofb.projektkalkulationsvaerktoej.model.Account;
import ek.dfofb.projektkalkulationsvaerktoej.model.Task;

import java.util.List;

public interface ITaskRepository {

    Task getTaskByID(int taskID);

    List<Task> getAllTasksForProjects(int projectID);

    List<Task> getAllSubTasks(int taskID);

    List<Task> getAllTasksForAccount(int accountID);

    List<Account> getAllAccountsAssignedToTask(int taskID);

    boolean assignAccountToTask(int accountID,int taskID);

    boolean removeAccountFromTask(int accountID,int taskID);

    boolean addTask(Task task);

    Task updateTask(Task task);

    Task completeTask(Task task);

    boolean deleteTask(int taskID);

}
