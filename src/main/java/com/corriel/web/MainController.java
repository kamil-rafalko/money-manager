package com.corriel.web;

import com.corriel.budget.entity.Budget;
import com.corriel.users.entity.SystemUser;
import com.corriel.users.service.UserService;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.inject.Inject;
import java.util.HashSet;
import java.util.Set;

@Controller
@NoArgsConstructor
public class MainController {

    private UserService userService;

    @Inject
    public MainController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = { "/", "/home" }, method = RequestMethod.GET)
    public String defaultPage(Model model) {
        model.addAttribute("title", "Money Manager Home Page");

        Set<Budget> budgets = userService.getCurrentUserWithBudgets()
                .map(SystemUser::getBudgets).orElse(new HashSet<>());

        model.addAttribute("budgets", budgets);

        return "home";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(@RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "logout", required = false) String logout, Model model) {

        if (error != null) {
            model.addAttribute("error", "Invalid username and password!");
        }
        if (logout != null) {
            model.addAttribute("message", "You've been logged out successfully.");
        }

        return "login";
    }
}
