package ek.dfofb.projektkalkulationsvaerktoej.service;

import ek.dfofb.projektkalkulationsvaerktoej.model.Account;
import ek.dfofb.projektkalkulationsvaerktoej.model.Permission;
import ek.dfofb.projektkalkulationsvaerktoej.model.Role;
import org.springframework.stereotype.Service;



@Service
public class AuthorizationService
{

    private final RoleService roleService;

    public AuthorizationService(RoleService roleService)
    {
        this.roleService = roleService;
    }

    public boolean hasPermission(Account account, Permission permission)
    {
        if (account == null || permission == null)
        {
            return false;
        }

        Role role = roleService.getRoleFromID(account.getRoleID());
        if (role == null)
        {
            return false;
        }
        return role.hasPermission(permission);
    }

    // Denne her metode gør det samme, men er bare lidt mere "convinient" ift. hvis man kun har roleID
    public boolean hasPermission(int roleID, Permission permission)
    {
        Role role = roleService.getRoleFromID(roleID);
        if (role == null || permission == null)
        {
            return false;
        }
        return role.hasPermission(permission);
    }

}
