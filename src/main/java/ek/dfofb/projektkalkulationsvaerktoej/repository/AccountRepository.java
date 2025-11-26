package ek.dfofb.projektkalkulationsvaerktoej.repository;

import ek.dfofb.projektkalkulationsvaerktoej.model.Account;
import ek.dfofb.projektkalkulationsvaerktoej.repository.interfaces.IAccountRepository;
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
    public Account getAccountFromID(int accountID) {
        return null;
    }

    @Override
    public List<Account> getAllAccounts() {
        return List.of();
    }

    @Override
    public boolean addAccount(Account account) {
        return false;
    }

    @Override
    public Account updateAccount(Account account) {
        return null;
    }
}
