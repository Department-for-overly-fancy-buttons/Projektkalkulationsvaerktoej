package ek.dfofb.projektkalukulationsvaerktoej.repository.interfaces;

import ek.dfofb.projektkalukulationsvaerktoej.model.Account;
import ek.dfofb.projektkalukulationsvaerktoej.model.Project;

import java.util.List;

public interface IProjectRepository {

    Project getProjectByID(int projectID);

    List<Project> getAllProjects();

    List<Project> getAllProjectsForAccount(int accountID);

    List<Account> getAllAssignedToProject(int projectID);

    boolean addProject(Project project);

    Project updateProject(Project project);

}
