package com.corriel.budget.entity;

import com.corriel.data.converter.YearMonthConverter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.YearMonth;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class MonthlyBudget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    @Convert(converter = YearMonthConverter.class)
    private YearMonth yearMonth;

    @OneToMany
    @JoinColumn(name = "part_budget")
    private Set<TransactionCategory> transactionCategories;

    public MonthlyBudget(final YearMonth yearMonth) {
        this.yearMonth = yearMonth;
        this.transactionCategories = new HashSet<>();
    }
}
