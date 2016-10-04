package com.corriel.web;

import com.corriel.budget.entity.Budget;
import com.corriel.users.entity.SystemUser;
import com.corriel.users.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class MainControllerTest {

    private MockMvc mockMvc;

    @Before
    public void prepareMockMvc() {
        UserService userService = mock(UserService.class);

        SystemUser systemUser = new SystemUser();
        systemUser.setBudgets(createTestsBudgets());

        when(userService.getCurrentUserWithBudgets()).thenReturn(Optional.of(systemUser));

        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/jsp/view/");
        viewResolver.setSuffix(".jsp");

        mockMvc = standaloneSetup(new MainController(userService)).setViewResolvers(viewResolver).build();
    }

    @Test
    public void shouldShowHomePageWithBudgets() throws Exception {

        mockMvc.perform(get("/home")).andExpect(view().name("home"))
                .andExpect(model().attributeExists("title"))
                .andExpect(model().attributeExists("budgets"))
                .andExpect(model().attribute("budgets", createTestsBudgets()));
    }

    @Test
    public void shouldShowLoginPageWithGivenParametersInModel() throws Exception {
        mockMvc.perform(get("/login")).andExpect(view().name("login"));
        mockMvc.perform(get("/login?error=errorTest")).andExpect(view().name("login"))
                .andExpect(model().attributeExists("error"));
        mockMvc.perform(get("/login?logout=logoutTest")).andExpect(view().name("login"))
                .andExpect(model().attributeExists("message"));
    }

    private Set<Budget> createTestsBudgets() {
        Set<Budget> budgets = new HashSet<Budget>();

        for (int i = 1; i <= 10; i++) {
            Budget budget = new Budget();
            budget.setId(i);
            budgets.add(budget);
        }

        return budgets;
    }
}
