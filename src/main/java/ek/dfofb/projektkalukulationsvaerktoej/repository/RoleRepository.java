package ek.dfofb.projektkalukulationsvaerktoej.repository;

import ek.dfofb.projektkalukulationsvaerktoej.model.Role;
import ek.dfofb.projektkalukulationsvaerktoej.repository.interfaces.IRoleRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class RoleRepository implements IRoleRepository {

    private final JdbcTemplate jdbcTemplate;

    public RoleRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Role getRoleFromID(int roleID) {
        return null;
    }

    @Override
    public List<Role> getAllRoles() {
        return List.of();
    }
}
