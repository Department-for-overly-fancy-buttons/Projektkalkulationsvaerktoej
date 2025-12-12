package ek.dfofb.projektkalkulationsvaerktoej.repository;

import ek.dfofb.projektkalkulationsvaerktoej.model.Account;
import ek.dfofb.projektkalkulationsvaerktoej.model.Task;
import ek.dfofb.projektkalkulationsvaerktoej.repository.interfaces.ITaskRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TaskRepository implements ITaskRepository {
    private final JdbcTemplate jdbcTemplate;

    public TaskRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Task getTaskByID(int taskID) throws DataAccessException {
        String sql = "SELECT * FROM Tasks where TaskID = ?";
        return jdbcTemplate.queryForObject(sql, new TaskRowMapper(), taskID);
    }

    @Override
    public List<Task> getAllTasksForProjects(int projectID) throws DataAccessException {
        String sql = "SELECT * FROM Tasks where ProjectID = ? AND ParentID = 0 ORDER BY Completed,Deadline,HourEstimate,Name";
        return jdbcTemplate.query(sql, new TaskRowMapper(), projectID);
    }

    @Override
    public List<Task> getAllSubTasks(int taskID) throws DataAccessException {
        String sql = "SELECT * FROM Tasks where ParentID = ? ORDER BY Completed,Deadline,HourEstimate,Name";
        return jdbcTemplate.query(sql, new TaskRowMapper(), taskID);
    }

    @Override
    public List<Task> getAllTasksForAccount(int accountID) throws DataAccessException {
        String sql = "select * from tasks join TaskList " +
                "on tasks.TaskID=TaskList.TaskID where TaskList.AccountID = ? " +
                "ORDER BY tasks.Completed,tasks.Deadline,tasks.HourEstimate,tasks.Name";
        return jdbcTemplate.query(sql, new TaskRowMapper(), accountID);
    }

    @Override
    public List<Account> getAllAccountsAssignedToTask(int taskID) throws DataAccessException {
        String sql = "select * from Accounts join TaskList " +
                "on Accounts.AccountID=TaskList.AccountID where TaskList.TaskID = ? " +
                "ORDER BY Accounts.name";
        return jdbcTemplate.query(sql, new AccountRowMapper(), taskID);
    }

    @Override
    public boolean assignAccountToTask(int accountID, int taskID) throws DataAccessException {
        String sql = "INSERT INTO taskList (AccountID,TaskID) VALUES(?,?)";
        int rowsInserted = jdbcTemplate.update(sql, accountID, taskID);
        return rowsInserted == 1;
    }

    @Override
    public boolean removeAccountFromTask(int accountID, int taskID) throws DataAccessException {
        String sql = "DELETE FROM taskList WHERE AccountID = ? and TaskID = ?";
        int deletedRows = jdbcTemplate.update(sql, accountID, taskID);
        return deletedRows != 0;
    }

    @Override
    public boolean addTask(Task task) throws DataAccessException {
        String sql = "INSERT INTO Tasks (Name, Description, HourEstimate, Completed," +
                "  Deadline, StartDate, ProjectID, ParentID) VALUES (?,?,?,?,?,?,?,?)";
        int rowsInserted = jdbcTemplate.update(sql, task.getName(), task.getDescription(), task.getHourEstimate(),
                task.getIsCompleted(), task.getDeadLine(), task.getStartDate(),
                task.getProjectID(), task.getParentID());
        return rowsInserted == 1;
    }

    @Override
    public Task updateTask(Task task) throws DataAccessException {
        String sql = "UPDATE tasks SET Name=?, Description=?, HourEstimate=?, Completed=?, " +
                "Deadline=?, StartDate=? WHERE TaskID = ?";
        jdbcTemplate.update(sql, task.getName(), task.getDescription(), task.getHourEstimate(),
                task.getIsCompleted(), task.getDeadLine(), task.getStartDate(), task.getTaskID());
        return task;
    }

    @Override
    public Task completeTask(Task task) throws DataAccessException {
        String sql = "UPDATE tasks SET Completed=?,HoursSpent=? WHERE TaskID = ?";
        jdbcTemplate.update(sql, task.getIsCompleted(), task.getHoursSpentOnTask(), task.getTaskID());
        return task;
    }

    @Override
    public boolean deleteTask(int taskID) throws DataAccessException {
        String sql = "DELETE FROM tasks where TaskID = ?";
        int deletedRows = jdbcTemplate.update(sql, taskID);
        return deletedRows != 0;
    }
}
