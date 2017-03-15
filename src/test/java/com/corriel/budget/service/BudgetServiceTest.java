package com.corriel.budget.service;

import com.corriel.budget.entity.Budget;
import com.corriel.budget.entity.Category;
import com.corriel.budget.entity.MonthlyBudget;
import com.corriel.budget.repository.BudgetDao;
import com.corriel.users.service.UserService;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BudgetServiceTest {

    private Category first_category;
    private Category second_category;
    private Category third_category;

    private Map<String, BigDecimal> firstPartBudgetCategoryToExpense;
    private Map<String, BigDecimal> secondPartBudgetCategoryToExpense;

    @Before
    public void prepareTransactionCategories() {
        first_category = Category.builder().name("FIRST_CATEGORY").build();
        second_category = Category.builder().name("SECOND_CATEGORY").build();
        third_category = Category.builder().name("THIRD_CATEGORY").build();
    }

    @Before
    public void fillDataForMock() {
        firstPartBudgetCategoryToExpense = new HashMap<>();
        firstPartBudgetCategoryToExpense.put(first_category.getName(), new BigDecimal(123.45));
        firstPartBudgetCategoryToExpense.put(second_category.getName(), new BigDecimal(223.45));
        firstPartBudgetCategoryToExpense.put(third_category.getName(), new BigDecimal(323.45));

        secondPartBudgetCategoryToExpense = new HashMap<>();
        secondPartBudgetCategoryToExpense.put(first_category.getName(), new BigDecimal(100));
        secondPartBudgetCategoryToExpense.put(third_category.getName(), new BigDecimal(200));
    }

    @Test
    public void shouldMapCategoryToValidExpense() {
        UserService userService = mock(UserService.class);
        PartBudgetService partBudgetService = mock(PartBudgetService.class);
        MonthlyBudget firstMonthlyBudget = new MonthlyBudget();
        firstMonthlyBudget.setId(1L);
        when(partBudgetService.mapCategoryToExpenses(firstMonthlyBudget))
                .thenReturn(firstPartBudgetCategoryToExpense);
        MonthlyBudget secondMonthlyBudget = new MonthlyBudget();
        secondMonthlyBudget.setId(2L);
        when(partBudgetService.mapCategoryToExpenses(secondMonthlyBudget))
                .thenReturn(secondPartBudgetCategoryToExpense);

        HashSet<MonthlyBudget> monthlyBudgets = new HashSet<>();
        monthlyBudgets.add(firstMonthlyBudget);
        monthlyBudgets.add(secondMonthlyBudget);
        Budget budget = new Budget();
        budget.setMonthlyBudgets(monthlyBudgets);

        BudgetDao budgetDao = mock(BudgetDao.class);
        when(budgetDao.find(1L)).thenReturn(budget);

        BudgetService budgetService = new BudgetService(budgetDao, userService, partBudgetService);
        Map<String, BigDecimal> categoryToSummaryExpense = budgetService
                .mapCategoryToExpenses(budget);

        Map<String, BigDecimal> expectedMap = prepareExpectedMap();

        assertEquals(expectedMap, categoryToSummaryExpense);
    }

    private Map<String, BigDecimal> prepareExpectedMap() {
        Map<String, BigDecimal> expectedMap = new HashMap<>();

        expectedMap.put(first_category.getName(), firstPartBudgetCategoryToExpense.get(first_category.getName())
                .add(secondPartBudgetCategoryToExpense.get(first_category.getName())));
        expectedMap.put(second_category.getName(), firstPartBudgetCategoryToExpense.get(second_category.getName()));
        expectedMap.put(third_category.getName(), firstPartBudgetCategoryToExpense.get(third_category.getName())
                .add(secondPartBudgetCategoryToExpense.get(third_category.getName())));

        return expectedMap;
    }
}