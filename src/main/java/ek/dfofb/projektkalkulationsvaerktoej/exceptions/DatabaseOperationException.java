package ek.dfofb.projektkalkulationsvaerktoej.exceptions;

public class DatabaseOperationException extends RuntimeException {
    public DatabaseOperationException(String message) {
        super(message);
    }
}
