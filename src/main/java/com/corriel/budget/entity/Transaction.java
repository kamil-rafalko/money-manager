package com.corriel.budget.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private BigDecimal amount;
    @ManyToOne
    @JoinColumn(name = "fund")
    private Fund fund;
    @Column(nullable = false)
    private LocalDate date;
    @ManyToOne
    @JoinColumn(name = "transaction_category")
    private TransactionCategory transactionCategory;
}
