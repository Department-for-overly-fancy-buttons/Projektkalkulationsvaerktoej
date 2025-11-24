package ek.dfofb.projektkalukulationsvaerktoej.repository;

import ek.dfofb.projektkalukulationsvaerktoej.model.Account;
import ek.dfofb.projektkalukulationsvaerktoej.model.Project;
import ek.dfofb.projektkalukulationsvaerktoej.repository.interfaces.IProjectRepository;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class ProjectRepository implements IProjectRepository {
    private final JdbcTemplate jdbcTemplate;

    public ProjectRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Project getProjectByID(int ProjectID) {
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
    public List<Account> getAllAssignedToProject(int ProjectID) {
        return List.of();
    }
}
