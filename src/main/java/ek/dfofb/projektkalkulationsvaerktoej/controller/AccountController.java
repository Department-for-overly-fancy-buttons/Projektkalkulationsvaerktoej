package ek.dfofb.projektkalkulationsvaerktoej.controller;

import ek.dfofb.projektkalkulationsvaerktoej.model.Account;
import ek.dfofb.projektkalkulationsvaerktoej.service.AccountService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("account")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/list")
    public String listProjects(Model model, HttpSession httpSession) {
        Account account = (Account) httpSession.getAttribute("account");
        if (account == null) {
            return "redirect:login";
        }
        model.addAttribute("accounts", accountService.getAllAccounts());
        return "list-all-accounts";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model, HttpSession httpSession) {
        Account account = (Account) httpSession.getAttribute("account");
        if (account == null) {
            return "redirect:login";
        }
        Account newAccount = new Account();
        model.addAttribute("account", newAccount);
        return "create-account-form";
    }

    @PostMapping("/create")
    public String handleCreateForm(@ModelAttribute Account account) {
        accountService.addAccount(account);
        return "redirect:list";
    }

    @GetMapping("/login")
    public String logInForm(Model model, HttpSession session) {
        if (session.getAttribute("account") == null) {
            model.addAttribute("account", new Account());
            return "log-in-form";
        } else {
            return "redirect:/project/list";
        }
    }

    @PostMapping("/login")
    public String LogIn(@ModelAttribute Account account, HttpSession session) {
        Account foundAccount = accountService.logIn(account.getEmail(), account.getPassword());
        //TODO find rollen og sæt den på account eller som sin egen session attribute
        session.setAttribute("account", foundAccount);
        session.setMaxInactiveInterval(1800);
        return "redirect:/project";
    }

    @GetMapping("log_out")
    public String logOut(HttpSession session) {
        session.removeAttribute("account");
        return "redirect:login";
    }

}
