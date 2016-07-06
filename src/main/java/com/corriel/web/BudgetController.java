package com.corriel.web;

import com.corriel.budget.entity.Budget;
import com.corriel.budget.service.BudgetService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.Map;

@Controller
@RequestMapping("/budget")
public class BudgetController {

    @Inject
    private BudgetService budgetService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String budgetInfo(@PathVariable long id, Model model) {

        Budget budget = budgetService.findWithPartBudgets(id);
        Map<String, BigDecimal> expensesForCategories = budgetService.mapExpensesToCategoriesNames(id);

        model.addAttribute("expensesForCategories", expensesForCategories);
        model.addAttribute(budget);
        return "budget";
    }
}
