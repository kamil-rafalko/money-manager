package com.corriel.application.core.budget;

import com.corriel.application.core.annotations.ApplicationService;
import com.corriel.application.core.entity.*;
import com.corriel.application.core.repository.MonthBudgetRepository;
import com.corriel.application.core.users.UserService;
import com.corriel.application.dto.BudgetDetails;
import com.corriel.application.dto.BudgetDto;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@ApplicationService
public class MonthBudgetService {

    private final UserService userService;
    private final MonthBudgetRepository repository;

    @Inject
    public MonthBudgetService(final UserService userService, final MonthBudgetRepository repository) {
        this.userService = userService;
        this.repository = repository;
    }

    Optional<MonthBudget> findForCurrentUserBy(YearMonth yearMonth) {
        SystemUser user = userService.getCurrentUser();
        Budget budget = user.getBudget();
        Set<MonthBudget> monthBudgets = budget.getMonthBudgets();
        return monthBudgets.stream()
                .filter(monthlyBudget -> monthlyBudget.getYearMonth().equals(yearMonth))
                .findAny();
    }

    MonthBudget createBudgetFor(YearMonth yearMonth) {
        SystemUser user = userService.getCurrentUser();
        Budget budget = user.getBudget();
        MonthBudget monthBudget = new MonthBudget(yearMonth);
        repository.create(monthBudget);
        budget.getMonthBudgets().add(monthBudget);
        return monthBudget;
    }

    public List<BudgetDto> findAllForCurrentUser() {
        SystemUser currentUser = userService.getCurrentUser();
        Set<MonthBudget> monthBudgets = currentUser.getBudget().getMonthBudgets();

        return monthBudgets.stream()
                .map(monthlyBudget -> new BudgetDto(monthlyBudget.getId(), monthlyBudget.getYearMonth().toString()))
                .collect(Collectors.toList());
    }

    public BudgetDetails createDetails(long id) {
        MonthBudget monthBudget = repository.find(id);
        Map<String, BigDecimal> categoryToExpenses = mapCategoryToExpenses(monthBudget);
        return new BudgetDetails(monthBudget.getYearMonth().toString(), categoryToExpenses);
    }

    Map<String, BigDecimal> mapCategoryToExpenses(MonthBudget monthBudget) {
        Set<Category> categories = monthBudget.getCategories();

        return categories.stream().collect(Collectors.toMap(Category::getName,
                category -> category.getTransactions().stream().map(Transaction::getAmount)
                        .reduce(BigDecimal.ZERO, BigDecimal::add)));
    }
}
