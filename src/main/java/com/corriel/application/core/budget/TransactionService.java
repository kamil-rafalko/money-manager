package com.corriel.application.core.budget;

import com.corriel.application.core.annotations.ApplicationService;
import com.corriel.application.core.entity.MonthlyBudget;
import com.corriel.application.core.entity.Transaction;
import com.corriel.application.core.entity.Category;
import com.corriel.application.core.repository.TransactionRepository;
import com.corriel.application.dto.TransactionDto;

import javax.inject.Inject;
import java.time.YearMonth;
import java.util.ArrayList;

@ApplicationService
public class TransactionService {

    private final TransactionRepository repository;
    private final PartBudgetService partBudgetService;
    private final CategoryService categoryService;

    @Inject
    public TransactionService(final TransactionRepository repository,
                              final PartBudgetService partBudgetService,
                              final CategoryService categoryService) {
        this.repository = repository;
        this.partBudgetService = partBudgetService;
        this.categoryService = categoryService;
    }

    public void create(final TransactionDto dto) {
        YearMonth yearMonth = YearMonth.from(dto.getDate());
        MonthlyBudget monthlyBudget = partBudgetService.findForCurrentUserBy(yearMonth)
                .orElseGet(() -> partBudgetService.createBudgetFor(yearMonth));

        Transaction transaction = convertToEntity(dto);
        Category category = monthlyBudget.getCategories().stream()
                .filter(c -> c.getName().equals(dto.getCategoryName()))
                .findAny()
                .orElseGet(() -> createCategory(dto.getCategoryName()));
        monthlyBudget.getCategories().add(category);
        category.getTransactions().add(transaction);
        transaction.setCategory(category);
        repository.create(transaction);
    }

    private Category createCategory(String name) {
        Category category = Category.builder()
                .name(name)
                .transactions(new ArrayList<>())
                .build();
        categoryService.create(category);
        return category;
    }

    private Transaction convertToEntity(TransactionDto dto) {
        return Transaction.builder()
                .amount(dto.getAmount())
                .date(dto.getDate())
                .build();
    }
}
