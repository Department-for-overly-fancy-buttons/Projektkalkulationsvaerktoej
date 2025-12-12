package ek.dfofb.projektkalkulationsvaerktoej.repository.interfaces;

import ek.dfofb.projektkalkulationsvaerktoej.model.Account;
import ek.dfofb.projektkalkulationsvaerktoej.model.Project;

import java.util.List;

public interface IProjectRepository {

    Project getProjectByID(int projectID);

    List<Project> getAllProjects();

    List<Project> getAllProjectsForAccount(int accountID);

    List<Account> getAllAssignedToProject(int projectID);

    boolean assignAccountToProject(int accountID, int projectID);

    boolean addProject(Project project);

    Project updateProject(Project project);

    boolean deleteProject(int projectID);

}
