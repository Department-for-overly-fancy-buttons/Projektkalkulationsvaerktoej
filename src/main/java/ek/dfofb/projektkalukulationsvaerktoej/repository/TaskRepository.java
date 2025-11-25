package ek.dfofb.projektkalukulationsvaerktoej.repository;

import ek.dfofb.projektkalukulationsvaerktoej.model.Account;
import ek.dfofb.projektkalukulationsvaerktoej.model.Task;
import ek.dfofb.projektkalukulationsvaerktoej.repository.interfaces.ITaskRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public class TaskRepository implements ITaskRepository {
    private final JdbcTemplate jdbcTemplate;

    public TaskRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Task getTaskByID(int taskID) {
        String sql = "SELECT * FROM Tasks where TaskID = ?";
        List<Task> task =jdbcTemplate.query(sql,new TaskRowMapper(),taskID);
        return task.get(0);
    }

    @Override
    public Set<Task> getAllTasksForProjects(int projectID) {
        String sql = "SELECT * FROM Tasks where ProjectID = ?";
        Set<Task> tasks = (Set<Task>) jdbcTemplate.query(sql,new TaskRowMapper(),projectID);
        return tasks;
    }

    @Override
    public List<Task> getAllSubTasks(int taskID) {
        return List.of();
    }

    @Override
    public List<Task> getAllTasksForAccount(int accountID) {
        return List.of();
    }

    @Override
    public List<Account> getAllAssignedToProject(int projectID) {
        return List.of();
    }
}
