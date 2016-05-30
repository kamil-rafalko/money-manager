package com.corriel.budget.entity;

import com.corriel.budget.entity.fund.Fund;
import com.corriel.users.entity.SystemUser;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
public class Budget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 120)
    private String name;

    @ManyToMany
    @JoinTable(name = "budget_fund", joinColumns = @JoinColumn(name = "budget"), inverseJoinColumns = @JoinColumn(name = "fund"))
    private Set<Fund> funds;

    @ManyToMany(mappedBy = "budgets")
    private Set<SystemUser> users;

    @OneToMany
    private Set<PartBudget> partBudgets;
}
