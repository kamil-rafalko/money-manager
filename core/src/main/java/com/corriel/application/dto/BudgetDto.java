package com.corriel.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BudgetDto {
    private final long id;
    private final String name;
}