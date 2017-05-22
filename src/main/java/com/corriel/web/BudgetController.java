package com.corriel.web;

import com.corriel.application.core.budget.BudgetService;
import com.corriel.application.core.budget.PartBudgetService;
import com.corriel.application.dto.BudgetDetails;
import com.corriel.application.dto.BudgetDto;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;

@RestController
@RequestMapping("/budget")
class BudgetController {

    private final BudgetService budgetService;
    private final PartBudgetService partBudgetService;

    @Inject
    public BudgetController(final BudgetService budgetService, final PartBudgetService partBudgetService) {
        this.budgetService = budgetService;
        this.partBudgetService = partBudgetService;
    }

    @RequestMapping(value = "/details", method = RequestMethod.GET)
    public BudgetDetails details() {
        return budgetService.createDetails();
    }

    @RequestMapping("/{id}/details")
    public BudgetDetails details(@PathVariable final long id) {
        return partBudgetService.createDetails(id);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<BudgetDto> getMonthlyBudgets() {
        return partBudgetService.findAllForCurrentUser();
    }
}
