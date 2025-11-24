package ek.dfofb.projektkalukulationsvaerktoej.repository;

import ek.dfofb.projektkalukulationsvaerktoej.model.Account;
import ek.dfofb.projektkalukulationsvaerktoej.repository.interfaces.IAccountRepository;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class AccountRepository implements IAccountRepository {
    private final JdbcTemplate jdbcTemplate;

    public AccountRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Account getAccountFromID(int accountID) {
        return null;
    }

    @Override
    public List<Account> getAllAccounts() {
        return List.of();
    }
}
