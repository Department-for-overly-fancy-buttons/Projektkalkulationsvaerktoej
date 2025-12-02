package ek.dfofb.projektkalkulationsvaerktoej.controller;

import ek.dfofb.projektkalkulationsvaerktoej.model.Account;
import ek.dfofb.projektkalkulationsvaerktoej.model.Project;
import ek.dfofb.projektkalkulationsvaerktoej.service.AccountService;
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
    public String listProjects(Model model) {
        model.addAttribute("accounts", accountService.getAllAccounts());
        return "list-all-accounts";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        Account account = new Account();
        model.addAttribute("account", account);
        return "create-account-form";
    }

    @PostMapping("/create")
    public String handleCreateForm(@ModelAttribute Account account) {
        accountService.addAccount(account);
        return "redirect:/account/list";
    }

}
