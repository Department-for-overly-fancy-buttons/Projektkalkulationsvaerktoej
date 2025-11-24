package ek.dfofb.projektkalukulationsvaerktoej.repository.interfaces;

import ek.dfofb.projektkalukulationsvaerktoej.model.Account;
import ek.dfofb.projektkalukulationsvaerktoej.model.Project;

import java.util.List;

public interface IProjectRepository {

    Project getProjectByID(int ProjectID);

    List<Project> getAllProjects();

    List<Project> getAllProjectsForAccount(int accountID);

    List<Account> getAllAssignedToProject(int ProjectID);

}
