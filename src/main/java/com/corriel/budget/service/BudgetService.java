package com.corriel.budget.service;

import com.corriel.budget.entity.Budget;
import com.corriel.budget.entity.PartBudget;
import com.corriel.budget.entity.transaction.TransactionCategory;
import com.corriel.budget.repository.BudgetDao;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.*;

@Service
@Transactional
public class BudgetService {

    final private BudgetDao budgetDao;
    final private PartBudgetService partBudgetService;

    @Inject
    public BudgetService(final BudgetDao budgetDao, PartBudgetService
            partBudgetService) {
        this.budgetDao = budgetDao;
        this.partBudgetService = partBudgetService;
    }

    public Budget find(long id) {
        return budgetDao.find(id);
    }

    public Budget findWithPartBudgets(long id) {
        Budget budget = find(id);
        Hibernate.initialize(budget.getPartBudgets());
        return budget;
    }

    public Map<TransactionCategory, BigDecimal> mapCategoryToSummaryExpense(long id) {
        HashMap<TransactionCategory, BigDecimal> expensesForCategories = new HashMap<>();
        Budget budget = find(id);
        Set<PartBudget> partBudgets = budget.getPartBudgets();

        partBudgets.forEach(partBudget -> {
            Map<TransactionCategory, BigDecimal> transactionCategoryBigDecimalMap = partBudgetService
                    .mapCategoryToSummaryExpense(partBudget);
            transactionCategoryBigDecimalMap.forEach((k, v) -> expensesForCategories.merge(k, v, BigDecimal::add));
        });
        return expensesForCategories;
    }
}
