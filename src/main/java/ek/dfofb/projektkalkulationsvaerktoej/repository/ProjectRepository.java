package ek.dfofb.projektkalkulationsvaerktoej.repository;

import ek.dfofb.projektkalkulationsvaerktoej.model.Account;
import ek.dfofb.projektkalkulationsvaerktoej.model.Project;
import ek.dfofb.projektkalkulationsvaerktoej.repository.interfaces.IProjectRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class ProjectRepository implements IProjectRepository {
    private final JdbcTemplate jdbcTemplate;

    public ProjectRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Project getProjectByID(int projectID) {
        return null;
    }

    @Override
    public List<Project> getAllProjects() {
        return List.of();
    }

    @Override
    public List<Project> getAllProjectsForAccount(int accountID) {
        return List.of();
    }

    @Override
    public List<Account> getAllAssignedToProject(int projectID) {
        return List.of();
    }

    @Override
    public boolean addProject(Project project) {
        return false;
    }

    @Override
    public Project updateProject(Project project) {
        return null;
    }
}
