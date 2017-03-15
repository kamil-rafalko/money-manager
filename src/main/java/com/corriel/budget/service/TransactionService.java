package com.corriel.budget.service;

import com.corriel.budget.entity.MonthlyBudget;
import com.corriel.budget.entity.Transaction;
import com.corriel.budget.entity.TransactionCategory;
import com.corriel.budget.repository.CategoryDao;
import com.corriel.budget.repository.FundDao;
import com.corriel.budget.repository.TransactionDao;
import com.corriel.users.service.UserService;
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
                .orElse(partBudgetService.createBudgetFor(yearMonth));

        Transaction transaction = convertToEntity(dto);
        TransactionCategory category = monthlyBudget.getTransactionCategories().stream()
                .filter(c -> c.getName().equals(dto.getCategoryName()))
                .findAny()
                .orElseGet(() -> createCategory(dto.getCategoryName()));
        monthlyBudget.getTransactionCategories().add(category);
        category.getTransactions().add(transaction);
        transaction.setTransactionCategory(category);
        transactionDao.create(transaction);
    }

    private TransactionCategory createCategory(String name) {
        TransactionCategory category = TransactionCategory.builder()
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
