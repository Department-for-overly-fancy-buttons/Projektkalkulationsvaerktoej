package ek.dfofb.projektkalukulationsvaerktoej.repository;

import ek.dfofb.projektkalukulationsvaerktoej.model.Account;
import ek.dfofb.projektkalukulationsvaerktoej.model.Project;
import ek.dfofb.projektkalukulationsvaerktoej.repository.interfaces.IProjectRepository;

import java.util.List;

public class ProjectRepository implements IProjectRepository
{
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
