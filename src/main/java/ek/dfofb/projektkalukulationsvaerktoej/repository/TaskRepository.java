package ek.dfofb.projektkalukulationsvaerktoej.repository;

import ek.dfofb.projektkalukulationsvaerktoej.model.Account;
import ek.dfofb.projektkalukulationsvaerktoej.model.Task;
import ek.dfofb.projektkalukulationsvaerktoej.repository.interfaces.ITaskRepository;

import java.util.List;

public class TaskRepository implements ITaskRepository {
    @Override
    public Task getTaskByID(int TaskID) {
        return null;
    }

    @Override
    public List<Task> getAllTasksForProjects(int projectID) {
        return List.of();
    }

    @Override
    public List<Task> getAllSubTasks(int TaskID) {
        return List.of();
    }

    @Override
    public List<Task> getAllTasksForAccount(int accountID) {
        return List.of();
    }

    @Override
    public List<Account> getAllAssignedToProject(int ProjectID) {
        return List.of();
    }
}
