package com.corriel.application.core.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@EqualsAndHashCode
public class Budget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 120)
    private String name;

    @ManyToMany
    @JoinTable(name = "budget_fund", joinColumns = @JoinColumn(name = "budget"),
            inverseJoinColumns = @JoinColumn(name = "fund"))
    private Set<Fund> funds;

    @OneToMany()
    @JoinColumn(name = "budget")
    @OrderBy("yearmonth DESC")
    private Set<MonthlyBudget> monthlyBudgets;
}
