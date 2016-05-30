package com.corriel.web;

import com.corriel.budget.entity.Budget;
import com.corriel.budget.service.BudgetService;
import com.corriel.users.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.inject.Inject;
import java.util.List;

@Controller
@RequestMapping("/budgets")
public class BudgetController {

    @Inject
    private UserService userService;

    @Inject
    private BudgetService budgetService;

    @RequestMapping(method = RequestMethod.GET)
    public String budgetInfo(Model model) {

        List<Budget> usersBudgets = budgetService.findUsersBudgets(userService.getCurrentUserName());

        if (CollectionUtils.isEmpty(usersBudgets)) {
            return "new_budget";
        } else {
            model.addAttribute(usersBudgets);
            return "budget";
        }
    }
}
