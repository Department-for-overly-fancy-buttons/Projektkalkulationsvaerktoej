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
            return "redirect:login";
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
            return "redirect:login";
        }
        if (!authorizationService.hasPermission(account.getRoleID(), Permission.GRANT_PERMISSIONS)) {
            return "redirect:/project";
        } else {
            accountService.addAccount(account);
        }
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
        session.setAttribute("account", foundAccount);
        session.setMaxInactiveInterval(1800);
        return "redirect:/project";
    }

    @GetMapping("log_out")
    public String logOut(HttpSession session) {
        session.removeAttribute("account");
        return "redirect:login";
    }

    @GetMapping("/list")
    public String listAccounts(Model model, HttpSession httpSession) {
        Account account = (Account) httpSession.getAttribute("account");
        if (account == null) {
            return "redirect:login";
        }
        List<Account> accounts = accountService.getAllAccounts();
        List<Role> roles = roleService.getAllRoles();

        Map<Integer, String> roleNames = roles.stream().collect(Collectors.toMap(Role::getRoleID, Role::getName));

        model.addAttribute("accounts", accounts);
        model.addAttribute("role", roleService.getRoleFromID(account.getRoleID()));
        model.addAttribute("roleNames", roleNames);

        return "list-all-accounts";
    }


}
