package com.corriel.budget.entity;

import com.corriel.budget.entity.fund.Fund;
import com.corriel.budget.entity.transaction.TransactionCategory;
import com.corriel.users.entity.User;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Budget {

    private long id;
    private String name;
    private Set<Fund> funds;
    private Set<User> users;
    private Set<PartBudget> partBudgets;
    private Set<TransactionCategory> defaultTransactionCategories;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(length = 120)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToMany
    @JoinTable(name = "budget_fund", joinColumns = @JoinColumn(name = "budget"), inverseJoinColumns = @JoinColumn(name = "fund"))
    public Set<Fund> getFunds() {
        return funds;
    }

    public void setFunds(Set<Fund> funds) {
        this.funds = funds;
    }

    @ManyToMany
    @JoinTable(name = "user_budget", joinColumns = @JoinColumn(name = "budget"))
    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @OneToMany
    public Set<PartBudget> getPartBudgets() {
        return partBudgets;
    }

    public void setPartBudgets(Set<PartBudget> partBudgets) {
        this.partBudgets = partBudgets;
    }
}
