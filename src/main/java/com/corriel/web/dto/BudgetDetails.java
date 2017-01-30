package com.corriel.web.dto;

import java.math.BigDecimal;
import java.util.Map;

public class BudgetDetails {
    private String name;
    private Map<String, BigDecimal> categoryToExpense;

    public BudgetDetails(String name, Map<String, BigDecimal> categoryToExpense) {
        this.name = name;
        this.categoryToExpense = categoryToExpense;
    }
    public String getName() {
        return name;
    }

    public Map<String, BigDecimal> getCategoryToExpense() {
        return categoryToExpense;
    }
}
