package com.corriel.budget.service;

import com.corriel.budget.entity.Budget;
import com.corriel.budget.entity.PartBudget;
import com.corriel.budget.entity.transaction.MoneyTransaction;
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

    @Inject
    private BudgetDao budgetDao;

    public Budget find(long id) {
        return budgetDao.find(id);
    }

    public Budget findWithPartBudgets(long id) {
        Budget budget = find(id);
        Hibernate.initialize(budget.getPartBudgets());
        return budget;
    }

    public Map<String, BigDecimal> mapExpensesToCategoriesNames(long id) {
        HashMap<String, BigDecimal> expensesForCategories = new HashMap<>();
        Budget budget = find(id);
        Set<PartBudget> partBudgets = budget.getPartBudgets();
        getAllTransactionCategoriesFor(partBudgets).stream().forEach(
                category -> expensesForCategories.put(category.getName(), getAmountOfExpensesFor(category)));
        return expensesForCategories;
    }

    private List<TransactionCategory> getAllTransactionCategoriesFor(Set<PartBudget> partBudgets) {
        List<TransactionCategory> transactionCategories = new ArrayList<>();
        partBudgets.stream().forEach(partBudget -> transactionCategories.addAll(partBudget.getTransactionCategories()));
        return transactionCategories;
    }

    private BigDecimal getAmountOfExpensesFor(TransactionCategory category) {
        BigDecimal expenses = BigDecimal.ZERO;
        List<MoneyTransaction> moneyTransactions = category.getMoneyTransactions();
        for (MoneyTransaction transaction : moneyTransactions) {
            expenses = expenses.add(transaction.getAmount());
        }
        return expenses;
    }
}
