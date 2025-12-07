package ek.dfofb.projektkalkulationsvaerktoej.controller;

import ek.dfofb.projektkalkulationsvaerktoej.model.Account;
import ek.dfofb.projektkalkulationsvaerktoej.model.Project;
import ek.dfofb.projektkalkulationsvaerktoej.model.Role;
import ek.dfofb.projektkalkulationsvaerktoej.service.AccountService;
import ek.dfofb.projektkalkulationsvaerktoej.service.RoleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("account")
public class AccountController {

    private final AccountService accountService;
    private final RoleService roleService;

    public AccountController(AccountService accountService, RoleService roleService)
    {
        this.accountService = accountService;
        this.roleService = roleService;
    }

    @GetMapping("/create")
    public String showCreateForm(Model model)
    {
        Account account = new Account();
        List<Role> roles = roleService.getAllRoles();

        model.addAttribute("account", account);
        model.addAttribute("roles", roles);

        return "create-account-form";
    }

    @PostMapping("/create")
    public String handleCreateForm(@ModelAttribute Account account)
    {
        accountService.addAccount(account);
        return "redirect:/account/list";
    }

    @GetMapping("/list")
    public String listAccounts(Model model)
    {
        List<Account> accounts = accountService.getAllAccounts();
        List<Role> roles = roleService.getAllRoles();

        Map<Integer, String> roleNames = roles.stream().collect(Collectors.toMap(Role::getRoleID, Role::getName));

        model.addAttribute("accounts", accounts);
        model.addAttribute("roleNames", roleNames);

        return "list-all-accounts";
    }





}
