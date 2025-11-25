package ek.dfofb.projektkalukulationsvaerktoej.repository.interfaces;

import ek.dfofb.projektkalukulationsvaerktoej.model.Account;
import ek.dfofb.projektkalukulationsvaerktoej.model.Task;

import java.util.List;
import java.util.Set;

public interface ITaskRepository {

    Task getTaskByID(int taskID);

    Set<Task> getAllTasksForProjects(int projectID);

    List<Task> getAllSubTasks(int taskID);

    List<Task> getAllTasksForAccount(int accountID);

    List<Account> getAllAssignedToProject(int projectID);

}
