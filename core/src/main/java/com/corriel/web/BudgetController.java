package com.corriel.web;

import com.corriel.application.core.budget.BudgetService;
import com.corriel.application.core.budget.MonthBudgetService;
import com.corriel.application.dto.BudgetDetails;
import com.corriel.application.dto.BudgetDto;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;

@RestController
@RequestMapping("/budgets")
class BudgetController {

    private final BudgetService budgetService;
    private final MonthBudgetService monthBudgetService;

    @Inject
    public BudgetController(final BudgetService budgetService,
                            final MonthBudgetService monthBudgetService) {
        this.budgetService = budgetService;
        this.monthBudgetService = monthBudgetService;
    }

    @RequestMapping(value = "/details", method = RequestMethod.GET)
    public BudgetDetails details() {
        return budgetService.createDetails();
    }

    @RequestMapping("/{id}/details")
    public BudgetDetails details(@PathVariable final long id) {
        return monthBudgetService.createDetails(id);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<BudgetDto> getMonthlyBudgets() {
        return monthBudgetService.findAllForCurrentUser();
    }
}
