package com.corriel.budget.entity.fund;

import com.corriel.budget.entity.transaction.MoneyTransaction;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "fund_type", discriminatorType = DiscriminatorType.STRING)
public abstract class Fund {
    private long id;
    private String name;
    private BigDecimal amount;
    private Set<MoneyTransaction> transaction;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(nullable = false, length = 120)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(nullable = false)
    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @OneToMany(mappedBy = "fund")
    public Set<MoneyTransaction> getTransaction() {
        return transaction;
    }

    public void setTransaction(Set<MoneyTransaction> transaction) {
        this.transaction = transaction;
    }
}
