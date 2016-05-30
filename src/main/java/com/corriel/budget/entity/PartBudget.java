package com.corriel.budget.entity;

import com.corriel.budget.entity.transaction.TransactionCategory;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;
import java.util.Set;

@Getter
@Setter
@Entity(name = "PartBudget")
public class PartBudget {

    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "Name", nullable = false, length = 120)
    private String name;

    @Column(name = "StartDate")
    private Instant startDate;

    @Column(name = "EndDate")
    private Instant endDate;

    @OneToMany
    private Set<TransactionCategory> transactionCategories;
}
