package com.corriel.budget.entity;

import com.corriel.budget.entity.transaction.TransactionCategory;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Entity(name = "part_budget")
public class PartBudget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 120)
    private String name;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @OneToMany
    @JoinColumn(name = "id")
    private Set<TransactionCategory> transactionCategories;
}
