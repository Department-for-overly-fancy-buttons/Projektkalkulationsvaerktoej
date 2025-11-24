package ek.dfofb.projektkalukulationsvaerktoej.repository.interfaces;

import ek.dfofb.projektkalukulationsvaerktoej.model.Role;

import java.util.List;

public interface IRoleRepository {

    Role getRoleFromID(int RoleID);

    List<Role> getAllRoles();

}
