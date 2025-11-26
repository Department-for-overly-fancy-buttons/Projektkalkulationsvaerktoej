package ek.dfofb.projektkalkulationsvaerktoej.exceptions;

public class AccountNotFoundException extends RuntimeException
{
    public AccountNotFoundException(String message)
    {
        super(message);
    }
}
