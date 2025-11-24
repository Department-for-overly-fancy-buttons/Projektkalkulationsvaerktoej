package ek.dfofb.projektkalukulationsvaerktoej.repository.interfaces;

import ek.dfofb.projektkalukulationsvaerktoej.model.Account;
import ek.dfofb.projektkalukulationsvaerktoej.model.Task;

import java.util.List;

public interface ITaskRepository {

    Task getTaskByID(int TaskID);

    List<Task> getAllTasksForProjects(int projectID);

    List<Task> getAllSubTasks(int TaskID);

    List<Task> getAllTasksForAccount(int accountID);

    List<Account> getAllAssignedToProject(int ProjectID);

}
