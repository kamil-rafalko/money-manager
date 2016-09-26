package com.corriel.budget.service;

import com.corriel.budget.entity.PartBudget;
import com.corriel.budget.entity.transaction.MoneyTransaction;
import com.corriel.budget.entity.transaction.TransactionCategory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Transactional
public class PartBudgetService {

    public Map<TransactionCategory, BigDecimal> mapCategoryToSummaryExpense(PartBudget partBudget) {
        Set<TransactionCategory> transactionCategories = partBudget.getTransactionCategories();

        return transactionCategories.stream().collect(Collectors.toMap(Function.identity(),
                category -> category.getMoneyTransactions().stream().map(MoneyTransaction::getAmount)
                        .reduce(BigDecimal.ZERO, BigDecimal::add)));
    }
}
