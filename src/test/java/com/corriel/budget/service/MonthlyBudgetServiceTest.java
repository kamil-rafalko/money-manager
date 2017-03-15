package com.corriel.budget.service;

import com.corriel.budget.entity.Category;
import com.corriel.budget.entity.MonthlyBudget;
import com.corriel.budget.entity.Transaction;
import com.corriel.budget.repository.PartBudgetDao;
import com.corriel.users.service.UserService;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MonthlyBudgetServiceTest {

    private static final String FIRST_CATEGORY = "FIRST_CATEGORY";
    private static final String SECOND_CATEGORY = "SECOND_CATEGORY";
    private static final String THIRD_CATEGORY = "THIRD_CATEGORY";
    
    private Category first_category;
    private Category second_category;
    private Category third_category;
    
    private MonthlyBudget monthlyBudget;

    private List<Transaction> firstTransactions = new ArrayList<>();
    private List<Transaction> secondTransactions = new ArrayList<>();
    private List<Transaction> thirdTransactions = new ArrayList<>();
    
    @Before
    public void setupPartBudget() {
        Map<String, List<Transaction>> moneyTransactionMap = setupMoneyTransactions();
        Set<Category> categories = setupCategories(moneyTransactionMap);
        monthlyBudget = new MonthlyBudget();
        monthlyBudget.setCategories(categories);
    }

    @Test
    public void shouldMapCategoryToValidExpense() {
        UserService userService = mock(UserService.class);
        PartBudgetDao partBudgetDao = mock(PartBudgetDao.class);
        when(partBudgetDao.find(monthlyBudget.getId())).thenReturn(monthlyBudget);
        PartBudgetService partBudgetService = new PartBudgetService(userService, partBudgetDao);
        Map<String, BigDecimal> categoryToSummaryExpense = partBudgetService
                .mapCategoryToExpenses(monthlyBudget);

        HashMap<String, BigDecimal> expectedMap = new HashMap<>();
        expectedMap.put(first_category.getName(), firstTransactions.stream().map(Transaction::getAmount).reduce
                (BigDecimal.ZERO, BigDecimal::add));
        expectedMap.put(second_category.getName(), secondTransactions.stream().map(Transaction::getAmount).reduce
                (BigDecimal.ZERO, BigDecimal::add));
        expectedMap.put(third_category.getName(), thirdTransactions.stream().map(Transaction::getAmount).reduce
                (BigDecimal.ZERO, BigDecimal::add));

        assertEquals(expectedMap, categoryToSummaryExpense);
    }

    private Map<String, List<Transaction>> setupMoneyTransactions() {
        HashMap<String, List<Transaction>> moneyTransactionsMap = new HashMap<>();

        firstTransactions.add(Transaction.builder().amount(new BigDecimal(100)).build());
        firstTransactions.add(Transaction.builder().amount(new BigDecimal(200)).build());
        firstTransactions.add(Transaction.builder().amount(new BigDecimal(300)).build());
        moneyTransactionsMap.put(FIRST_CATEGORY, firstTransactions);

        secondTransactions.add(Transaction.builder().amount(new BigDecimal(400)).build());
        secondTransactions.add(Transaction.builder().amount(new BigDecimal(500)).build());

        moneyTransactionsMap.put(SECOND_CATEGORY, secondTransactions);
        return moneyTransactionsMap;
    }

    private Set<Category> setupCategories(Map<String, List<Transaction>> moneyTransactionsMap) {
        first_category = Category.builder()
                .name(FIRST_CATEGORY)
                .transactions(moneyTransactionsMap.get(FIRST_CATEGORY))
                .build();

        second_category = Category.builder()
                .name(SECOND_CATEGORY)
                .transactions(secondTransactions)
                .build();

        third_category = Category.builder()
                .name(THIRD_CATEGORY)
                .transactions(thirdTransactions)
                .build();
        
        Set<Category> categories = new HashSet<>();
        categories.add(first_category);
        categories.add(second_category);
        categories.add(third_category);
        
        return categories;
    }
}
