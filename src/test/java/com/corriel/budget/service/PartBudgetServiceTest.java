package com.corriel.budget.service;

import com.corriel.budget.entity.PartBudget;
import com.corriel.budget.entity.transaction.MoneyTransaction;
import com.corriel.budget.entity.transaction.TransactionCategory;
import com.corriel.budget.repository.PartBudgetDao;
import com.corriel.users.service.UserService;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PartBudgetServiceTest {

    private static final String FIRST_CATEGORY = "FIRST_CATEGORY";
    private static final String SECOND_CATEGORY = "SECOND_CATEGORY";
    private static final String THIRD_CATEGORY = "THIRD_CATEGORY";
    
    private TransactionCategory first_category;
    private TransactionCategory second_category;
    private TransactionCategory third_category;
    
    private PartBudget partBudget;

    private List<MoneyTransaction> firstMoneyTransactions = new ArrayList<>();
    private List<MoneyTransaction> secondMoneyTransactions = new ArrayList<>();
    private List<MoneyTransaction> thirdMoneyTransactions = new ArrayList<>();
    
    @Before
    public void setupPartBudget() {
        Map<String, List<MoneyTransaction>> moneyTransactionMap = setupMoneyTransactions();
        Set<TransactionCategory> transactionCategories = setupCategories(moneyTransactionMap);
        partBudget = new PartBudget();
        partBudget.setTransactionCategories(transactionCategories);
    }

    @Test
    public void shouldMapCategoryToValidExpense() {
        UserService userService = mock(UserService.class);
        PartBudgetDao partBudgetDao = mock(PartBudgetDao.class);
        when(partBudgetDao.find(partBudget.getId())).thenReturn(partBudget);
        PartBudgetService partBudgetService = new PartBudgetService(userService, partBudgetDao);
        Map<String, BigDecimal> categoryToSummaryExpense = partBudgetService
                .mapCategoryToExpenses(partBudget);

        HashMap<String, BigDecimal> expectedMap = new HashMap<>();
        expectedMap.put(first_category.getName(), firstMoneyTransactions.stream().map(MoneyTransaction::getAmount).reduce
                (BigDecimal.ZERO, BigDecimal::add));
        expectedMap.put(second_category.getName(), secondMoneyTransactions.stream().map(MoneyTransaction::getAmount).reduce
                (BigDecimal.ZERO, BigDecimal::add));
        expectedMap.put(third_category.getName(), thirdMoneyTransactions.stream().map(MoneyTransaction::getAmount).reduce
                (BigDecimal.ZERO, BigDecimal::add));

        assertEquals(expectedMap, categoryToSummaryExpense);
    }

    private Map<String, List<MoneyTransaction>> setupMoneyTransactions() {
        HashMap<String, List<MoneyTransaction>> moneyTransactionsMap = new HashMap<>();

        firstMoneyTransactions.add(MoneyTransaction.builder().amount(new BigDecimal(100)).build());
        firstMoneyTransactions.add(MoneyTransaction.builder().amount(new BigDecimal(200)).build());
        firstMoneyTransactions.add(MoneyTransaction.builder().amount(new BigDecimal(300)).build());
        moneyTransactionsMap.put(FIRST_CATEGORY, firstMoneyTransactions);

        secondMoneyTransactions.add(MoneyTransaction.builder().amount(new BigDecimal(400)).build());
        secondMoneyTransactions.add(MoneyTransaction.builder().amount(new BigDecimal(500)).build());

        moneyTransactionsMap.put(SECOND_CATEGORY, secondMoneyTransactions);
        return moneyTransactionsMap;
    }

    private Set<TransactionCategory> setupCategories(Map<String, List<MoneyTransaction>> moneyTransactionsMap) {
        first_category = TransactionCategory.builder()
                .name(FIRST_CATEGORY)
                .moneyTransactions(moneyTransactionsMap.get(FIRST_CATEGORY))
                .build();

        second_category = TransactionCategory.builder()
                .name(SECOND_CATEGORY)
                .moneyTransactions(secondMoneyTransactions)
                .build();

        third_category = TransactionCategory.builder()
                .name(THIRD_CATEGORY)
                .moneyTransactions(thirdMoneyTransactions)
                .build();
        
        Set<TransactionCategory> transactionCategories = new HashSet<>();
        transactionCategories.add(first_category);
        transactionCategories.add(second_category);
        transactionCategories.add(third_category);
        
        return transactionCategories;
    }
}
