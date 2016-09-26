package com.corriel.web;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class MainControllerTest {

    private MockMvc mockMvc;

    @Before
    public void prepareMockMvc() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/jsp/view/");
        viewResolver.setSuffix(".jsp");

        mockMvc = standaloneSetup(new MainController()).setViewResolvers(viewResolver).build();
    }

    @Test
    public void shouldShowHomePage() throws Exception {
        mockMvc.perform(get("/home")).andExpect(view().name("home")).andExpect(model().attributeExists("title"));
    }

    @Test
    public void shouldShowLoginPageWithGivenParametersInModel() throws Exception {
        mockMvc.perform(get("/login")).andExpect(view().name("login"));
        mockMvc.perform(get("/login?error=errorTest")).andExpect(view().name("login"))
                .andExpect(model().attributeExists("error"));
        mockMvc.perform(get("/login?logout=logoutTest")).andExpect(view().name("login"))
                .andExpect(model().attributeExists("message"));
    }
}
