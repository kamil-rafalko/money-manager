package com.corriel.budget.entity.fund;

import com.corriel.budget.entity.transaction.MoneyTransaction;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "fund_type", discriminatorType = DiscriminatorType.STRING)
public abstract class Fund {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 120)
    private String name;

    @Column(nullable = false)
    private BigDecimal amount;

    @OneToMany(mappedBy = "fund")
    private Set<MoneyTransaction> transaction;
}
