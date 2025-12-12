package ek.dfofb.projektkalkulationsvaerktoej.service;

import ek.dfofb.projektkalkulationsvaerktoej.exceptions.AccountNotFoundException;
import ek.dfofb.projektkalkulationsvaerktoej.exceptions.DatabaseOperationException;
import ek.dfofb.projektkalkulationsvaerktoej.exceptions.DuplicateAccountException;
import ek.dfofb.projektkalkulationsvaerktoej.model.Account;
import ek.dfofb.projektkalkulationsvaerktoej.repository.interfaces.IAccountRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {
    private IAccountRepository accountRepository;

    public AccountService(IAccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account getAccountFromID(int accountID) {
        try {
            return accountRepository.getAccountFromID(accountID);
        } catch (DataAccessException exception) {
            throw new AccountNotFoundException("Failed to find account");
        }
    }

    public List<Account> getAllAccounts() {
        try {
            return accountRepository.getAllAccounts();
        } catch (DataAccessException exception) {
            throw new DatabaseOperationException("A fatal error has occurred while attempting to access accounts");
        }
    }

    public List<Account> getAllAccountsWithRoleID(int roleID) {
        try {
            return accountRepository.getAllAccountsWithRoleID(roleID);
        } catch (DataAccessException exception) {
            throw new DatabaseOperationException("A fatal error has occurred while attempting to access accounts with given role");
        }
    }

    public boolean addAccount(Account account) {
        try {
            return accountRepository.addAccount(account);
        } catch (DataIntegrityViolationException exception) {
            throw new DuplicateAccountException("An account with the chosen email (" + account.getEmail() + ") and, or number (" + account.getNumber() + ") already exists");
        } catch (DataAccessException exception) {
            throw new DatabaseOperationException("A fatal error has occurred while attempting to create account");
        }
    }

    public Account updateAccount(Account account) {
        try {
            return accountRepository.updateAccount(account);
        } catch (DataIntegrityViolationException exception) {
            throw new DuplicateAccountException("An account with the chosen email (" + account.getEmail() + ") and, or number (" + account.getNumber() + ") already exists");
        } catch (DataAccessException exception) {
            throw new DatabaseOperationException("A fatal error has occurred while attempting to update account");
        }
    }

    public boolean deleteAccount(int accountID) {
        try {
            return accountRepository.deleteAccount(accountID);
        } catch (DataIntegrityViolationException exception) {
            throw new AccountNotFoundException("An account, with id (" + accountID + ") could not be found");
        } catch (DataAccessException exception) {
            throw new DatabaseOperationException("A fatal error has occurred while attempting to delete account");
        }
    }

    public Account logIn(String email, String password) {
        try {
            return accountRepository.getAccountFromEmailAndPassword(email, password);
        } catch (DataAccessException exception) {
            throw new AccountNotFoundException("Wrong Email or Password");
        }
    }

}
