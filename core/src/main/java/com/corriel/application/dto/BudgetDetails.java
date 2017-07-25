package com.corriel.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Map;

@Getter
@AllArgsConstructor
public class BudgetDetails {
    private final String name;
    private final Map<String, BigDecimal> categoryToExpense;
}
