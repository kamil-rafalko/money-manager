package com.corriel.application.core.budget;

import com.corriel.application.core.annotations.ApplicationService;
import com.corriel.application.core.entity.Budget;
import com.corriel.application.core.entity.MonthBudget;
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
    private final MonthBudgetService monthBudgetService;

    @Inject
    public BudgetService(final UserService userService,
                         final MonthBudgetService monthBudgetService) {
        this.userService = userService;
        this.monthBudgetService = monthBudgetService;
    }

    public BudgetDetails createDetails() {
        Budget budget = userService.getCurrentUser().getBudget();
        Map<String, BigDecimal> categoryToSummaryExpense = mapCategoryToExpenses(budget);
        return new BudgetDetails("Summary expenses", categoryToSummaryExpense);
    }

    Map<String, BigDecimal> mapCategoryToExpenses(Budget budget) {
        Map<String, BigDecimal> expensesForCategories = new HashMap<>();
        Set<MonthBudget> monthBudgets = budget.getMonthBudgets();

        monthBudgets.forEach(partBudget -> {
            Map<String, BigDecimal> categoryBigDecimalMap = monthBudgetService
                    .mapCategoryToExpenses(partBudget);
            categoryBigDecimalMap.forEach((k, v) -> expensesForCategories.merge(k, v, BigDecimal::add));
        });
        return expensesForCategories;
    }
}
