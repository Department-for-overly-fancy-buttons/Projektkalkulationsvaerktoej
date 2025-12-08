package ek.dfofb.projektkalkulationsvaerktoej.model;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class RoleModelTest
{


    @Test
    public void hasPermission_withIdAndEnumWorks()
    {
        Role role = new Role
                (
                        Arrays.asList(1, 3, 5),
                        "Test role",
                        "Tester",
                        99
                );

        assertTrue(role.hasPermission(1));
        assertTrue(role.hasPermission(Permission.VIEW_PROJECT));
        assertTrue(role.hasPermission(3));
        assertTrue(role.hasPermission(Permission.EDIT_TASKS));
        assertTrue(role.hasPermission(5));
        assertTrue(role.hasPermission(Permission.ADD_PROJECTS));

        assertFalse(role.hasPermission(7));
        assertFalse(role.hasPermission(Permission.GRANT_PERMISSIONS));


    }
}
