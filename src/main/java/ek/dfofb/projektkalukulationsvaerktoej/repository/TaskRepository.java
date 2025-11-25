package ek.dfofb.projektkalukulationsvaerktoej.repository;

import ek.dfofb.projektkalukulationsvaerktoej.model.Account;
import ek.dfofb.projektkalukulationsvaerktoej.model.Task;
import ek.dfofb.projektkalukulationsvaerktoej.repository.interfaces.ITaskRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

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
        List<Task> tasks = jdbcTemplate.query(sql,new TaskRowMapper(),projectID);
        Set<Task> taskSet = new HashSet<>();
        for(Task task: tasks){
            taskSet.add(task);
        }
        return taskSet;
    }

    @Override
    public Set<Task> getAllSubTasks(int taskID) {
        return Set.of();
    }

    @Override
    public Set<Task> getAllTasksForAccount(int accountID) {
        return Set.of();
    }

    @Override
    public Set<Account> getAllAssignedToProject(int projectID) {
        return Set.of();
    }
}
