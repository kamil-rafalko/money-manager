package com.corriel.application.core.budget;

import com.corriel.application.core.annotations.ApplicationService;
import com.corriel.application.core.entity.*;
import com.corriel.application.core.repository.TransactionRepository;
import com.corriel.application.dto.TransactionDto;

import javax.inject.Inject;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationService
public class TransactionService {

    private final TransactionRepository repository;
    private final MonthBudgetService monthBudgetService;
    private final CategoryService categoryService;

    @Inject
    public TransactionService(final TransactionRepository repository,
                              final MonthBudgetService monthBudgetService,
                              final CategoryService categoryService) {
        this.repository = repository;
        this.monthBudgetService = monthBudgetService;
        this.categoryService = categoryService;
    }

    public List<TransactionDto> findAllForCurrentUser() {
        return collectDtosFrom(categoryService.findAllForCurrentUser());
    }

    public List<TransactionDto> findAllForMonthBudget(long id) {
        return collectDtosFrom(categoryService.findAllForMonthBudget(id));
    }

    public void create(final TransactionDto dto) {
        YearMonth yearMonth = YearMonth.from(dto.getDate());
        MonthBudget monthBudget = monthBudgetService.findForCurrentUserBy(yearMonth)
                .orElseGet(() -> monthBudgetService.createBudgetFor(yearMonth));

        Transaction transaction = convertToEntity(dto);
        String categoryName = dto.getCategoryName();
        Category category = monthBudget.getCategories().stream()
                .filter(c -> c.getName().equals(categoryName))
                .findAny()
                .orElseGet(() -> createCategory(categoryName));
        monthBudget.getCategories().add(category);
        category.getTransactions().add(transaction);
        transaction.setCategory(category);
        repository.create(transaction);
    }

    private List<TransactionDto> collectDtosFrom(Collection<Category> categories) {
        return categories.stream()
                .map(Category::getTransactions)
                .flatMap(Collection::stream)
                .map(this::convertToDto)
                .collect(Collectors.toList());
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

    private TransactionDto convertToDto(Transaction transaction) {
        return new TransactionDto(transaction.getDate(),
                transaction.getAmount(),
                transaction.getCategory().getName());
    }
}
