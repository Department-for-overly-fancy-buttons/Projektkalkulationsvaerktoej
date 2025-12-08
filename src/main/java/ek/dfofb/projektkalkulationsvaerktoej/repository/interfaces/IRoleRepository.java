package ek.dfofb.projektkalkulationsvaerktoej.repository.interfaces;

import ek.dfofb.projektkalkulationsvaerktoej.model.Role;

import java.util.List;

public interface IRoleRepository {

    Role getRoleFromID(int roleID);

    List<Role> getAllRoles();

    boolean addRole(Role role);

    Role updateRole(Role role);

    boolean deleteRole(int roleID);
}
