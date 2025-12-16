package ek.dfofb.projektkalkulationsvaerktoej.service;

import ek.dfofb.projektkalkulationsvaerktoej.model.Account;
import ek.dfofb.projektkalkulationsvaerktoej.model.Permission;
import ek.dfofb.projektkalkulationsvaerktoej.model.Role;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthorizationServiceTest
{
    @Mock
    private RoleService roleService;

    @InjectMocks
    private AuthorizationService authorizationService;

    @Test
    public void hasPermission_returnsFalseWhenAccountNull()
    {
        assertFalse(authorizationService.hasPermission(null, Permission.VIEW_PROJECT));
    }

    @Test
    public void hasPermission_returnsFalseWhenRoleNotFound()
    {
        Account account = new Account
                (
                        1,
                        "Test",
                        "t@test.dk",
                        new Date(System.currentTimeMillis()),
                        "12345678",
                        37,
                        "pwd",
                        99
                );

        when(roleService.getRoleFromID(99)).thenReturn(null);

        assertFalse(authorizationService.hasPermission(account.getRoleID(), Permission.VIEW_PROJECT));
    }

    @Test
    public void hasPermission_usesRolePermissions()
    {
        Account account = new Account
                (
                        1,
                        "Test",
                        "t@test.dk",
                        new Date(System.currentTimeMillis()),
                        "12345678",
                        37,
                        "pwd",
                        1
                );

        Role role = new Role
                (
                        Arrays.asList(Permission.ADD_PROJECTS.getId()),
                        "Test role",
                        "Tester",
                        1
                );

        when(roleService.getRoleFromID(1)).thenReturn(role);

        assertTrue(authorizationService.hasPermission(account, Permission.ADD_PROJECTS));
        assertFalse(authorizationService.hasPermission(account, Permission.DELETE_PROJECTS));
    }
}
