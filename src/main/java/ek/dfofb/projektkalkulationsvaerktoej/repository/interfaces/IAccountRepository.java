package ek.dfofb.projektkalkulationsvaerktoej.repository.interfaces;

import ek.dfofb.projektkalkulationsvaerktoej.model.Account;

import java.util.List;

public interface IAccountRepository {

    Account getAccountFromID(int accountID);

    List<Account> getAllAccounts();

    List<Account> getAllAccountsWithRoleID(int roleID);

    boolean addAccount(Account account);

    Account updateAccount(Account account);

    boolean deleteAccount(int accountID);
}
