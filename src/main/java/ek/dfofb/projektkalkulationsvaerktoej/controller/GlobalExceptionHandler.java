package ek.dfofb.projektkalkulationsvaerktoej.controller;

import ek.dfofb.projektkalkulationsvaerktoej.exceptions.*;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

@ExceptionHandler({ProjectNotFoundException.class, AccountNotFoundException.class, TaskNotFoundException.class})
    public String handleProjectNotFoundException(EntryNotFoundException exception, Model model) {
    model.addAttribute("status", HttpStatus.NOT_FOUND.value());
    model.addAttribute("errorType", "Not found");
    model.addAttribute("message", exception.getMessage());
    return "error/404";
}

    @ExceptionHandler({DuplicateProjectException.class, DuplicateAccountException.class, DuplicateTaskException.class, DuplicateTasklistEntryException.class})
    public String handleDuplicateEntryException(DuplicateEntryException exception, Model model) {
        model.addAttribute("status", HttpStatus.CONFLICT.value());
        model.addAttribute("errorType", "Duplicate entry");
        model.addAttribute("message", exception.getMessage());
        return "error/error";
    }

    @ExceptionHandler(DatabaseOperationException.class)
    public String handleDatabaseOperationException(DatabaseOperationException exception, Model model) {
        model.addAttribute("status", HttpStatus.INTERNAL_SERVER_ERROR);
        model.addAttribute("errorType", "Internal server error");
        model.addAttribute("message", "Something went wrong, please try again later");
        return "error/500";
    }

}
