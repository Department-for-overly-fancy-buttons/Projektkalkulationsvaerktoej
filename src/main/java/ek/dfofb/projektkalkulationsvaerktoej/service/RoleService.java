package ek.dfofb.projektkalkulationsvaerktoej.service;

import ek.dfofb.projektkalkulationsvaerktoej.model.Role;
import ek.dfofb.projektkalkulationsvaerktoej.repository.interfaces.IRoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService
{
    private final IRoleRepository roleRepository;

    public RoleService(IRoleRepository roleRepository)
    {
        this.roleRepository = roleRepository;
    }

    public Role getRoleFromID(int roleID)
    {
        return roleRepository.getRoleFromID(roleID);
    }

    public List<Role> getAllRoles()
    {
        return roleRepository.getAllRoles();
    }

    public boolean addRole(Role role)
    {
        return roleRepository.addRole(role);
    }

    public Role updateRole(Role role)
    {
        return roleRepository.updateRole(role);
    }

    public boolean deleteRole(int roleID)
    {
        return roleRepository.deleteRole(roleID);
    }




}
