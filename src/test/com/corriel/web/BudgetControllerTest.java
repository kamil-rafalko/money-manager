package com.corriel.web;

import com.corriel.budget.entity.Budget;
import com.corriel.budget.service.BudgetService;
import com.corriel.users.entity.SystemUser;
import com.corriel.users.service.UserService;
import com.corriel.users.service.implementation.AppUserDetailsService;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class BudgetControllerTest {

    private static final String TEST_USERNAME = "testUsername";

    @Test
    public void shouldShowUserBudgets() throws Exception {
        List<Budget> testBudgets = createTestBudgets();

        MockMvc mockMvc = prepareMockMvcController(testBudgets);

        mockMvc.perform(get("/budgets")).andExpect(view().name("budget"))
                .andExpect(model().attributeExists("budgetList"))
                .andExpect(model().attribute("budgetList", hasItems(testBudgets.toArray())));
    }

    @Test
    public void shouldShowCreateNewBudgetPageIfUserHasNoBudgets() throws Exception {

        MockMvc mockMvc = prepareMockMvcController(new ArrayList<>());

        mockMvc.perform(get("/budgets"))
                .andExpect(view().name("new_budget"));
    }

    private MockMvc prepareMockMvcController(List<Budget> budgets) {
        UserService userService = mock(UserService.class);
        when(userService.getCurrentUserName()).thenReturn(TEST_USERNAME);

        BudgetService budgetService = mock(BudgetService.class);
        when(budgetService.findUsersBudgets(TEST_USERNAME)).thenReturn(budgets);

        BudgetController controller = new BudgetController();
        ReflectionTestUtils.setField(controller, "userService", userService);
        ReflectionTestUtils.setField(controller, "budgetService", budgetService);

        return standaloneSetup(controller).build();
    }

    private List<Budget> createTestBudgets() {
        List<Budget> budgets = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            budgets.add(new Budget());
        }
        return budgets;
    }

}