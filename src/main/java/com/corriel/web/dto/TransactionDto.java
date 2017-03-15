package com.corriel.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@Getter
public class TransactionDto {
    private LocalDate date;
    private BigDecimal amount;
    private String categoryName;
    private long fundId;
}