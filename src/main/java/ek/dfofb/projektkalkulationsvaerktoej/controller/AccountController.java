package ek.dfofb.projektkalkulationsvaerktoej.controller;

import ek.dfofb.projektkalkulationsvaerktoej.model.Account;
import ek.dfofb.projektkalkulationsvaerktoej.model.Permission;
import ek.dfofb.projektkalkulationsvaerktoej.model.Role;
import ek.dfofb.projektkalkulationsvaerktoej.service.AccountService;
import ek.dfofb.projektkalkulationsvaerktoej.service.AuthorizationService;
import ek.dfofb.projektkalkulationsvaerktoej.service.RoleService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("account")
public class AccountController {

    private final AccountService accountService;
    private final RoleService roleService;
    private final AuthorizationService authorizationService;

    public AccountController(AccountService accountService, RoleService roleService, AuthorizationService authorizationService) {
        this.accountService = accountService;
        this.roleService = roleService;
        this.authorizationService = authorizationService;
    }

    @GetMapping("/create")
    public String showCreateForm(Model model, HttpSession httpSession) {
        Account account = (Account) httpSession.getAttribute("account");
        if (account == null) {
            return "redirect:/account/login";
        }
        if (!authorizationService.hasPermission(account.getRoleID(), Permission.GRANT_PERMISSIONS)) {
            return "redirect:/project";
        }
        Account newAccount = new Account();
        List<Role> roles = roleService.getAllRoles();
        model.addAttribute("roles", roles);
        model.addAttribute("account", newAccount);
        return "create-account-form";
    }

    @PostMapping("/create")
    public String handleCreateForm(@ModelAttribute Account account, HttpSession httpSession) {
        Account creatorAccount = (Account) httpSession.getAttribute("account");
        if (creatorAccount == null) {
            return "redirect:/account/login";
        }
        if (!authorizationService.hasPermission(creatorAccount.getRoleID(), Permission.GRANT_PERMISSIONS)) {
            return "redirect:/project";
        } else {
            accountService.addAccount(account);
        }
        return "redirect:/account/list";
    }

    @GetMapping("/edit")
    public String showEditAccountForm(int accountID, Model model, HttpSession httpSession) {
        Account creatorAccount = (Account) httpSession.getAttribute("account");
        if (creatorAccount == null) {
            return "redirect:/account/login";
        }
        if (!authorizationService.hasPermission(creatorAccount.getRoleID(), Permission.GRANT_PERMISSIONS)) {
            return "redirect:/project";
        }
        model.addAttribute("account", accountService.getAccountFromID(accountID));
        model.addAttribute("roles", roleService.getAllRoles());
        return "edit-account-form";
    }

    @PostMapping("/update")
    public String updateAccount(@ModelAttribute Account account, HttpSession httpSession) {
        Account creatorAccount = (Account) httpSession.getAttribute("account");
        if (creatorAccount == null) {
            return "redirect:/account/login";
        }
        if (!authorizationService.hasPermission(creatorAccount.getRoleID(), Permission.GRANT_PERMISSIONS)) {
            return "redirect:/project";
        }
        accountService.updateAccount(account);
        return "redirect:/account/list";
    }

    @GetMapping("/change/password")
    public String showChangePasswordForm(Model model, HttpSession httpSession) {
        Account account = (Account) httpSession.getAttribute("account");
        if (account == null) {
            return "redirect:/account/login";
        }
        model.addAttribute("account", account);
        return "change-password-form";
    }

    @PostMapping("/change/password")
    public String changePassword(@ModelAttribute Account account, HttpSession httpSession) {
        Account myAccount = (Account) httpSession.getAttribute("account");
        if (myAccount == null) {
            return "redirect:/account/login";
        }
        if (myAccount.getAccountID() != account.getAccountID()) {
            return "redirect:/project";
        }
        Account newPassword = accountService.getAccountFromID(account.getAccountID());
        newPassword.setPassword(account.getPassword());
        accountService.updateAccount(newPassword);
        return "redirect:/account/myAccount";
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
        session.setAttribute("account", foundAccount);
        session.setMaxInactiveInterval(1800);
        return "redirect:/project";
    }

    @GetMapping("log_out")
    public String logOut(HttpSession session) {
        session.removeAttribute("account");
        return "redirect:/account/login";
    }

    @GetMapping("/list")
    public String listAccounts(Model model, HttpSession httpSession) {
        Account account = (Account) httpSession.getAttribute("account");
        if (account == null) {
            return "redirect:/account/login";
        }
        List<Account> accounts = accountService.getAllAccounts();
        List<Role> roles = roleService.getAllRoles();

        Map<Integer, String> roleNames = roles.stream().collect(Collectors.toMap(Role::getRoleID, Role::getName));

        model.addAttribute("accounts", accounts);
        model.addAttribute("role", roleService.getRoleFromID(account.getRoleID()));
        model.addAttribute("roleNames", roleNames);

        return "list-all-accounts";
    }

    @GetMapping("/myAccount")
    public String showAccount(Model model, HttpSession httpSession) {
        Account account = (Account) httpSession.getAttribute("account");
        if (account == null) {
            return "redirect:/account/login";
        }
        model.addAttribute("role",roleService.getRoleFromID(account.getRoleID()));
        model.addAttribute("account", account);
        return "show-account";
    }


}
