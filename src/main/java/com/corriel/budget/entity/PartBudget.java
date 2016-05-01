package com.corriel.budget.entity;

import com.corriel.budget.entity.transaction.TransactionCategory;

import javax.persistence.*;
import java.time.Instant;
import java.util.Set;

@Entity(name = "PartBudget")
public class PartBudget {

    private long id;
    private String name;
    private Instant startDate;
    private Instant endDate;
    private Set<TransactionCategory> transactionCategories;

    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(name = "Name", nullable = false, length = 120)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToMany
    public Set<TransactionCategory> getTransactionCategories() {
        return transactionCategories;
    }

    public void setTransactionCategories(Set<TransactionCategory> transactionCategories) {
        this.transactionCategories = this.transactionCategories;
    }

    @Column(name = "StartDate")
    public Instant getStartDate() {
        return startDate;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    @Column(name = "EndDate")
    public Instant getEndDate() {
        return endDate;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }
}
