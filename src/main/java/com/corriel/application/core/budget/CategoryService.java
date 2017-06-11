package com.corriel.application.core.budget;

import com.corriel.application.core.annotations.ApplicationService;
import com.corriel.application.core.entity.Category;
import com.corriel.application.core.entity.MonthBudget;
import com.corriel.application.core.repository.CategoryRepository;
import com.corriel.application.core.repository.MonthBudgetRepository;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@ApplicationService
public class CategoryService {

    private final CategoryRepository repository;
    private final BudgetService budgetService;
    private final MonthBudgetRepository monthBudgetRepository;

    public CategoryService(final CategoryRepository repository,
                           final BudgetService budgetService,
                           final MonthBudgetRepository monthBudgetRepository) {
        this.repository = repository;
        this.budgetService = budgetService;
        this.monthBudgetRepository = monthBudgetRepository;
    }

    void create(Category category) {
        repository.create(category);
    }

    List<Category> findAllForCurrentUser() {
        return budgetService.getCurrentUserBudget().getMonthBudgets()
                .stream()
                .map(MonthBudget::getCategories)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    Set<Category> findAllForMonthBudget(long id) {
        return monthBudgetRepository.find(id).getCategories();
    }
}