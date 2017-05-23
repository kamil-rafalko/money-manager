package com.corriel.application.core.budget;

import com.corriel.application.core.entity.Category;
import com.corriel.application.core.entity.MonthBudget;
import com.corriel.application.core.entity.Transaction;
import com.corriel.application.core.repository.MonthBudgetRepository;
import com.corriel.application.core.users.UserService;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MonthBudgetServiceTest {

    private static final String FIRST_CATEGORY = "FIRST_CATEGORY";
    private static final String SECOND_CATEGORY = "SECOND_CATEGORY";
    private static final String THIRD_CATEGORY = "THIRD_CATEGORY";
    
    private Category first_category;
    private Category second_category;
    private Category third_category;
    
    private MonthBudget monthBudget;

    private List<Transaction> firstTransactions = new ArrayList<>();
    private List<Transaction> secondTransactions = new ArrayList<>();
    private List<Transaction> thirdTransactions = new ArrayList<>();
    
    @Before
    public void setupPartBudget() {
        Map<String, List<Transaction>> moneyTransactionMap = setupMoneyTransactions();
        Set<Category> categories = setupCategories(moneyTransactionMap);
        monthBudget = new MonthBudget();
        monthBudget.setCategories(categories);
    }

    @Test
    public void shouldMapCategoryToValidExpense() {
        UserService userService = mock(UserService.class);
        MonthBudgetRepository monthBudgetRepository = mock(MonthBudgetRepository.class);
        when(monthBudgetRepository.find(monthBudget.getId())).thenReturn(monthBudget);
        MonthBudgetService monthBudgetService = new MonthBudgetService(userService, monthBudgetRepository);
        Map<String, BigDecimal> categoryToSummaryExpense = monthBudgetService
                .mapCategoryToExpenses(monthBudget);

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
