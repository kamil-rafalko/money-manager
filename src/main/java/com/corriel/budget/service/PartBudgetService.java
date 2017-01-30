package com.corriel.budget.service;

import com.corriel.budget.entity.PartBudget;
import com.corriel.budget.entity.transaction.MoneyTransaction;
import com.corriel.budget.entity.transaction.TransactionCategory;
import com.corriel.budget.repository.PartBudgetDao;
import com.corriel.users.entity.SystemUser;
import com.corriel.users.service.UserService;
import com.corriel.web.dto.BudgetDetails;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class PartBudgetService {

    private final UserService userService;
    private final PartBudgetDao partBudgetDao;

    @Inject
    public PartBudgetService(final UserService userService, final PartBudgetDao partBudgetDao) {
        this.userService = userService;
        this.partBudgetDao = partBudgetDao;
    }

    public PartBudget find(long id) {
        return partBudgetDao.find(id);
    }

    public Set<PartBudget> findAllForCurrentUser() {
        SystemUser currentUser = userService.getCurrentUser();
        Set<PartBudget> partBudgets = currentUser.getBudget().getPartBudgets();
        Hibernate.initialize(partBudgets);
        return partBudgets;
    }

    public BudgetDetails createDetails(long id) {
        PartBudget partBudget = find(id);
        Map<String, BigDecimal> categoryToExpenses = mapCategoryToExpenses(partBudget);
        return new BudgetDetails(partBudget.getName(), categoryToExpenses);
    }

    Map<String, BigDecimal> mapCategoryToExpenses(PartBudget partBudget) {
        Set<TransactionCategory> transactionCategories = partBudget.getTransactionCategories();

        return transactionCategories.stream().collect(Collectors.toMap(TransactionCategory::getName,
                category -> category.getMoneyTransactions().stream().map(MoneyTransaction::getAmount)
                        .reduce(BigDecimal.ZERO, BigDecimal::add)));
    }
}
