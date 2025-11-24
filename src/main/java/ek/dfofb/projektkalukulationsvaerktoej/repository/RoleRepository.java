package ek.dfofb.projektkalukulationsvaerktoej.repository;

import ek.dfofb.projektkalukulationsvaerktoej.model.Role;
import ek.dfofb.projektkalukulationsvaerktoej.repository.interfaces.IRoleRepository;

import java.util.List;

public class RoleRepository implements IRoleRepository {
    @Override
    public Role getRoleFromID(int RoleID) {
        return null;
    }

    @Override
    public List<Role> getAllRoles() {
        return List.of();
    }
}
