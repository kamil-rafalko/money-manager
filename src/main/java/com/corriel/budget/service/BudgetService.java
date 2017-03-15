package com.corriel.budget.service;

import com.corriel.budget.entity.Budget;
import com.corriel.budget.entity.MonthlyBudget;
import com.corriel.budget.repository.BudgetDao;
import com.corriel.users.service.UserService;
import com.corriel.web.dto.BudgetDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
@Transactional
public class BudgetService {

    private final BudgetDao budgetDao;
    private final UserService userService;
    private final PartBudgetService partBudgetService;

    @Inject
    public BudgetService(final BudgetDao budgetDao,
                         final UserService userService,
                         final PartBudgetService partBudgetService) {
        this.budgetDao = budgetDao;
        this.userService = userService;
        this.partBudgetService = partBudgetService;
    }

    public Budget find(long id) {
        return budgetDao.find(id);
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
            Map<String, BigDecimal> transactionCategoryBigDecimalMap = partBudgetService
                    .mapCategoryToExpenses(partBudget);
            transactionCategoryBigDecimalMap.forEach((k, v) -> expensesForCategories.merge(k, v, BigDecimal::add));
        });
        return expensesForCategories;
    }
}
