package ek.dfofb.projektkalkulationsvaerktoej.repository;

import ek.dfofb.projektkalkulationsvaerktoej.model.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@SpringBootTest
@ActiveProfiles("test")
@Sql(scripts = "classpath:h2init.sql", executionPhase = BEFORE_TEST_METHOD)
public class RoleRepositoryTest
{

    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void getRoleFromID_returnsRoleWithPermissions()
    {
        Role role = roleRepository.getRoleFromID(1);

        assertThat(role).isNotNull();
        assertThat(role.getName()).isEqualTo("Scrum master");
        assertThat(role.getPermissionList()).containsExactly(1);
    }

    @Test
    void getAllRoles_returnsAtleastOneRole()
    {
        List<Role> roles = roleRepository.getAllRoles();

        assertThat(roles).isNotEmpty();
        assertThat(roles.stream().anyMatch(r -> r.getName().equals("Scrum master"))).isTrue();
    }

    @Test
    void addRole_insertsRoleAndPermissions()
    {
        Role newRole = new Role
                (
                        Arrays.asList(1),
                        "Test role",
                        "Tester",
                        0
                );

        boolean inserted = roleRepository.addRole(newRole);

        assertThat(inserted).isTrue();

        List<Role> roles = roleRepository.getAllRoles();
        assertThat(roles.stream().anyMatch(r -> "Tester".equals(r.getName()))).isTrue();
    }

    @Test
    void updateRole_updatesNameAndPermissions()
    {
        Role role = roleRepository.getRoleFromID(1);
        role.setName("Scrum updated");
        role.setPermissionList(Arrays.asList(1));

        Role updated = roleRepository.updateRole(role);

        assertThat(updated.getName()).isEqualTo("Scrum updated");

        Role fromDb = roleRepository.getRoleFromID(1);
        assertThat(fromDb.getName()).isEqualTo("Scrum updated");
    }

    @Test
    public void deleteRole_removesRole()
    {
        Role role = new Role(Arrays.asList(1), "ToDelete", "ToDelete", 0);
        boolean inserted = roleRepository.addRole(role);
        assertThat(inserted).isTrue();

        List<Role> rolesBefore = roleRepository.getAllRoles();
        int sizeBefore = rolesBefore.size();

        int newRoleId = rolesBefore.stream()
                .filter(r -> "ToDelete".equals(r.getName()))
                .findFirst()
                .map(Role::getRoleID)
                .orElseThrow();

        boolean deleted = roleRepository.deleteRole(newRoleId);

        assertThat(deleted).isTrue();
        assertThat(roleRepository.getAllRoles().size()).isEqualTo(sizeBefore - 1);
    }
}
