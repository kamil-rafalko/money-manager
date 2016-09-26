package com.corriel.budget.service;

import com.corriel.budget.entity.Budget;
import com.corriel.budget.entity.PartBudget;
import com.corriel.budget.entity.transaction.TransactionCategory;
import com.corriel.budget.repository.BudgetDao;
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

    private TransactionCategory first_category;
    private TransactionCategory second_category;
    private TransactionCategory third_category;

    private Map<TransactionCategory, BigDecimal> firstPartBudgetCategoryToExpense;
    private Map<TransactionCategory, BigDecimal> secondPartBudgetCategoryToExpense;

    @Before
    public void prepareTransactionCategories() {
        first_category = TransactionCategory.builder().name("FIRST_CATEGORY").build();
        second_category = TransactionCategory.builder().name("SECOND_CATEGORY").build();
        third_category = TransactionCategory.builder().name("THIRD_CATEGORY").build();
    }

    @Before
    public void fillDataForMock() {
        firstPartBudgetCategoryToExpense = new HashMap<>();
        firstPartBudgetCategoryToExpense.put(first_category, new BigDecimal(123.45));
        firstPartBudgetCategoryToExpense.put(second_category, new BigDecimal(223.45));
        firstPartBudgetCategoryToExpense.put(third_category, new BigDecimal(323.45));

        secondPartBudgetCategoryToExpense = new HashMap<>();
        secondPartBudgetCategoryToExpense.put(first_category, new BigDecimal(100));
        secondPartBudgetCategoryToExpense.put(third_category, new BigDecimal(200));
    }

    @Test
    public void shouldMapCategoryToValidExpense() {
        PartBudgetService partBudgetService = mock(PartBudgetService.class);
        PartBudget firstPartBudget = new PartBudget();
        when(partBudgetService.mapCategoryToSummaryExpense(firstPartBudget))
                .thenReturn(firstPartBudgetCategoryToExpense);
        PartBudget secondPartBudget = new PartBudget();
        when(partBudgetService.mapCategoryToSummaryExpense(secondPartBudget))
                .thenReturn(secondPartBudgetCategoryToExpense);

        HashSet<PartBudget> partBudgets = new HashSet<>();
        partBudgets.add(firstPartBudget);
        partBudgets.add(secondPartBudget);
        Budget budget = new Budget();
        budget.setPartBudgets(partBudgets);

        BudgetDao budgetDao = mock(BudgetDao.class);
        when(budgetDao.find(1L)).thenReturn(budget);

        BudgetService budgetService = new BudgetService(budgetDao, partBudgetService);
        Map<TransactionCategory, BigDecimal> categoryToSummaryExpense = budgetService
                .mapCategoryToSummaryExpense(1L);

        Map<TransactionCategory, BigDecimal> expectedMap = prepareExpectedMap();

        assertEquals(expectedMap, categoryToSummaryExpense);
    }

    private Map<TransactionCategory, BigDecimal> prepareExpectedMap() {
        Map<TransactionCategory, BigDecimal> expectedMap = new HashMap<>();

        expectedMap.put(first_category, firstPartBudgetCategoryToExpense.get(first_category)
                .add(secondPartBudgetCategoryToExpense.get(first_category)));
        expectedMap.put(second_category, firstPartBudgetCategoryToExpense.get(second_category));
        expectedMap.put(third_category, firstPartBudgetCategoryToExpense.get(third_category)
                .add(secondPartBudgetCategoryToExpense.get(third_category)));

        return expectedMap;
    }
}