package com.corriel.web;

import com.corriel.budget.entity.Budget;
import com.corriel.budget.service.BudgetService;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class BudgetControllerTest {

    private static final long TEST_BUDGET_ID = 1;

    @Test
    public void shouldShowUserBudget() throws Exception {
        Budget testBudget = new Budget();

        MockMvc mockMvc = prepareMockMvcController(testBudget);

        mockMvc.perform(get("/budget/" + TEST_BUDGET_ID))
                .andExpect(view().name("budget"))
                .andExpect(model().attributeExists("budget"))
                .andExpect(model().attribute("budget", testBudget));
    }

    private MockMvc prepareMockMvcController(Budget budget) {
        BudgetService budgetService = mock(BudgetService.class);
        when(budgetService.find(TEST_BUDGET_ID)).thenReturn(budget);

        BudgetController controller = new BudgetController();
        ReflectionTestUtils.setField(controller, "budgetService", budgetService);

        return standaloneSetup(controller).build();
    }

}