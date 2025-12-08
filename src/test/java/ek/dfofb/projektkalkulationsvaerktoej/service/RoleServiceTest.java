package ek.dfofb.projektkalkulationsvaerktoej.service;

import ek.dfofb.projektkalkulationsvaerktoej.model.Role;
import ek.dfofb.projektkalkulationsvaerktoej.repository.interfaces.IRoleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RoleServiceTest
{

    @Mock
    private IRoleRepository roleRepository;

    @InjectMocks
    private RoleService roleService;

    @Test
    public void getRoleFromID_sendsToRepository()
    {
        Role role = new Role(Collections.emptyList(), "Test", "Test", 1);
        when(roleRepository.getRoleFromID(1)).thenReturn(role);

        Role result = roleService.getRoleFromID(1);

        assertSame(role, result);
        verify(roleRepository).getRoleFromID(1);
    }

    @Test
    public void deleteRole_sendsToRepository()
    {
        when(roleRepository.deleteRole(1)).thenReturn(true);

        boolean result = roleService.deleteRole(1);

        assertTrue(result);
        verify(roleRepository).deleteRole(1);
    }
}
