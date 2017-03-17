package com.corriel.budget.service;

import com.corriel.budget.entity.MonthlyBudget;
import com.corriel.budget.entity.Transaction;
import com.corriel.budget.entity.Category;
import com.corriel.budget.repository.FundDao;
import com.corriel.budget.repository.TransactionDao;
import com.corriel.web.dto.TransactionDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.time.YearMonth;
import java.util.ArrayList;

@Service
@Transactional
public class TransactionService {

    private final TransactionDao transactionDao;
    private final FundDao fundDao;
    private final PartBudgetService partBudgetService;
    private final CategoryService categoryService;

    @Inject
    public TransactionService(final TransactionDao transactionDao,
                              final FundDao funDao,
                              final PartBudgetService partBudgetService,
                              final CategoryService categoryService) {
        this.transactionDao = transactionDao;
        this.fundDao = funDao;
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
        transactionDao.create(transaction);
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
