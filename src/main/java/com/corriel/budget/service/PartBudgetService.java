package com.corriel.budget.service;

import com.corriel.budget.entity.Budget;
import com.corriel.budget.entity.MonthlyBudget;
import com.corriel.budget.entity.Transaction;
import com.corriel.budget.entity.TransactionCategory;
import com.corriel.budget.repository.PartBudgetDao;
import com.corriel.users.entity.SystemUser;
import com.corriel.users.service.UserService;
import com.corriel.web.dto.BudgetDetails;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.Map;
import java.util.Optional;
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

    public MonthlyBudget find(long id) {
        return partBudgetDao.find(id);
    }

    Optional<MonthlyBudget> findForCurrentUserBy(YearMonth yearMonth) {
        SystemUser user = userService.getCurrentUser();
        Budget budget = user.getBudget();
        Set<MonthlyBudget> monthlyBudgets = budget.getMonthlyBudgets();
        return monthlyBudgets.stream()
                .filter(monthlyBudget -> monthlyBudget.getYearMonth().equals(yearMonth))
                .findAny();
    }

    MonthlyBudget createBudgetFor(YearMonth yearMonth) {
        SystemUser user = userService.getCurrentUser();
        Budget budget = user.getBudget();
        MonthlyBudget monthlyBudget = new MonthlyBudget(yearMonth);
        partBudgetDao.create(monthlyBudget);
        budget.getMonthlyBudgets().add(monthlyBudget);
        return monthlyBudget;
    }

    public Set<MonthlyBudget> findAllForCurrentUser() {
        SystemUser currentUser = userService.getCurrentUser();
        Set<MonthlyBudget> monthlyBudgets = currentUser.getBudget().getMonthlyBudgets();
        Hibernate.initialize(monthlyBudgets);
        return monthlyBudgets;
    }

    public BudgetDetails createDetails(long id) {
        MonthlyBudget monthlyBudget = find(id);
        Map<String, BigDecimal> categoryToExpenses = mapCategoryToExpenses(monthlyBudget);
        return new BudgetDetails(monthlyBudget.getYearMonth().toString(), categoryToExpenses);
    }

    Map<String, BigDecimal> mapCategoryToExpenses(MonthlyBudget monthlyBudget) {
        Set<TransactionCategory> transactionCategories = monthlyBudget.getTransactionCategories();

        return transactionCategories.stream().collect(Collectors.toMap(TransactionCategory::getName,
                category -> category.getTransactions().stream().map(Transaction::getAmount)
                        .reduce(BigDecimal.ZERO, BigDecimal::add)));
    }
}
