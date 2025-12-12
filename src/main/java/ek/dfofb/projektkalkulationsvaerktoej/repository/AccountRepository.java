package ek.dfofb.projektkalkulationsvaerktoej.repository;

import ek.dfofb.projektkalkulationsvaerktoej.model.Account;
import ek.dfofb.projektkalkulationsvaerktoej.repository.interfaces.IAccountRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AccountRepository implements IAccountRepository {
    private final JdbcTemplate jdbcTemplate;

    public AccountRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Account getAccountFromID(int accountID) throws DataAccessException {
        String sql = "SELECT * FROM Accounts where AccountID = ?";
        return jdbcTemplate.queryForObject(sql, new AccountRowMapper(), accountID);
    }

    @Override
    public List<Account> getAllAccounts() throws DataAccessException {
        String sql = "SELECT * FROM Accounts";
        return jdbcTemplate.query(sql, new AccountRowMapper());
    }

    @Override
    public List<Account> getAllAccountsWithRoleID(int roleID) {
        String sql = "SELECT * FROM Accounts WHERE RoleID = ?";
        return jdbcTemplate.query(sql, new AccountRowMapper(), roleID);
    }

    @Override
    public boolean addAccount(Account account) throws DataAccessException {
        String sql = "INSERT INTO Accounts(Name, Email, Birthday, " +
                "Number, WeeklyHours, Password, RoleID) VALUES(?,?,?,?,?,?,?)";
        int rowsInserted = jdbcTemplate.update(sql, account.getName(),
                account.getEmail(), account.getBirthday(), account.getNumber(),
                account.getWeeklyHours(), account.getPassword(), account.getRoleID());
        return rowsInserted == 1;
    }

    @Override
    public Account updateAccount(Account account) throws DataAccessException {
        String sql = "UPDATE Accounts SET(Name = ?, Email = ?, Birthday = ?, " +
                "Number = ?, WeeklyHours = ?, Password = ?, RoleID = ?";
        jdbcTemplate.update(sql, account.getName(),
                account.getEmail(), account.getBirthday(), account.getNumber(),
                account.getWeeklyHours(), account.getPassword(), account.getRoleID());
        return account;
    }

    @Override
    public boolean deleteAccount(int accountID) throws DataAccessException {
        String sql = "DELETE FROM Accounts where AccountID = ?";
        int deletedRows = jdbcTemplate.update(sql, accountID);
        return deletedRows != 0;
    }

    @Override
    public Account getAccountFromEmailAndPassword(String email, String password) throws DataAccessException {
        String sql = "SELECT * FROM Accounts where Email = ? AND Password = ?";
        return jdbcTemplate.queryForObject(sql, new AccountRowMapper(), email, password);
    }
}
