package ek.dfofb.projektkalkulationsvaerktoej.repository;

import ek.dfofb.projektkalkulationsvaerktoej.model.Role;
import ek.dfofb.projektkalkulationsvaerktoej.repository.interfaces.IRoleRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
@Repository
public class RoleRepository implements IRoleRepository
{

    private final JdbcTemplate jdbcTemplate;

    public RoleRepository(JdbcTemplate jdbcTemplate)
    {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public Role getRoleFromID(int roleID)
    {
        String sqlRole = "SELECT RoleID, Name, Description FROM Roles WHERE RoleID = ?";

        return jdbcTemplate.queryForObject(sqlRole, (rs, rowNum) ->
                {
                    int id = rs.getInt("RoleID");
                    String name = rs.getString("Name");
                    String description = rs.getString("Description");

                    List<Integer> permissions = getPermissionIdsForRole(id);

                    return new Role(permissions, description, name, id);
                }, roleID);
    }

    private List<Integer> getPermissionIdsForRole(int roleID)
    {
        String sql = "SELECT PermissionID FROM RolePermissions WHERE RoleID = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> rs.getInt("PermissionID"), roleID);
    }

    @Override
    public List<Role> getAllRoles()
    {
        String sql = "SELECT RoleID, Name, Description FROM Roles ORDER BY Name";

        return jdbcTemplate.query(sql, (rs, rowNum) ->
        {
            int id = rs.getInt("RoleID");
            String name = rs.getString("Name");
            String description = rs.getString("Description");
            List<Integer> permissions = getPermissionIdsForRole(id);
            return new Role(permissions, description, name, id);
        });
    }

    @Override
    public boolean addRole(Role role)
    {
        String sql = "INSERT INTO Roles (Name, Description) VALUES (?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        int rows = jdbcTemplate.update(connection ->
        {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, role.getName());
            ps.setString(2, role.getDescription());
            return ps;
        }, keyHolder);

        if (rows != 1)
        {
            return false;
        }

        int newRoleId = keyHolder.getKey().intValue();

        if (role.getPermissionList() != null)
        {
            for (Integer permId : role.getPermissionList())
            {
                jdbcTemplate.update(
                        "INSERT INTO RolePermissions (RoleID, PermissionID) VALUES (?, ?)",
                        newRoleId, permId
                );
            }
        }
        return true;
    }

    @Override
    public boolean deleteRole(int RoleID)
    {
        String sql = "DELETE FROM Roles WHERE RoleID = ?";
        int rows = jdbcTemplate.update(sql, RoleID);
        return rows == 1;
    }

    @Override
    @Transactional
    public Role updateRole(Role role)
    {
        String sql = "UPDATE Roles SET Name = ?, Description = ? WHERE RoleID = ?";
        jdbcTemplate.update(sql, role.getName(), role.getDescription(), role.getRoleID());

        jdbcTemplate.update("DELETE FROM RolePermissions WHERE RoleID = ?", role.getRoleID());

        if (role.getPermissionList() != null)
        {
            for (Integer permId : role.getPermissionList())
            {
                jdbcTemplate.update(
                        "INSERT INTO RolePermissions (RoleID, PermissionID) VALUES (?, ?)", role.getRoleID(), permId
                );
            }
        }
        return role;
    }
}
