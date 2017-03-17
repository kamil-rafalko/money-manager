package com.corriel.web.dto;

import jdk.nashorn.internal.ir.annotations.Immutable;

@Immutable
public class BudgetDto {
    public final long id;
    public final String name;

    public BudgetDto(final long id, final String name) {
        this.id = id;
        this.name = name;
    }
}