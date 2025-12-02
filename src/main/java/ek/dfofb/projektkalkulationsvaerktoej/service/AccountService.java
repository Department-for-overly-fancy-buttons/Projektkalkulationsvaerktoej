package ek.dfofb.projektkalkulationsvaerktoej.service;

import ek.dfofb.projektkalkulationsvaerktoej.model.Account;
import ek.dfofb.projektkalkulationsvaerktoej.repository.interfaces.IAccountRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {
    private IAccountRepository accountRepository;

    public AccountService(IAccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account getAccountFromID(int accountID) {
        return accountRepository.getAccountFromID(accountID);
    }

    public List<Account> getAllAccounts() {
        return accountRepository.getAllAccounts();
    }

    public List<Account> getAllAccountsWithRoleID(int roleID) {
        return accountRepository.getAllAccountsWithRoleID(roleID);
    }

    public boolean addAccount(Account account) {
        return accountRepository.addAccount(account);
    }

    public Account updateAccount(Account account) {
        return accountRepository.updateAccount(account);
    }

    public boolean deleteAccount(int accountID) {
        return accountRepository.deleteAccount(accountID);
    }

}
