package ek.dfofb.projektkalukulationsvaerktoej.repository;

import ek.dfofb.projektkalukulationsvaerktoej.model.Account;
import ek.dfofb.projektkalukulationsvaerktoej.repository.interfaces.IAccountRepository;

import java.util.List;

public class AccountRepository implements IAccountRepository
{
    @Override
    public Account getAccountFromID(int accountID) {
        return null;
    }

    @Override
    public List<Account> getAllAccounts() {
        return List.of();
    }
}
