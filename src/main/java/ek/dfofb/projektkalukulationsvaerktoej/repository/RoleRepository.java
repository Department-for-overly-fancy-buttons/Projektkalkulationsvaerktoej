package ek.dfofb.projektkalukulationsvaerktoej.repository;

import ek.dfofb.projektkalukulationsvaerktoej.model.Role;
import ek.dfofb.projektkalukulationsvaerktoej.repository.interfaces.IRoleRepository;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class RoleRepository implements IRoleRepository {

    private final JdbcTemplate jdbcTemplate;

    public RoleRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Role getRoleFromID(int RoleID) {
        return null;
    }

    @Override
    public List<Role> getAllRoles() {
        return List.of();
    }
}
