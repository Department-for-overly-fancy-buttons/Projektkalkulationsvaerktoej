package ek.dfofb.projektkalukulationsvaerktoej.repository.interfaces;

import ek.dfofb.projektkalukulationsvaerktoej.model.Account;

import java.util.List;

public interface IAccountRepository {

    Account getAccountFromID(int accountID);

    List<Account> getAllAccounts();

    boolean addAccount(Account account);

    Account updateAccount(Account account);
}
