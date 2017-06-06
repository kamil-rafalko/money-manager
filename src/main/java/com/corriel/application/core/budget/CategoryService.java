package com.corriel.application.core.budget;

import com.corriel.application.core.annotations.ApplicationService;
import com.corriel.application.core.entity.Category;
import com.corriel.application.core.entity.MonthBudget;
import com.corriel.application.core.repository.CategoryRepository;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationService
public class CategoryService {

    private final CategoryRepository repository;
    private final BudgetService budgetService;

    public CategoryService(final CategoryRepository repository,
                           final BudgetService budgetService) {
        this.repository = repository;
        this.budgetService = budgetService;
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
}