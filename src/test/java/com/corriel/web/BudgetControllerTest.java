package com.corriel.web;

import com.corriel.budget.service.BudgetService;
import com.corriel.budget.service.PartBudgetService;
import com.corriel.web.dto.BudgetDetails;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.Charset;
import java.util.HashMap;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class BudgetControllerTest {

    private static final long TEST_PART_BUDGET_ID = 1;
    private static final String TEST_DETAILS = "TEST_DETAILS";

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    @Test
    public void shouldShowMainBudgetDetails() throws Exception {
        BudgetDetails testDetails = new BudgetDetails(TEST_DETAILS, new HashMap<>());
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String detailsJson = ow.writeValueAsString(testDetails);

        MockMvc mockMvc = prepareMockMvcController(testDetails);

        mockMvc.perform(get("/budget/details"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(content().json(detailsJson));
    }

    private MockMvc prepareMockMvcController(BudgetDetails details) {
        BudgetService budgetService = mock(BudgetService.class);
        when(budgetService.createDetails()).thenReturn(details);

        PartBudgetService partBudgetService = mock(PartBudgetService.class);
        when(partBudgetService.createDetails(TEST_PART_BUDGET_ID)).thenReturn(details);

        BudgetController controller = new BudgetController(budgetService, partBudgetService);
        return standaloneSetup(controller).build();
    }

}