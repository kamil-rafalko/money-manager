package com.corriel.application.core.budget;

import com.corriel.application.core.annotations.ApplicationService;
import com.corriel.application.core.entity.Budget;
import com.corriel.application.core.entity.Category;
import com.corriel.application.core.entity.MonthlyBudget;
import com.corriel.application.core.entity.Transaction;
import com.corriel.application.core.entity.SystemUser;
import com.corriel.application.core.repository.PartBudgetRepository;
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
public class PartBudgetService {

    private final UserService userService;
    private final PartBudgetRepository repository;

    @Inject
    public PartBudgetService(final UserService userService, final PartBudgetRepository repository) {
        this.userService = userService;
        this.repository = repository;
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
        repository.create(monthlyBudget);
        budget.getMonthlyBudgets().add(monthlyBudget);
        return monthlyBudget;
    }

    public List<BudgetDto> findAllForCurrentUser() {
        SystemUser currentUser = userService.getCurrentUser();
        Set<MonthlyBudget> monthlyBudgets = currentUser.getBudget().getMonthlyBudgets();

        return monthlyBudgets.stream()
                .map(monthlyBudget -> new BudgetDto(monthlyBudget.getId(), monthlyBudget.getYearMonth().toString()))
                .collect(Collectors.toList());
    }

    public BudgetDetails createDetails(long id) {
        MonthlyBudget monthlyBudget = repository.find(id);
        Map<String, BigDecimal> categoryToExpenses = mapCategoryToExpenses(monthlyBudget);
        return new BudgetDetails(monthlyBudget.getYearMonth().toString(), categoryToExpenses);
    }

    Map<String, BigDecimal> mapCategoryToExpenses(MonthlyBudget monthlyBudget) {
        Set<Category> categories = monthlyBudget.getCategories();

        return categories.stream().collect(Collectors.toMap(Category::getName,
                category -> category.getTransactions().stream().map(Transaction::getAmount)
                        .reduce(BigDecimal.ZERO, BigDecimal::add)));
    }
}
