package ek.dfofb.projektkalukulationsvaerktoej.repository;

import ek.dfofb.projektkalukulationsvaerktoej.model.Account;
import org.springframework.jdbc.core.RowMapper;

import javax.swing.tree.TreePath;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountRowMapper implements RowMapper<Account> {

    @Override
    public Account mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Account(rs.getInt("AccountID"), rs.getString("Name"), rs.getString("Email"),
                rs.getDate("Birthday"), rs.getString("Number"), rs.getInt("WeeklyHours"),
                rs.getString("Password"));
    }
}
