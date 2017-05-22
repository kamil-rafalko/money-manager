package com.corriel.application.core.budget;

import com.corriel.application.core.annotations.ApplicationService;
import com.corriel.application.core.entity.Budget;
import com.corriel.application.core.entity.MonthlyBudget;
import com.corriel.application.core.users.UserService;
import com.corriel.application.dto.BudgetDetails;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@ApplicationService
public class BudgetService {

    private final UserService userService;
    private final PartBudgetService partBudgetService;

    @Inject
    public BudgetService(final UserService userService,
                         final PartBudgetService partBudgetService) {
        this.userService = userService;
        this.partBudgetService = partBudgetService;
    }

    public BudgetDetails createDetails() {
        Budget budget = userService.getCurrentUser().getBudget();
        Map<String, BigDecimal> categoryToSummaryExpense = mapCategoryToExpenses(budget);
        return new BudgetDetails("Summary expenses", categoryToSummaryExpense);
    }

    Map<String, BigDecimal> mapCategoryToExpenses(Budget budget) {
        Map<String, BigDecimal> expensesForCategories = new HashMap<>();
        Set<MonthlyBudget> monthlyBudgets = budget.getMonthlyBudgets();

        monthlyBudgets.forEach(partBudget -> {
            Map<String, BigDecimal> categoryBigDecimalMap = partBudgetService
                    .mapCategoryToExpenses(partBudget);
            categoryBigDecimalMap.forEach((k, v) -> expensesForCategories.merge(k, v, BigDecimal::add));
        });
        return expensesForCategories;
    }
}
