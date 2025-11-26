package ek.dfofb.projektkalukulationsvaerktoej.repository;

import ek.dfofb.projektkalukulationsvaerktoej.model.Account;
import ek.dfofb.projektkalukulationsvaerktoej.model.Task;
import ek.dfofb.projektkalukulationsvaerktoej.repository.interfaces.ITaskRepository;
import org.springframework.dao.DataAccessException;
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
    public Task getTaskByID(int taskID) throws DataAccessException{
        String sql = "SELECT * FROM Tasks where TaskID = ?";
        Task task = jdbcTemplate.queryForObject(sql, new TaskRowMapper(), taskID);
        return task;
    }

    @Override
    public Set<Task> getAllTasksForProjects(int projectID) throws DataAccessException{
        String sql = "SELECT * FROM Tasks where ProjectID = ?";
        List<Task> tasks = jdbcTemplate.query(sql, new TaskRowMapper(), projectID);
        Set<Task> taskSet = new HashSet<>();
        for (Task task : tasks) {
            taskSet.add(task);
        }
        return taskSet;
    }

    @Override
    public Set<Task> getAllSubTasks(int taskID) throws DataAccessException{
        String sql = "SELECT * FROM Tasks where ParentID = ?";
        List<Task> tasks = jdbcTemplate.query(sql, new TaskRowMapper(), taskID);
        Set<Task> taskSet = new HashSet<>();
        for (Task task : tasks) {
            taskSet.add(task);
        }
        return taskSet;
    }

    @Override
    public Set<Task> getAllTasksForAccount(int accountID) throws DataAccessException {
        return Set.of();
    }

    @Override
    public Set<Account> getAllAssignedToTask(int taskID) throws DataAccessException{
        return Set.of();
    }

    @Override
    public boolean addTask(Task task) throws DataAccessException {
        String sql = "INSERT INTO Tasks (Name, Description, HourEstimate, Completed," +
                "  Deadline, StartDate, ProjectID, ParentID) VALUES (?,?,?,?,?,?,?,?)";
        int rowsInserted = jdbcTemplate.update(sql, task.getName(), task.getDescription(), task.getHourEstimate(),
                task.isCompleted(), task.getDeadLine(), task.getStartDate(),
                task.getProjectID(), task.getParentID());
        if (rowsInserted == 1) {
            return true;
        }
        return false;
    }

    @Override
    public Task updateTask(Task task) throws DataAccessException{
        String sql = "UPDATE tasks SET Name=?, Description=?, HourEstimate=?, Completed=?, " +
                "Deadline=?, StartDate=? WHERE TaskID = ?";
        jdbcTemplate.update(sql,task.getName(),task.getDescription(),task.getHourEstimate(),
                task.isCompleted(),task.getDeadLine(),task.getStartDate(),task.getTaskID());
        return task;
    }

    @Override
    public boolean deleteTask(int taskID) throws  DataAccessException{
        String sql = "DELETE FROM tasks where TaskID = ?";
        int deletedRows = jdbcTemplate.update(sql,taskID);
        if(deletedRows == 0){
            return false;
        }
        return true;
    }
}
