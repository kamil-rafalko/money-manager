package com.corriel.application.core.entity;

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
public class MonthBudget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    @Convert(converter = YearMonthConverter.class)
    private YearMonth yearMonth;

    @OneToMany
    @JoinColumn(name = "month_budget")
    private Set<Category> categories;

    public MonthBudget(final YearMonth yearMonth) {
        this.yearMonth = yearMonth;
        this.categories = new HashSet<>();
    }
}
