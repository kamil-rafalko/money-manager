package com.corriel.web;

import com.corriel.budget.entity.PartBudget;
import com.corriel.budget.service.BudgetService;
import com.corriel.budget.service.PartBudgetService;
import com.corriel.web.dto.BudgetDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.Set;

@RestController
@RequestMapping("/budget")
public class BudgetController {

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
    public Set<PartBudget> partBudgetList() {
        return partBudgetService.findAllForCurrentUser();
    }
}
