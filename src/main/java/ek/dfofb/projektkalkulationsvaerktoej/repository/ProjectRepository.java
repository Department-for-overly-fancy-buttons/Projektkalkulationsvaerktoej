package ek.dfofb.projektkalkulationsvaerktoej.repository;

import ek.dfofb.projektkalkulationsvaerktoej.model.Account;
import ek.dfofb.projektkalkulationsvaerktoej.model.Project;
import ek.dfofb.projektkalkulationsvaerktoej.repository.interfaces.IProjectRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class ProjectRepository implements IProjectRepository {

    private final JdbcTemplate jdbcTemplate;

    public ProjectRepository(JdbcTemplate jdbcTemplate)
    {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Project getProjectByID(int projectID) throws DataAccessException
    {
        String sql = "SELECT * FROM Projects WHERE ProjectID = ?";
        return jdbcTemplate.queryForObject(sql, new ProjectRowMapper(), projectID);
    }

    @Override
    public List<Project> getAllProjects() throws DataAccessException
    {
        String sql = "SELECT * FROM Projects";
        return jdbcTemplate.query(sql, new ProjectRowMapper());
    }

    @Override
    public List<Project> getAllProjectsForAccount(int accountID) throws DataAccessException
    {
        String sql = "SELECT p.* FROM Projects p " +
                "JOIN ProjectMembers pm ON p.ProjectID = pm.ProjectID " +
                "WHERE pm.AccountID = ?";
        return jdbcTemplate.query(sql, new ProjectRowMapper(), accountID);
    }

    @Override
    public List<Account> getAllAssignedToProject(int projectID) throws DataAccessException
    {
        String sql = "SELECT a.* FROM Accounts a " +
                "JOIN ProjectMembers pm ON a.AccountID = pm.AccountID " +
                "WHERE pm.ProjectID = ?";
        return jdbcTemplate.query(sql, new AccountRowMapper(), projectID);
    }

    @Override
    public boolean assignAccountToProject(int accountID, int projectID) throws DataAccessException {
        String sql = "INSERT INTO ProjectMembers (AccountID,ProjectID) VALUES(?,?)";
        int rowsInserted = jdbcTemplate.update(sql, accountID, projectID);
        return rowsInserted == 1;
    }

    @Override
    public boolean removeAccountFromProject(int accountID, int projectID) throws DataAccessException {
        String sql = "DELETE FROM ProjectMembers where AccountID = ? and ProjectID = ?";
        int deletedRows = jdbcTemplate.update(sql, accountID, projectID);
        return deletedRows != 0;
    }

    @Override
    public boolean addProject(Project project) throws DataAccessException
    {
        String sql = "INSERT INTO Projects (Name, Description, IsActive, StartDate, Deadline) " +
                "VALUES (?, ?, ?, ?, ?)";
        int rows = jdbcTemplate.update
                (
                sql,
                project.getName(),
                project.getDescription(),
                project.getIsActive(),
                project.getStartDate(),
                project.getDeadline()
                );
        return rows == 1;
    }

    @Override
    public Project updateProject(Project project) throws DataAccessException
    {
        String sql = "UPDATE Projects SET Name = ?, Description = ?, IsActive = ?, " +
                "StartDate = ?, Deadline = ? WHERE ProjectID = ?";
        jdbcTemplate.update
                (
                sql,
                project.getName(),
                project.getDescription(),
                project.getIsActive(),
                project.getStartDate(),
                project.getDeadline(),
                project.getProjectID()
                );
        return project;
    }

    @Override
    public boolean deleteProject(int projectID) throws DataAccessException{
        String sql = "DELETE FROM Projects where ProjectID = ?";
        int deletedRows = jdbcTemplate.update(sql, projectID);
        return deletedRows != 0;
    }
}
