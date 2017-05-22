package com.corriel.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@Getter
public class TransactionDto {
    private final LocalDate date;
    private final BigDecimal amount;
    private final String categoryName;
    private final long fundId;
}